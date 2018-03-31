package com.cc.workflow.exceptions;

public class InvalidEmpUser extends RuntimeException {
    private String errorMessage;

    public InvalidEmpUser() {
        errorMessage = "Name and employment start date must be non-empty, salary > 0";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
