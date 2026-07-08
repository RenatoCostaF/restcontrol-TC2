package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserTypeException;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Entidade UserType")
class UserTypeTest {

    @Test
    @DisplayName("Deve criar tipo de usuário com dados válidos")
    void shouldCreateUserTypeWithValidData() {
        UserType userType = UserTypeTestHelper.validUserType();

        assertEquals(UserTypeTestHelper.VALID_USER_TYPE_ID, userType.getId());
        assertEquals(UserTypeTestHelper.VALID_NAME, userType.getName());
        assertEquals(UserTypeTestHelper.VALID_CODE, userType.getCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o nome for inválido")
    void shouldThrowWhenNameIsInvalid(String name) {
        InvalidUserTypeException exception = assertThrows(InvalidUserTypeException.class, () ->
                new UserType(UserTypeTestHelper.VALID_USER_TYPE_ID, name, UserTypeTestHelper.VALID_CODE)
        );

        assertEquals("Name is required", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o código for inválido")
    void shouldThrowWhenCodeIsInvalid(String code) {
        InvalidUserTypeException exception = assertThrows(InvalidUserTypeException.class, () ->
                new UserType(UserTypeTestHelper.VALID_USER_TYPE_ID, UserTypeTestHelper.VALID_NAME, code)
        );

        assertEquals("Code is required", exception.getMessage());
    }

    @Test
    @DisplayName("Deve identificar tipo RESTAURANT_OWNER")
    void shouldIdentifyRestaurantOwnerType() {
        UserType userType = UserTypeTestHelper.restaurantOwnerUserType();

        assertTrue(userType.isRestaurantOwner());
    }

    @Test
    @DisplayName("Deve identificar tipo RESTAURANT_OWNER ignorando maiúsculas/minúsculas")
    void shouldIdentifyRestaurantOwnerTypeCaseInsensitive() {
        UserType userType = new UserType(
                UserTypeTestHelper.VALID_USER_TYPE_ID,
                UserTypeTestHelper.RESTAURANT_OWNER_NAME,
                "restaurant_owner"
        );

        assertTrue(userType.isRestaurantOwner());
    }

    @Test
    @DisplayName("Deve identificar que CUSTOMER não é dono de restaurante")
    void shouldIdentifyCustomerIsNotRestaurantOwner() {
        UserType userType = UserTypeTestHelper.validUserType();

        assertFalse(userType.isRestaurantOwner());
    }
}
