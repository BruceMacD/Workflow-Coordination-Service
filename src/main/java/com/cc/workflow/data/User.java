package com.cc.workflow.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class User implements Serializable {
    @NotNull
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    public User() {
    }

    // used for copying to perform function of a downcast
    public User(User user) {
        this.id = user.id;
        this.password = user.password;
        this.salt = user.salt;
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}