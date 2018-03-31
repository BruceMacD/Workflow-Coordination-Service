package com.cc.workflow.data.ins;

import com.cc.workflow.data.User;

public interface InsDAO {
    User createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
}
