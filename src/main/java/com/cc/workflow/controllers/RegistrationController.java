package com.cc.workflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.cc.workflow.data.User;
import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.data.mbr.MbrUser;
import com.cc.workflow.exceptions.WorkflowError;
import com.cc.workflow.services.EmpService;
import com.cc.workflow.services.InsService;
import com.cc.workflow.services.MbrService;
import com.cc.workflow.services.ReService;

@CrossOrigin
@RestController
@RequestMapping(value = {"/emp/register", "/ins/register", "/mbr/register", "/re/register"})
public class RegistrationController {

    @Autowired
    private MbrService mbrService;

    @Autowired
    private EmpService empService;

    @Autowired
    private ReService reService;

    @Autowired
    private InsService insService;

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user , HttpServletRequest request) {
        String url = request.getServletPath();

        switch (url) {
            case "/emp/register":
                return empService.createUser((EmpUser) user);
            case "/ins/register":
                return insService.createUser(user);
            case "/mbr/register":
                return mbrService.createUser((MbrUser) user);
            case "/re/register":
                return reService.createUser(user);
            default:
                throw new WorkflowError();
        }
    }
}
