package com.cc.workflow.data;

public interface EmpDAO {
    User createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
}
