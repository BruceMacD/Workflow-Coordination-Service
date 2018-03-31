package com.cc.workflow.data.ins;

public interface InsDAO {
    InsuranceQuote getQuote(String mortgageId);
    InsuranceQuote updateQuote(InsuranceQuote quote);
}
