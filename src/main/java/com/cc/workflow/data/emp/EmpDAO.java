package com.cc.workflow.data.emp;

public interface EmpDAO {
    EmpUser createUser(EmpUser user);
    EmpUser getUser(String id);
    void deleteUser(String id);
    EmpUser updateUser(String id, EmpUser user);
}
