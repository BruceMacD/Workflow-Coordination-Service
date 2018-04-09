package com.cc.workflow.controllers;

import com.cc.workflow.data.re.Appraisal;
import com.cc.workflow.data.re.REUser;
import com.cc.workflow.services.ReService;

import com.cc.workflow.services.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    final String AWS = "http://cloundfinalserver-env.us-east-1.elasticbeanstalk.com";

    @Autowired
    private ReService reService;

    @Autowired
    private WorkflowService workflowService;

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
            value = "mortgage/{mortgageId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public REUser getUserByMortgageId(@PathVariable String mortgageId) {
        return reService.getUserByMortgageId(mortgageId);
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

    @RequestMapping(
            value = "/subscribe/{mortgageId}",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> setWebhook(@PathVariable String mortgageId, @RequestBody String callbackUrl) {
        workflowService.addRealEstateCallback(mortgageId, callbackUrl);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("location", AWS + "/re/timeout/" + mortgageId);
        responseHeaders.set("retry-after", "60");
        return new ResponseEntity<String>("", responseHeaders, HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value = "/timeout/{mortgageId}",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> timeoutWebhook(@PathVariable String mortgageId) {
        if (!workflowService.realEstateCallbacksContains(mortgageId)) {
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", AWS + "/re/timeout/" + mortgageId);
            responseHeaders.set("retry-after", "60");
            return new ResponseEntity<String>("", responseHeaders, HttpStatus.ACCEPTED);
        }
    }
}
