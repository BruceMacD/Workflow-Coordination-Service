package com.cc.workflow.security;

public class JWTUser {
    private String id;

    public JWTUser() {

    }

    public JWTUser(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}