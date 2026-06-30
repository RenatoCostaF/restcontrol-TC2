package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserException;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

    @Test
    @DisplayName("Should create a user with valid data")
    void shouldCreateUserWithValidData() {
        User user = UserTestFixtures.validUser();

        Assert.assertEquals(UserTestFixtures.VALID_USER_ID, user.getId());
        Assert.assertEquals(UserTestFixtures.VALID_NAME, user.getName());
        Assert.assertEquals(UserTestFixtures.VALID_EMAIL, user.getEmail());
        Assert.assertEquals(UserTestFixtures.VALID_PASSWORD, user.getPassword());
        Assert.assertEquals(UserTestFixtures.VALID_USER_TYPE_ID, user.getUserTypeId());
    }

    @Test
    @DisplayName("Should throw an exception when name is null")
    void shouldThrowWhenNameIsNull() {
        InvalidUserException exception = Assert.assertThrows(InvalidUserException.class, () ->
                new User(null, null, UserTestFixtures.VALID_EMAIL, UserTestFixtures.VALID_PASSWORD, UserTestFixtures.VALID_USER_TYPE_ID)
        );

        Assert.assertEquals("Name cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalid-email", "user@", "@example.com", "user@.com"})
    @DisplayName("Should throw an exception when email is invalid")
    void shouldThrowWhenEmailIsInvalid(String email) {
        InvalidUserException exception = Assert.assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, email, UserTestFixtures.VALID_PASSWORD, UserTestFixtures.VALID_USER_TYPE_ID)
        );

        Assert.assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw an exception when password is null")
    void shouldThrowWhenPasswordIsNull() {
        InvalidUserException exception = Assert.assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, UserTestFixtures.VALID_EMAIL, null, UserTestFixtures.VALID_USER_TYPE_ID)
        );

        Assert.assertEquals("The password must contain at least 8 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw an exception when user type ID is null")
    void shouldThrowWhenUserTypeIdIsNull() {
        InvalidUserException exception = Assert.assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, UserTestFixtures.VALID_EMAIL, UserTestFixtures.VALID_PASSWORD, null)
        );

        Assert.assertEquals("User type cannot be null", exception.getMessage());
    }
}
