package com.cc.workflow.data.emp;

import com.cc.workflow.data.User;

public class EmpUser extends User {
    private String name;
    private int salary;
    private String employmentStartDate;
    private boolean applied;

    public EmpUser(User user) {
        super(user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(String employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }
}
