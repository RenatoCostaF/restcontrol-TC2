package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.mapper.UserMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserUseCase;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private CreateUserInputDTO createInput;
    private UpdateUserInputDTO updateInput;

    @BeforeEach
    void setUp() {
        user = UserTestFixtures.validUser();
        createInput = UserTestFixtures.createUserInput();
        updateInput = UserTestFixtures.updateUserInput();
    }

    @Test
    @DisplayName("Should create an user")
    void shouldCreateUser() {
        when(userMapper.toEntity(createInput)).thenReturn(user);
        when(userUseCase.create(user)).thenReturn(user);

        User result = userController.create(createInput);

        assertEquals(user, result);
        verify(userMapper).toEntity(createInput);
        verify(userUseCase).create(user);
    }

    @Test
    @DisplayName("Should update an user")
    void shouldUpdateUser() {
        when(userMapper.toEntity(updateInput, user.getId())).thenReturn(user);
        when(userUseCase.update(user)).thenReturn(user);

        User result = userController.update(updateInput, user.getId());

        assertEquals(user, result);
        verify(userMapper).toEntity(updateInput, user.getId());
        verify(userUseCase).update(user);
    }

    @Test
    @DisplayName("Should get an user by the ID")
    void shouldGetUserById() {
        when(userUseCase.getById(user.getId())).thenReturn(user);

        User result = userController.getById(user.getId());

        assertEquals(user, result);
        verify(userUseCase).getById(user.getId());
    }

    @Test
    @DisplayName("Should delete an user")
    void shouldDeleteUser() {
        userController.delete(user.getId());

        verify(userUseCase).delete(user.getId());
    }
}
