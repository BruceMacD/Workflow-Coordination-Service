package com.cc.workflow.exceptions;

public class ApplicationNotFound extends RuntimeException {
    private String errorMessage;

    public ApplicationNotFound() {
        errorMessage = "No mortgage application found for user.";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
