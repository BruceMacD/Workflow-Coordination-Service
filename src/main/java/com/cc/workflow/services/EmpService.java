package com.cc.workflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.workflow.data.User;
import javafx.util.Pair;

import com.cc.workflow.data.emp.EmpDAO;
import com.cc.workflow.security.PasswordHashUtility;

import java.util.UUID;

@Service
public class EmpService {

    @Autowired
    EmpDAO empDAO;

    @Autowired
    PasswordHashUtility pwUtils;

    public boolean authenticate(String id, String password) {
        User user = getUser(id);
        return null != user && pwUtils.passwordIsValid(password, user.getSalt(), user.getPassword());
    }

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        Pair<String, String> saltAndHashedPassword = pwUtils.getHashedPasswordAndSalt(user.getPassword());
        user.setPassword(saltAndHashedPassword.getValue());
        user.setSalt(saltAndHashedPassword.getKey());

        empDAO.createUser(user);
        return user;
    }

    public User getUser(String id) {
        return empDAO.getUser(id);
    }

    public void deleteUser(String id) {
        empDAO.deleteUser(id);
    }
}
