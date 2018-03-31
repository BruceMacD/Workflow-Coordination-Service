package com.cc.workflow.exceptions;

public class AlreadyApplied extends RuntimeException {
    private String errorMessage;

    public AlreadyApplied() {
        errorMessage = "User already applied.";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
