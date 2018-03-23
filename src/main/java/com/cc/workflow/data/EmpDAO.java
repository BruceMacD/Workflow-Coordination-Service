package com.cc.workflow.data;

import com.cc.workflow.data.User;

public interface EmpDAO {
    public User createUser(User user);
    public User getUser(String id);
    public void deleteUser(String id);
}
