package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.helper.UserTestHelper;
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
@DisplayName("Use case - User")
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
        user = UserTestHelper.validUser();
    }

    @Test
    @DisplayName("Deve criar usuário quando o tipo de usuário existir")
    void shouldCreateUserWhenUserTypeExists() {
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.of(UserTestHelper.validUserType()));
        when(userGateway.create(user)).thenReturn(user);

        User result = userUseCase.create(user);

        assertEquals(user, result);
        verify(userTypeGateway).getById(user.getUserTypeId());
        verify(userGateway).create(user);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com tipo de usuário inexistente")
    void shouldThrowWhenCreatingUserWithInvalidUserType() {
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> userUseCase.create(user));

        verify(userGateway, never()).create(user);
    }

    @Test
    @DisplayName("Deve atualizar usuário quando usuário e tipo de usuário existirem")
    void shouldUpdateUserWhenUserAndUserTypeExist() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.of(UserTestHelper.validUserType()));
        when(userGateway.update(user)).thenReturn(user);

        User result = userUseCase.update(user);

        assertEquals(user, result);
        verify(userGateway).getById(user.getId());
        verify(userTypeGateway).getById(user.getUserTypeId());
        verify(userGateway).update(user);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void shouldThrowWhenUpdatingNonExistentUser() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.update(user));

        verify(userGateway, never()).update(user);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário com tipo de usuário inexistente")
    void shouldThrowWhenUpdatingUserWithInvalidUserType() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));
        when(userTypeGateway.getById(user.getUserTypeId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> userUseCase.update(user));

        verify(userGateway, never()).update(user);
    }

    @Test
    @DisplayName("Deve buscar usuário por ID")
    void shouldGetUserById() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));

        User result = userUseCase.getById(user.getId());

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente")
    void shouldThrowWhenGettingNonExistentUser() {
        String userId = user.getId();
        when(userGateway.getById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getById(userId));
    }

    @Test
    @DisplayName("Deve remover usuário quando existir")
    void shouldDeleteUserWhenExists() {
        when(userGateway.getById(user.getId())).thenReturn(Optional.of(user));

        userUseCase.delete(user.getId());

        verify(userGateway).getById(user.getId());
        verify(userGateway).delete(user.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover usuário inexistente")
    void shouldThrowWhenDeletingNonExistentUser() {
        String userId = user.getId();
        when(userGateway.getById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.delete(userId));

        verify(userGateway, never()).delete(userId);
    }
}
