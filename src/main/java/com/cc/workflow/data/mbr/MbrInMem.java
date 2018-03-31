package com.cc.workflow.data.mbr;

import com.cc.workflow.data.User;
import com.cc.workflow.exceptions.ApplicationNotFound;
import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;

public class MbrInMem implements MbrDAO {
    private static HashMap<String ,User> userDB = new HashMap<>();
    private static HashMap<String, MortgageApplication> applicationDB = new HashMap<>();

    @Override
    public User createUser(User user) {
        userDB.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(String id) {
        User user = userDB.get(id);
        if (user == null) {
            throw new UserNotFound();
        }
        return user;
    }

    @Override
    public void deleteUser(String id) {
        userDB.remove(id);
    }

    @Override
    public MortgageApplication saveApplication(String id, MortgageApplication application) {
        applicationDB.put(id, application);
        return application;
    }

    @Override
    public MortgageApplication getApplication(String id) {
        MortgageApplication application = applicationDB.get(id);
        if (application == null) {
            throw new ApplicationNotFound();
        }
        return application;
    }
}
