package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Use case - UserType")
class UserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private UserTypeUseCaseImpl userTypeUseCase;

    private UserType userType;

    @BeforeEach
    void setUp() {
        userType = UserTypeTestHelper.validUserType();
    }

    @Test
    @DisplayName("Deve criar tipo de usuário")
    void shouldCreateUserType() {
        when(userTypeGateway.create(userType)).thenReturn(userType);

        UserType result = userTypeUseCase.create(userType);

        assertEquals(userType, result);
        verify(userTypeGateway).create(userType);
    }

    @Test
    @DisplayName("Deve atualizar tipo de usuário quando existir")
    void shouldUpdateUserTypeWhenExists() {
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));
        when(userTypeGateway.update(userType)).thenReturn(userType);

        UserType result = userTypeUseCase.update(userType);

        assertEquals(userType, result);
        verify(userTypeGateway).getById(userType.getId());
        verify(userTypeGateway).update(userType);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar tipo de usuário inexistente")
    void shouldThrowWhenUpdatingNonExistentUserType() {
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> userTypeUseCase.update(userType));

        verify(userTypeGateway, never()).update(userType);
    }

    @Test
    @DisplayName("Deve listar todos os tipos de usuário")
    void shouldListAllUserTypes() {
        when(userTypeGateway.listAll()).thenReturn(List.of(userType));

        List<UserType> result = userTypeUseCase.listAll();

        assertEquals(1, result.size());
        assertEquals(userType, result.getFirst());
    }

    @Test
    @DisplayName("Deve buscar tipo de usuário por ID")
    void shouldGetUserTypeById() {
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));

        UserType result = userTypeUseCase.getById(userType.getId());

        assertEquals(userType, result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar tipo de usuário inexistente")
    void shouldThrowWhenGettingNonExistentUserType() {
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> userTypeUseCase.getById(userType.getId()));
    }

    @Test
    @DisplayName("Deve remover tipo de usuário quando existir")
    void shouldDeleteUserTypeWhenExists() {
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));

        userTypeUseCase.delete(userType.getId());

        verify(userTypeGateway).getById(userType.getId());
        verify(userTypeGateway).delete(userType.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover tipo de usuário inexistente")
    void shouldThrowWhenDeletingNonExistentUserType() {
        when(userTypeGateway.getById(userType.getId())).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> userTypeUseCase.delete(userType.getId()));

        verify(userTypeGateway, never()).delete(userType.getId());
    }
}
