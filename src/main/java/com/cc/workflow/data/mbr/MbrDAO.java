package com.cc.workflow.data.mbr;

import com.cc.workflow.data.User;

public interface MbrDAO {
    User createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
    MortgageApplication saveApplication(String id, MortgageApplication application);
    MortgageApplication getApplication(String id);
}
