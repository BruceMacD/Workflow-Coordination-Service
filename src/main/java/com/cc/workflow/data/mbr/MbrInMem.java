package com.cc.workflow.data.mbr;

import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;

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
        return userDB.values()
                .stream()
                .filter(x -> x.getApplication().mortgageId.equals(mortgageId))
                .findFirst()
                .orElseThrow(UserNotFound::new);
    }
}
