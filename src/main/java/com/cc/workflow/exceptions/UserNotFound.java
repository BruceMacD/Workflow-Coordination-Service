package com.cc.workflow.exceptions;

public class UserNotFound extends RuntimeException {
    private String errorMessage;

    public UserNotFound() {
        errorMessage = "Requested user was not found.";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}