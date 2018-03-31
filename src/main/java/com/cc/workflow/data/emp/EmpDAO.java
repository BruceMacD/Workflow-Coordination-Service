package com.cc.workflow.data.emp;

import com.cc.workflow.data.User;

public interface EmpDAO {
    User createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
}
