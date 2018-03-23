package com.cc.workflow.controllers;

import com.cc.workflow.exceptions.AuthError;
import com.cc.workflow.security.AuthDTO;
import com.cc.workflow.security.JWT;
import com.cc.workflow.services.AuthService;
import com.cc.workflow.security.JWTUser;
import com.cc.workflow.services.EmpService;
import com.cc.workflow.services.InsService;
import com.cc.workflow.services.MbrService;
import com.cc.workflow.services.ReService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class AuthController {
    @Autowired
    private MbrService mbrService;

    @Autowired
    private EmpService empService;

    @Autowired
    private ReService reService;

    @Autowired
    private InsService insService;

    @Autowired
    private AuthService jwtService;

    @PostMapping(
            value = {"/emp/auth", "/ins/auth", "/mbr/auth", "/re/auth"},
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JWT auth(@RequestBody AuthDTO auth, HttpServletRequest request) {
        String url = request.getServletPath();

        String id = auth.getId();
        String password = auth.getPassword();
        Boolean correctCredentials;

        switch (url) {
            case "/emp/auth":
                correctCredentials = empService.authenticate(id, password);
                break;
            case "/ins/auth":
                correctCredentials = insService.authenticate(id, password);
                break;
            case "/mbr/auth":
                correctCredentials = mbrService.authenticate(id, password);
                break;
            case "/re/auth":
                correctCredentials = reService.authenticate(id, password);
                break;
            default:
                throw new AuthError();
        }

        if (correctCredentials) {
            JWTUser jwtUser = new JWTUser(id);
            return new JWT(jwtService.getToken(jwtUser));
        } else {
            throw new AuthError();
        }
    }
}
