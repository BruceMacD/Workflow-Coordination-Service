package com.cc.workflow.data.re;

import com.cc.workflow.data.User;

public class REUser extends User {
    private Appraisal appraisal;

    public Appraisal getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
    }
}
