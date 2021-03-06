package com.cc.workflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.workflow.data.User;

import com.cc.workflow.data.emp.EmpDAO;
import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.exceptions.AlreadyApplied;
import com.cc.workflow.exceptions.InvalidEmpUser;
import com.cc.workflow.security.PasswordHashUtility;

import java.util.AbstractMap;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmpService {

    @Autowired
    EmpDAO empDAO;

    @Autowired
    PasswordHashUtility pwUtils;

    @Autowired
    WorkflowService workflowService;

    public boolean authenticate(String id, String password) {
        User user = getUser(id);
        return null != user && pwUtils.passwordIsValid(password, user.getSalt(), user.getPassword());
    }

    public EmpUser createUser(EmpUser user) {
        // Had to remove this validation as a user object wont have all the fields needed for validating
        // validateUser(user);
        user.setId(UUID.randomUUID().toString());
        AbstractMap.SimpleEntry<String, String> saltAndHashedPassword = pwUtils.getHashedPasswordAndSalt(user.getPassword());
        user.setPassword(saltAndHashedPassword.getValue());
        user.setSalt(saltAndHashedPassword.getKey());

        empDAO.createUser(user);
        return user;
    }

    public EmpUser getUser(String id) {
        return empDAO.getUser(id);
    }

    public EmpUser getUserByMortgageId(String mortgageId) {
        return empDAO.getUserByMortgageId(mortgageId);
    }

    public EmpUser modify(String id, EmpUser user) {
        EmpUser patchedUser = getUser(id);
        patchedUser.setName(user.getName());
        patchedUser.setSalary(user.getSalary());
        patchedUser.setEmploymentStartDate(user.getEmploymentStartDate());

        empDAO.updateUser(id, patchedUser);
        return patchedUser;
    }

    public void deleteUser(String id) {
        empDAO.deleteUser(id);
    }

    public EmpUser apply(String id, String mortgageId) {
        EmpUser user = empDAO.getUser(id);
        if (user.isApplied()) {
            throw new AlreadyApplied();
        }
        // workaround for setting mortgageId
        user.setMortgageId(mortgageId);
        modify(user.getId(), user);
        workflowService.triggerEmployee(mortgageId);
        user.setApplied(true);
        empDAO.updateUser(id, user);
        return user;
    }

    private void validateUser(EmpUser user) {
        if (!Objects.nonNull(user.getEmploymentStartDate()) ||
                user.getEmploymentStartDate().length() == 0 ||
                !Objects.nonNull(user.getName()) ||
                user.getName().length() == 0 ||
                user.getSalary() < 0) {
            throw new InvalidEmpUser();
        }
    }
}
