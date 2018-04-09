package com.cc.workflow.data.re;

import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;
import java.util.Map;

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
    public REUser getUserByMortgageId(String mortgageId) {
        REUser user = null;
        for (Map.Entry<String, REUser> pair: db.entrySet()) {
            if(pair.getValue().getAppraisal().mortgageId.equals(mortgageId))
                user = pair.getValue();
        }
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
    public REUser updateUser(REUser user) {
        db.put(user.getId(), user);
        return user;
    }
}
