package com.cc.workflow.data;

import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;

public class MbrInMem implements MbrDAO {
    private static HashMap<String ,User> db = new HashMap<>();

    @Override
    public User createUser(User user) {
        db.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUser(String id) {
        User user = db.get(id);
        if (user == null) {
            throw new UserNotFound();
        }
        return user;
    }

    @Override
    public void deleteUser(String id) {
        db.remove(id);
    }
}
