package com.cc.workflow.data.re;

public interface ReDAO {
    REUser createUser(REUser user);
    REUser getUser(String id);
    REUser getUserByMortgageId(String mortgageId);
    void deleteUser(String id);
    REUser updateUser(REUser user);
}
