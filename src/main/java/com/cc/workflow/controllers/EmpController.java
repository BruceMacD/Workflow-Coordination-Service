package com.cc.workflow.controllers;

import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.services.EmpService;
import com.cc.workflow.services.WorkflowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/emp")
public class EmpController {

    final String AWS = "http://cloundfinalserver-env.us-east-1.elasticbeanstalk.com";

    @Autowired
    private EmpService empService;

    @Autowired
    private WorkflowService workflowService;

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
            value = "mortgage/{mortgageId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmpUser getUserByMortgageId(@PathVariable String mortgageId) {
        return empService.getUserByMortgageId(mortgageId);
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

    @RequestMapping(
            value = "/subscribe/{mortgageId}",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> setWebhook(@PathVariable String mortgageId, @RequestBody String callbackUrl) {
        workflowService.addEmployerCallback(mortgageId, callbackUrl);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("location", AWS + "/emp/timeout/" + mortgageId);
        responseHeaders.set("retry-after", "60");
        return new ResponseEntity<>("", responseHeaders, HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/timeout/{mortgageId}",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> timeoutWebhook(@PathVariable String mortgageId) {
        if (!workflowService.employerCallbacksContains(mortgageId)) {
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", AWS + "/emp/timeout/" + mortgageId);
            responseHeaders.set("retry-after", "60");
            return new ResponseEntity<String>("", responseHeaders, HttpStatus.ACCEPTED);
        }
    }

}
