package com.cc.workflow.data.emp;

import com.cc.workflow.exceptions.UserNotFound;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpSQL implements EmpDAO {
    private static String DB = "jdbc:mysql://csci4145final.cmeyxcfzcxwq.us-east-1.rds.amazonaws.com:3306/csci4145final?user=csci4145&password=csci4145";
    private static String CREATE_USER = "INSERT INTO `csci4145final`.`emp` (`id`, `password`, `salt`, `name`, `salary`, `employmentStartDate`, `applied`, `mortgageId`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    private  static String GET_USER = "SELECT * FROM `csci4145final`.`emp` WHERE id='%s';";
    private static String GET_USER_BY_MBID = "SELECT * FROM `csci4145final`.`emp` WHERE mortgageId='%s';";
    private static String DELETE_USER = "DELETE FROM `csci4145final`.`emp` WHERE id='%s';";
    private static String UPDATE_USER = "UPDATE `csci4145final`.`emp` SET `name`='%s', `salary`='%s', `employmentStartDate`='%s', `applied`='%s', `mortgageId`='%s' WHERE `id`='%s';";

    @Override
    public EmpUser createUser(EmpUser user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement create = conn.prepareStatement(String.format(CREATE_USER, user.getId(), user.getPassword(), user.getSalt(), user.getName(), "" + user.getSalary(), user.getEmploymentStartDate(), "" + user.isApplied(), user.getMortgageId()));
            create.executeUpdate();
            create.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return user;
    }

    @Override
    public EmpUser getUser(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            Statement get  = conn.createStatement();
            ResultSet result = get.executeQuery(String.format(GET_USER, id));
            result.first();
            EmpUser user =  convertFromDb(result);
            result.close();
            get.close();
            conn.close();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
    }

    @Override
    public EmpUser getUserByMortgageId(String mortgageId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            Statement get  = conn.createStatement();
            ResultSet result = get.executeQuery(String.format(GET_USER_BY_MBID, mortgageId));
            result.first();
            EmpUser user =  convertFromDb(result);
            result.close();
            get.close();
            conn.close();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
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
    public EmpUser updateUser(String id, EmpUser user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement update = conn.prepareStatement(String.format(UPDATE_USER, user.getName(), "" + user.getSalary(), user.getEmploymentStartDate(), "" + user.isApplied(), user.getMortgageId(), user.getId()));
            update.executeUpdate();
            update.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return user;
    }

    private EmpUser convertFromDb(ResultSet result) throws SQLException {
        String id;
        try {
            id = result.getString("id");
        } catch (Exception e) {
            throw new UserNotFound();
        }
        String salt = result.getString("salt");
        String password = result.getString("password");
        String name = result.getString("name");
        int salary = Integer.parseInt(result.getString("salary"));
        String startDate = result.getString("employmentStartDate");
        boolean applied = Boolean.parseBoolean(result.getString("applied"));
        String mortgageId = result.getString("mortgageId");
        EmpUser user = new EmpUser(name, salary, startDate, applied);
        user.setId(id);
        user.setPassword(password);
        user.setSalt(salt);
        user.setMortgageId(mortgageId);
        return user;
    }
}
