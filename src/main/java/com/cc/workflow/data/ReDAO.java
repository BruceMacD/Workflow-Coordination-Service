package com.cc.workflow.data;

public interface ReDAO {
    User createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
}
