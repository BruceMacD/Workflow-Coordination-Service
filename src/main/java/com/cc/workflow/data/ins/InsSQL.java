package com.cc.workflow.data.ins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsSQL implements InsDAO {
    private static String DB = "jdbc:mysql://csci4145final.cmeyxcfzcxwq.us-east-1.rds.amazonaws.com:3306/csci4145final?user=csci4145&password=csci4145";
    private static String GET_QUOTE = "SELECT * FROM `csci4145final`.`ins` WHERE mortgageId='%s'";
    private static String UPDATE_QUOTE = "INSERT INTO ins(`mortgageId`,`mortgageInsuranceId`,`insuredValue`,`deductible`,`name`,`receivedMun`,`receivedRe`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');";

    @Override
    public InsuranceQuote getQuote(String mortgageId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            Statement get = conn.createStatement();
            ResultSet result = get.executeQuery(String.format(GET_QUOTE, mortgageId));
            result.first();
            InsuranceQuote quote = convertFromDB(result);
            result.close();
            get.close();
            conn.close();
            return quote;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
    }

    @Override
    public InsuranceQuote updateQuote(InsuranceQuote quote) {
        InsuranceQuote fromDB = getQuote(quote.mortgageId);
        if (null != fromDB && fromDB.receivedMun) {
            quote.receivedMun = true;
        } else if (null != fromDB && fromDB.receivedRe) {
            quote.name = fromDB.name;
            quote.mortgageInsuranceId = fromDB.mortgageInsuranceId;
            quote.receivedRe = true;
        }
        return insert(quote);

    }

    private InsuranceQuote insert(InsuranceQuote quote) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB);
            PreparedStatement create = conn.prepareStatement(String.format(UPDATE_QUOTE, quote.mortgageId, quote.mortgageInsuranceId, "" + quote.insuredValue, "" + quote.deductible, quote.name, "" + quote.receivedMun, "" + quote.receivedRe));
            create.executeUpdate();
            create.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to db.", e);
        }
        return quote;
    }

    private InsuranceQuote convertFromDB(ResultSet result) throws SQLException {
        InsuranceQuote quote = new InsuranceQuote();
        String mortgageId;
        try {
            mortgageId = result.getString("mortgageId");
        } catch (Exception e) {
            return null;
        }
        quote.mortgageId = mortgageId;
        quote.mortgageInsuranceId = result.getString("mortgageInsuranceId");
        quote.name = result.getString("name");
        quote.insuredValue = Integer.parseInt(result.getString("insuredValue"));
        quote.deductible = Integer.parseInt(result.getString("deductible"));
        quote.receivedRe = Boolean.parseBoolean(result.getString("receivedRe"));
        quote.receivedMun = Boolean.parseBoolean(result.getString("receivedMun"));
        return quote;
    }
}
