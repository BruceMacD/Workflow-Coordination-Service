package com.cc.workflow.exceptions;

public class MortgageApplicationAlreadyExists extends RuntimeException{
    private String errorMessage;

    public MortgageApplicationAlreadyExists() {
        errorMessage = "User already has applied for a mortgage.";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
