package com.restcontrol.restcontrol_TC2.domain.entity;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.UUID;

public class UserTest {

    @DisplayName("Create User success")
    @Test
    public void createUserSuccess() {
        var name = "Test User";
        var email = "test@gmail.com";
        var password = "password123";
        var randomUUID = UUID.randomUUID();
        var userType = UserType.create("Has full access to the system");
        userType.setId(randomUUID.toString());

        var user = User.create(
                name,
                email,
                password,
                userType
        );

        Assertions.assertEquals(user.getName(), name);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getPassword(), password);
        Assertions.assertEquals(user.getUserType(), userType);

    }

}

