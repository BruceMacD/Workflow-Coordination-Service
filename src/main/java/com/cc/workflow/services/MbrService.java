package com.cc.workflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.workflow.data.mbr.MortgageApplication;
import com.cc.workflow.data.mbr.MbrDAO;
import com.cc.workflow.data.User;
import javafx.util.Pair;

import com.cc.workflow.exceptions.InvalidMortgageApplication;
import com.cc.workflow.exceptions.UserNotFound;
import com.cc.workflow.security.PasswordHashUtility;

import java.util.Objects;
import java.util.UUID;

@Service
public class MbrService {

    @Autowired
    MbrDAO mbrDAO;

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

        mbrDAO.createUser(user);
        return user;
    }

    public User getUser(String id) {
        return mbrDAO.getUser(id);
    }

    public void deleteUser(String id) {
        mbrDAO.deleteUser(id);
    }

    public MortgageApplication apply(String id, MortgageApplication application) {
        verifyApplication(id, application);
        application.mortgageId = UUID.randomUUID().toString();
        mbrDAO.saveApplication(id, application);
        return application;
    }

    public MortgageApplication getApplication(String id) {
        return mbrDAO.getApplication(id);
    }

    private void verifyApplication(String id, MortgageApplication application) {
        try {
            getUser(id);
            UUID.fromString(application.mortgageInsuranceId);
            if (application.mortgageVal < 0 || !Objects.nonNull(application.name) || application.name.length() == 0) {
                throw new InvalidMortgageApplication();
            }
        } catch (IllegalArgumentException | UserNotFound e) {
            throw new InvalidMortgageApplication();
        }
    }
}