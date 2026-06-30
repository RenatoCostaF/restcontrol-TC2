package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserTestFixtures.validUser();
    }

    @Test
    @DisplayName("Should create user when user type exists")
    void shouldCreateUserWhenUserTypeExists() {
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.of(UserTestFixtures.validUserType()));
        when(userGateway.create(user)).thenReturn(user);

        User result = userUseCase.create(user);

        assertEquals(user, result);
        verify(userTypeGateway).getById(user.getUserTypeId());
        verify(userGateway).create(user);
    }

    @Test
    @DisplayName("Should throw an exception when trying to create a user with invalid user type")
    void shouldThrowWhenCreatingUserWithInvalidUserType() {
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> userUseCase.create(user));

        verify(userGateway, never()).create(user);
    }

    @Test
    @DisplayName("Should update an user when user and user type exist")
    void shouldUpdateUserWhenUserAndUserTypeExist() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.of(UserTestFixtures.validUserType()));
        when(userGateway.update(user)).thenReturn(user);

        User result = userUseCase.update(user);

        assertEquals(user, result);
        verify(userGateway).getById(user.getId());
        verify(userTypeGateway).getById(user.getUserTypeId());
        verify(userGateway).update(user);
    }

    @Test
    @DisplayName("Should throw an exception when trying to update a non existent user")
    void shouldThrowWhenUpdatingNonExistentUser() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.update(user));

        verify(userGateway, never()).update(user);
    }

    @Test
    @DisplayName("Should get user by ID")
    void shouldGetUserById() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));

        User result = userUseCase.getById(user.getId());

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Should throw an exception when getting a non existent user")
    void shouldThrowWhenGettingNonExistentUser() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getById(user.getId()));
    }

    @Test
    @DisplayName("Should delete user when user exists")
    void shouldDeleteUserWhenExists() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));

        userUseCase.delete(user.getId());

        verify(userGateway).getById(user.getId());
        verify(userGateway).delete(user.getId());
    }

    @Test
    @DisplayName("Should throw an exception when trying to delete a non existent user")
    void shouldThrowWhenDeletingNonExistentUser() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.delete(user.getId()));

        verify(userGateway, never()).delete(user.getId());
    }
}
