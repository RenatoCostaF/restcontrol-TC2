package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserException;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Entidade User")
class UserTest {

    @Test
    @DisplayName("Deve criar usuário com dados válidos")
    void shouldCreateUserWithValidData() {
        User user = UserTestFixtures.validUser();

        assertEquals(UserTestFixtures.VALID_USER_ID, user.getId());
        assertEquals(UserTestFixtures.VALID_NAME, user.getName());
        assertEquals(UserTestFixtures.VALID_EMAIL, user.getEmail());
        assertEquals(UserTestFixtures.VALID_PASSWORD, user.getPassword());
        assertEquals(UserTestFixtures.VALID_USER_TYPE_ID, user.getUserTypeId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo")
    void shouldThrowWhenNameIsNull() {
        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                new User(null, null, UserTestFixtures.VALID_EMAIL, UserTestFixtures.VALID_PASSWORD, UserTestFixtures.VALID_USER_TYPE_ID)
        );

        assertEquals("Name cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalid-email", "user@", "@example.com", "user@.com"})
    @DisplayName("Deve lançar exceção quando o e-mail for inválido: {0}")
    void shouldThrowWhenEmailIsInvalid(String email) {
        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, email, UserTestFixtures.VALID_PASSWORD, UserTestFixtures.VALID_USER_TYPE_ID)
        );

        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for nula")
    void shouldThrowWhenPasswordIsNull() {
        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, UserTestFixtures.VALID_EMAIL, null, UserTestFixtures.VALID_USER_TYPE_ID)
        );

        assertEquals("The password must contain at least 8 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha tiver menos de 8 caracteres")
    void shouldThrowWhenPasswordIsTooShort() {
        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, UserTestFixtures.VALID_EMAIL, "short", UserTestFixtures.VALID_USER_TYPE_ID)
        );

        assertEquals("The password must contain at least 8 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de usuário for nulo")
    void shouldThrowWhenUserTypeIdIsNull() {
        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                new User(null, UserTestFixtures.VALID_NAME, UserTestFixtures.VALID_EMAIL, UserTestFixtures.VALID_PASSWORD, null)
        );

        assertEquals("User type cannot be null", exception.getMessage());
    }
}
