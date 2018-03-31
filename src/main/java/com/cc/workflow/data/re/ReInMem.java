package com.cc.workflow.data.re;

import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;

public class ReInMem implements ReDAO {
    private static HashMap<String ,REUser> db = new HashMap<>();

    @Override
    public REUser createUser(REUser user) {
        db.put(user.getId(), user);
        return user;
    }

    @Override
    public REUser getUser(String id) {
        REUser user = db.get(id);
        if (user == null) {
            throw new UserNotFound();
        }
        return user;
    }

    @Override
    public void deleteUser(String id) {
        db.remove(id);
    }

    @Override
    public REUser updateUser(String id, Appraisal appraisal) {
        REUser user = getUser(id);
        user.setAppraisal(appraisal);
        db.put(id, user);
        return user;
    }
}
