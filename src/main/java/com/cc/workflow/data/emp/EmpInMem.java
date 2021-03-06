package com.cc.workflow.data.emp;

import com.cc.workflow.exceptions.UserNotFound;

import java.util.HashMap;
import java.util.Map;

public class EmpInMem implements EmpDAO {
    private static HashMap<String, EmpUser> db = new HashMap<>();

    @Override
    public EmpUser createUser(EmpUser user) {
        db.put(user.getId(), user);
        return user;
    }

    @Override
    public EmpUser getUser(String id) {
        EmpUser user = db.get(id);
        if (user == null) {
            throw new UserNotFound();
        }
        return user;
    }

    @Override
    public EmpUser getUserByMortgageId(String mortgageId) {
        EmpUser user = null;
        for (Map.Entry<String, EmpUser> pair: db.entrySet()) {
            if(pair.getValue().getMortgageId().equals(mortgageId))
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
    public EmpUser updateUser(String id, EmpUser user) {
        if (db.get(id) == null) {
            throw new UserNotFound();
        }
        db.put(id, user);
        return user;
    }
}
