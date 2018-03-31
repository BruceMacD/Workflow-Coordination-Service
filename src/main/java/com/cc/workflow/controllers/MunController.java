package com.cc.workflow.controllers;

import com.cc.workflow.data.mun.MUNServices;
import com.cc.workflow.services.MunService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/mun")
public class MunController {

    @Autowired
    private MunService munService;

    @RequestMapping(
            value = "/services/{mortgageId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MUNServices getServices(@PathVariable String mortgageId) {
        return munService.getServices(mortgageId);
    }
}
