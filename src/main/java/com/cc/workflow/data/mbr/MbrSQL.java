package com.cc.workflow.data.mbr;

import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.data.ins.InsuranceQuote;
import com.cc.workflow.data.mun.MUNServices;
import com.cc.workflow.exceptions.UserNotFound;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MbrSQL implements MbrDAO {
    private static String DB = "jdbc:mysql://csci4145final.cmeyxcfzcxwq.us-east-1.rds.amazonaws.com:3306/csci4145final?user=csci4145&password=csci4145";
    private static String CREATE_USER = "INSERT INTO `csci4145final`.`mbr` (`id`, `password`, `salt`, `application`, `empInfo`, `munInfo`, `insInfo`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private  static String GET_USER = "SELECT * FROM `csci4145final`.`mbr` WHERE id='%s';";
    private static String DELETE_USER = "DELETE FROM `csci4145final`.`mbr` WHERE id='%s';";
    private static String UPDATE_USER = "UPDATE `csci4145final`.`mbr` SET `application`=?, `empInfo`=?, `munInfo`=?, `insInfo`=? WHERE `id`=?;";

    @Override
    public MbrUser createUser(MbrUser user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement create = conn.prepareStatement(CREATE_USER);
            ObjectMapper mapper = new ObjectMapper();
            create.setString(1, user.getId());
            create.setString(2, user.getPassword());
            create.setString(3, user.getSalt());
            create.setString(4, mapper.writeValueAsString(user.getApplication()));
            create.setString(5, mapper.writeValueAsString(user.getEmpInfo()));
            create.setString(6, mapper.writeValueAsString(user.getMunInfo()));
            create.setString(7, mapper.writeValueAsString(user.getInsInfo()));
            create.executeUpdate();
            create.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return user;
    }

    @Override
    public MbrUser getUser(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            Statement get  = conn.createStatement();
            ResultSet result = get.executeQuery(String.format(GET_USER, id));
            result.first();
            MbrUser user =  convertFromDb(result);
            result.close();
            get.close();
            conn.close();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
    }

    @Override
    public MbrUser getUserByMortgageId(String mortgageId) {
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
    public MbrUser updateUser(MbrUser user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement update = conn.prepareStatement(UPDATE_USER);
            ObjectMapper mapper = new ObjectMapper();
            update.setString(1, mapper.writeValueAsString(user.getApplication()));
            update.setString(2, mapper.writeValueAsString(user.getEmpInfo()));
            update.setString(3, mapper.writeValueAsString(user.getMunInfo()));
            update.setString(4, mapper.writeValueAsString(user.getInsInfo()));
            update.setString(5, user.getId());
            update.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return user;
    }

    private MbrUser convertFromDb(ResultSet result) throws SQLException {
        String id;
        try {
            id = result.getString("id");
        } catch (Exception e) {
            throw new UserNotFound();
        }
        String salt = result.getString("salt");
        String password = result.getString("password");
        MbrUser user = new MbrUser();
        try {
            ObjectMapper mapper = new ObjectMapper();
            user = new MbrUser();
            MortgageApplication application = mapper.readValue(result.getString("application"), MortgageApplication.class);
            EmpUser empInfo = mapper.readValue(result.getString("empInfo"), EmpUser.class);
            MUNServices munInfo = mapper.readValue(result.getString("munInfo"), MUNServices.class);
            InsuranceQuote insInfo = mapper.readValue(result.getString("insInfo"), InsuranceQuote.class);
            user.setApplication(application);
            user.setEmpInfo(empInfo);
            user.setMunInfo(munInfo);
            user.setInsInfo(insInfo);
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        user.setId(id);
        user.setPassword(password);
        user.setSalt(salt);

        return user;
    }
}
