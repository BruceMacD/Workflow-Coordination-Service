package com.cc.workflow.controllers;

import com.cc.workflow.data.User;
import com.cc.workflow.data.mbr.MortgageApplication;
import com.cc.workflow.services.MbrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/mbr")
public class MbrController {

    @Autowired
    private MbrService mbrService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User getUser(@PathVariable String id) {
        return mbrService.getUser(id);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        mbrService.deleteUser(id);
    }

    @RequestMapping(
            value = "/{id}/apply",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MortgageApplication apply(@PathVariable String id, @RequestBody MortgageApplication application) {
        return mbrService.apply(id, application);
    }

    @RequestMapping(
            value = "/{id}/application",
            method = RequestMethod.GET
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MortgageApplication getApplication(@PathVariable String id) {
        return mbrService.getApplication(id);
    }
}
