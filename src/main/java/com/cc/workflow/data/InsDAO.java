package com.cc.workflow.data;

public interface InsDAO {
    User createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
}
