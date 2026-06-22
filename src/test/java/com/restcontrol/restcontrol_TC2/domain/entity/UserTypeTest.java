package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTypeTest {

    @Test
    @DisplayName("Should create user type with valid name")
    void shouldCreateUserTypeWithValidName() {
        var name = "Has full access to the system";

        var userType = UserType.create(name);

        assertNull(userType.getId());
        assertEquals(name, userType.getName());
    }

    @Test
    @DisplayName("Should reject null name on create")
    void shouldRejectNullNameOnCreate() {
        assertThrows(InvalidUserTypeException.class, () -> UserType.create(null));
    }

    @Test
    @DisplayName("Should reject null name on setter")
    void shouldRejectNullNameOnSetter() {
        var userType = UserType.create("Administrator");

        assertThrows(InvalidUserTypeException.class, () -> userType.setName(null));
    }
}
