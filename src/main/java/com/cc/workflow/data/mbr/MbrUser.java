package com.cc.workflow.data.mbr;

import com.cc.workflow.data.User;
import com.cc.workflow.data.emp.EmpUser;

public class MbrUser extends User {
    private MortgageApplication application;
    private EmpUser empInfo;

    public MortgageApplication getApplication() {
        return application;
    }

    public void setApplication(MortgageApplication application) {
        this.application = application;
    }

    public EmpUser getEmpInfo() {
        return empInfo;
    }

    public void setEmpInfo(EmpUser empInfo) {
        this.empInfo = empInfo;
    }
}
