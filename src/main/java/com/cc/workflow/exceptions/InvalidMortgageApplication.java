package com.cc.workflow.exceptions;

public class InvalidMortgageApplication extends RuntimeException {
    private String errorMessage;

    public InvalidMortgageApplication() {
        errorMessage = "Requires valid user id (UUID), valid mortgageInsuranceId (UUID), mortgageVal > 0";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
