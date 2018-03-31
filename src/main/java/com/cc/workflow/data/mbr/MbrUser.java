package com.cc.workflow.data.mbr;

import com.cc.workflow.data.User;

public class MbrUser extends User {
    private MortgageApplication application;

    public MortgageApplication getApplication() {
        return application;
    }

    public void setApplication(MortgageApplication application) {
        this.application = application;
    }
}
