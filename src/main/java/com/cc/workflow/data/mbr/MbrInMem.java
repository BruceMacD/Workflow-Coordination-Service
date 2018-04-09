package com.cc.workflow.data.mbr;

import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;
import java.util.Map;

public class MbrInMem implements MbrDAO {
    private static HashMap<String, MbrUser> userDB = new HashMap<>();

    @Override
    public MbrUser createUser(MbrUser user) {
        userDB.put(user.getId(), user);
        return user;
    }

    @Override
    public MbrUser getUser(String id) {
        MbrUser user = userDB.get(id);
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
    public MbrUser updateUser(MbrUser user) {
        userDB.put(user.getId(), user);
        return user;
    }

    @Override
    public MbrUser getUserByMortgageId(String mortgageId) {
        MbrUser user = null;
        for (Map.Entry<String, MbrUser> pair: userDB.entrySet()) {
            // needed to check for null application
            if(pair.getValue().getApplication() != null && pair.getValue().getApplication().mortgageId.equals(mortgageId)) {
                user = pair.getValue();
            }
        }
        if (user == null) {
            throw new UserNotFound();
        }
        return user;
    }
}
