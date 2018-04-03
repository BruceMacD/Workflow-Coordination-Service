package com.cc.workflow.controllers;

import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.services.EmpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmpUser getUser(@PathVariable String id) {
        return empService.getUser(id);
    }

    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PATCH,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmpUser update(@PathVariable String id, @RequestBody EmpUser user) {
        return empService.modify(id, user);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        empService.deleteUser(id);
    }

    @RequestMapping(
            value = "/{id}/application/{mortgageId}",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    public EmpUser apply(@PathVariable String id, @PathVariable String mortgageId) {
        return empService.apply(id, mortgageId);
    }
}
