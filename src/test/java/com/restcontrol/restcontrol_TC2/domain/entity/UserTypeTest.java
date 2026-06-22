package com.restcontrol.restcontrol_TC2.domain.entity;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class UserTypeTest {

    @DisplayName("Create UserType success")
    @Test
    public void createUserTypeSuccess() {
        var name = "Has full access to the system";

        var userType = UserType.create(name);

        Assertions.assertNull(userType.getId());
        Assertions.assertEquals(userType.getName(), name);
    }
}
