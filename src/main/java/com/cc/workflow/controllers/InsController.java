package com.cc.workflow.controllers;

import com.cc.workflow.data.ins.InsuranceQuote;
import com.cc.workflow.data.mun.MUNServices;
import com.cc.workflow.data.re.Appraisal;
import com.cc.workflow.services.InsService;

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

@CrossOrigin
@RestController
@RequestMapping("/ins")
public class InsController {

    @Autowired
    private InsService insService;

    @RequestMapping(
            value = "/munInfo",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public InsuranceQuote updateMunInfo(@RequestBody MUNServices services) {
        return insService.updateMunInfo(services);
    }

    @RequestMapping(
            value = "/reInfo",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public InsuranceQuote updateReInfo(@RequestBody Appraisal appraisal) {
        return insService.updateReInfo(appraisal);
    }
}
