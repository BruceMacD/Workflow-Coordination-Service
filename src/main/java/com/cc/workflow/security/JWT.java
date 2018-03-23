package com.cc.workflow.security;

public class JWT {
    private String token;

    public JWT(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}