package com.cc.workflow.data.mbr;

public interface MbrDAO {
    MbrUser createUser(MbrUser user);
    MbrUser getUser(String id);
    void deleteUser(String id);
    MbrUser updateUser(MbrUser user);
}
