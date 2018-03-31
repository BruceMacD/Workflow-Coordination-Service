package com.cc.workflow.data.ins;

import java.util.HashMap;

public class InsInMem implements InsDAO {
    private static HashMap<String ,InsuranceQuote> db = new HashMap<>();

    @Override
    public InsuranceQuote getQuote(String mortgageId) {
        return db.get(mortgageId);
    }

    @Override
    public InsuranceQuote updateQuote(InsuranceQuote quote) {
        return db.put(quote.mortgageId, quote);
    }
}
