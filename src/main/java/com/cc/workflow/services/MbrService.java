package com.cc.workflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.data.ins.InsuranceQuote;
import com.cc.workflow.data.mbr.MbrUser;
import com.cc.workflow.data.mbr.MortgageApplication;
import com.cc.workflow.data.mbr.MbrDAO;
import com.cc.workflow.data.User;
import javafx.util.Pair;

import com.cc.workflow.data.mun.MUNServices;
import com.cc.workflow.exceptions.AlreadyApplied;
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

    public MbrUser createUser(MbrUser user) {
        user.setId(UUID.randomUUID().toString());
        Pair<String, String> saltAndHashedPassword = pwUtils.getHashedPasswordAndSalt(user.getPassword());
        user.setPassword(saltAndHashedPassword.getValue());
        user.setSalt(saltAndHashedPassword.getKey());

        mbrDAO.createUser(user);
        return user;
    }

    public MbrUser getUser(String id) {
        return mbrDAO.getUser(id);
    }

    public void deleteUser(String id) {
        mbrDAO.deleteUser(id);
    }

    public MbrUser apply(String id, MortgageApplication application) {
        verifyApplication(id, application);
        application.mortgageId = UUID.randomUUID().toString();
        MbrUser user = mbrDAO.getUser(id);
        if (user.getApplication() != null) {
            throw new AlreadyApplied();
        }
        user.setApplication(application);
        mbrDAO.updateUser(user);
        return user;
    }

    public MbrUser updateEmpInfo(String id, EmpUser empinfo) {
        MbrUser user = getUser(id);
        user.setEmpInfo(empinfo);
        mbrDAO.updateUser(user);
        return user;
    }

    public MbrUser updateMunInfo(MUNServices services) {
        MbrUser user = mbrDAO.getUserByMortgageId(services.mortgageId);
        user.setMunInfo(services);
        mbrDAO.updateUser(user);
        return user;
    }

    public MbrUser updateInsInfo(InsuranceQuote quote) {
        MbrUser user = mbrDAO.getUserByMortgageId(quote.mortgageId);
        user.setInsuranceQuote(quote);
        mbrDAO.updateUser(user);
        return user;
    }

    private void verifyApplication(String id, MortgageApplication application) {
        try {
            UUID.fromString(application.mortgageInsuranceId);
            if (application.mortgageVal < 0 || !Objects.nonNull(application.name) || application.name.length() == 0) {
                throw new InvalidMortgageApplication();
            }
        } catch (IllegalArgumentException | UserNotFound e) {
            throw new InvalidMortgageApplication();
        }
    }
}