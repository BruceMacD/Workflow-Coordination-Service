package com.cc.workflow.data.mbr;

import com.cc.workflow.data.User;
import com.cc.workflow.data.emp.EmpUser;
import com.cc.workflow.data.mun.MUNServices;

public class MbrUser extends User {
    private MortgageApplication application;
    private EmpUser empInfo;
    private MUNServices munInfo;

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

    public MUNServices getMunInfo() {
        return munInfo;
    }

    public void setMunInfo(MUNServices munInfo) {
        this.munInfo = munInfo;
    }
}
