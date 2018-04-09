package com.cc.workflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.workflow.data.ins.InsDAO;

import com.cc.workflow.data.ins.InsuranceQuote;
import com.cc.workflow.data.mun.MUNServices;
import com.cc.workflow.data.re.Appraisal;

import java.util.Random;

@Service
public class InsService {

    @Autowired
    InsDAO insDAO;

    public InsuranceQuote updateMunInfo(MUNServices services) {
        InsuranceQuote quote = insDAO.getQuote(services.mortgageId);
        if (quote == null) {
            quote = new InsuranceQuote();
            quote.mortgageId = services.mortgageId;
            quote.deductible = new Random().nextInt(100);
            quote.insuredValue = new Random().nextInt(100);
        }

        quote.receivedMun = true;

        insDAO.updateQuote(quote);

        return quote;
    }

    public InsuranceQuote updateReInfo(Appraisal appraisal) {
        InsuranceQuote quote = insDAO.getQuote(appraisal.mortgageId);
        if (quote == null) {
            quote = new InsuranceQuote();
            quote.mortgageId = appraisal.mortgageId;
            quote.deductible = new Random().nextInt(100);
            quote.insuredValue = new Random().nextInt(100);
        }

        quote.name = appraisal.name;
        quote.mortgageInsuranceId = appraisal.mortgageInsuranceId;
        quote.receivedRe = true;

        insDAO.updateQuote(quote);

        return quote;
    }
}
