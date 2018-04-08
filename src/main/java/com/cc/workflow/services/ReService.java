package com.cc.workflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.workflow.data.re.Appraisal;
import com.cc.workflow.data.re.REUser;
import com.cc.workflow.data.re.ReDAO;
import com.cc.workflow.data.User;
import com.cc.workflow.security.PasswordHashUtility;

import java.util.AbstractMap;
import java.util.Random;
import java.util.UUID;

@Service
public class ReService {

    @Autowired
    ReDAO reDAO;

    @Autowired
    PasswordHashUtility pwUtils;

    public boolean authenticate(String id, String password) {
        User user = getUser(id);
        return null != user && pwUtils.passwordIsValid(password, user.getSalt(), user.getPassword());
    }

    public REUser createUser(REUser user) {
        user.setId(UUID.randomUUID().toString());
        AbstractMap.SimpleEntry<String, String> saltAndHashedPassword = pwUtils.getHashedPasswordAndSalt(user.getPassword());
        user.setPassword(saltAndHashedPassword.getValue());
        user.setSalt(saltAndHashedPassword.getKey());

        reDAO.createUser(user);
        return user;
    }

    public REUser getUser(String id) {
        return reDAO.getUser(id);
    }

    public void deleteUser(String id) {
        reDAO.deleteUser(id);
    }

    public Appraisal appraise(String id, Appraisal appraisal) {
        appraisal.value = (new Random().nextInt(100) + 1) * 10000;
        // TODO: CALL INS & MUN HERE
         REUser user = reDAO.getUser(id);
         user.setAppraisal(appraisal);
         reDAO.updateUser(user);
        return appraisal;
    }
}
