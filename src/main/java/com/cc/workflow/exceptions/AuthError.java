package com.cc.workflow.exceptions;

public class AuthError extends RuntimeException {
    public String errorMessage;

    public AuthError() {
        errorMessage = "Invalid credentials provided";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}