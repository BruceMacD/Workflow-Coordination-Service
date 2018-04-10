package com.cc.workflow.data.re;

import com.cc.workflow.exceptions.UserNotFound;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReSQL implements ReDAO {
    private static String DB = "jdbc:mysql://csci4145final.cmeyxcfzcxwq.us-east-1.rds.amazonaws.com:3306/csci4145final?user=csci4145&password=csci4145";
    private static String CREATE_USER = "INSERT INTO `csci4145final`.`re` (`id`, `password`, `salt`, `appraisal`) VALUES (?, ?, ?, ?);";
    private  static String GET_USER = "SELECT * FROM `csci4145final`.`re` WHERE id='%s';";
    private static String DELETE_USER = "DELETE FROM `csci4145final`.`re` WHERE id='%s';";
    private static String UPDATE_USER = "UPDATE `csci4145final`.`re` SET `appraisal`= ? WHERE `id`= ?;";

    @Override
    public REUser createUser(REUser user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement create = conn.prepareStatement(CREATE_USER);
            create.setString(1, user.getId());
            create.setString(2, user.getPassword());
            create.setString(3, user.getSalt());
            create.setString(4, new ObjectMapper().writeValueAsString(user.getAppraisal()));
            create.executeUpdate();
            create.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return user;
    }

    @Override
    public REUser getUser(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            Statement get  = conn.createStatement();
            ResultSet result = get.executeQuery(String.format(GET_USER, id));
            result.first();
            REUser user =  convertFromDb(result);
            result.close();
            get.close();
            conn.close();
            return user;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
    }

    @Override
    public REUser getUserByMortgageId(String mortgageId) {
        //TODO
        // just to stub return
        return getUser(mortgageId);
    }


    @Override
    public void deleteUser(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement delete = conn.prepareStatement(String.format(DELETE_USER, id));
            delete.executeUpdate();
            delete.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
    }

    @Override
    public REUser updateUser(REUser user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement update = conn.prepareStatement(UPDATE_USER);
            update.setString(1, new ObjectMapper().writeValueAsString(user.getAppraisal()));
            update.setString(2, user.getId());
            update.executeUpdate();
            update.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return user;
    }

    private REUser convertFromDb(ResultSet result) throws SQLException, IOException {
        String id;
        try {
            id = result.getString("id");
        } catch (Exception e) {
            throw new UserNotFound();
        }
        String salt = result.getString("salt");
        String password = result.getString("password");
        Appraisal appraisal = new ObjectMapper().readValue(result.getString("appraisal"), Appraisal.class);
        REUser user = new REUser();
        user.setId(id);
        user.setPassword(password);
        user.setSalt(salt);
        return user;
    }
}
