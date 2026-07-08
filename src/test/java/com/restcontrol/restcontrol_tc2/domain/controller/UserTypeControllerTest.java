package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.mapper.UserTypeMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserTypeUseCase;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller de domínio - UserType")
class UserTypeControllerTest {

    @Mock
    private UserTypeUseCase userTypeUseCase;

    @Mock
    private UserTypeMapper userTypeMapper;

    @InjectMocks
    private UserTypeController userTypeController;

    private UserType userType;
    private CreateUserTypeInputDTO createInput;
    private UpdateUserTypeInputDTO updateInput;

    @BeforeEach
    void setUp() {
        userType = UserTypeTestHelper.validUserType();
        createInput = UserTypeTestHelper.createUserTypeInput();
        updateInput = UserTypeTestHelper.updateUserTypeInput();
    }

    @Test
    @DisplayName("Deve criar tipo de usuário delegando ao use case")
    void shouldCreateUserType() {
        when(userTypeMapper.toEntity(createInput)).thenReturn(userType);
        when(userTypeUseCase.create(userType)).thenReturn(userType);

        UserType result = userTypeController.create(createInput);

        assertEquals(userType, result);
        verify(userTypeMapper).toEntity(createInput);
        verify(userTypeUseCase).create(userType);
    }

    @Test
    @DisplayName("Deve atualizar tipo de usuário delegando ao use case")
    void shouldUpdateUserType() {
        when(userTypeMapper.toEntity(updateInput, userType.getId())).thenReturn(userType);
        when(userTypeUseCase.update(userType)).thenReturn(userType);

        UserType result = userTypeController.update(updateInput, userType.getId());

        assertEquals(userType, result);
        verify(userTypeMapper).toEntity(updateInput, userType.getId());
        verify(userTypeUseCase).update(userType);
    }

    @Test
    @DisplayName("Deve listar todos os tipos de usuário")
    void shouldListAllUserTypes() {
        List<UserType> userTypes = List.of(userType);
        when(userTypeUseCase.listAll()).thenReturn(userTypes);

        List<UserType> result = userTypeController.listAll();

        assertEquals(userTypes, result);
        verify(userTypeUseCase).listAll();
    }

    @Test
    @DisplayName("Deve buscar tipo de usuário por ID")
    void shouldGetUserTypeById() {
        when(userTypeUseCase.getById(userType.getId())).thenReturn(userType);

        UserType result = userTypeController.getById(userType.getId());

        assertEquals(userType, result);
        verify(userTypeUseCase).getById(userType.getId());
    }

    @Test
    @DisplayName("Deve remover tipo de usuário por ID")
    void shouldDeleteUserType() {
        userTypeController.delete(userType.getId());

        verify(userTypeUseCase).delete(userType.getId());
    }
}
