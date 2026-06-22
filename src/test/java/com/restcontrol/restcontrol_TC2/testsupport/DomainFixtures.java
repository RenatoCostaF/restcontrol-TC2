package com.restcontrol.restcontrol_TC2.testsupport;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

import java.util.UUID;

public final class DomainFixtures {

    private DomainFixtures() {
    }

    public static UserType validUserType() {
        var userType = UserType.create("Administrator");
        userType.setId(UUID.randomUUID().toString());
        return userType;
    }
}
