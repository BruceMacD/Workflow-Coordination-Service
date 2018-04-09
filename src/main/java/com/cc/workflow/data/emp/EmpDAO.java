package com.cc.workflow.data.emp;

public interface EmpDAO {
    EmpUser createUser(EmpUser user);
    EmpUser getUser(String id);
    EmpUser getUserByMortgageId(String mortgageId);
    void deleteUser(String id);
    EmpUser updateUser(String id, EmpUser user);
}
