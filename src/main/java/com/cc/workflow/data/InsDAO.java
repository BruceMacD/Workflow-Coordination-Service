package com.cc.workflow.data;

public interface InsDAO {
    public User createUser(User user);
    public User getUser(String id);
    public void deleteUser(String id);
}
