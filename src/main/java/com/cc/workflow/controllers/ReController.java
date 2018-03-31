package com.cc.workflow.controllers;

import com.cc.workflow.data.re.Appraisal;
import com.cc.workflow.data.re.REUser;
import com.cc.workflow.services.ReService;

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
@RequestMapping("/re")
public class ReController {

    @Autowired
    private ReService reService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public REUser getUser(@PathVariable String id) {
        return reService.getUser(id);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        reService.deleteUser(id);
    }

    @RequestMapping(
            value = "/{id}/appraisal",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    public Appraisal appraise(@PathVariable String id, @RequestBody Appraisal appraisal) {
        return reService.appraise(id, appraisal);
    }
}
