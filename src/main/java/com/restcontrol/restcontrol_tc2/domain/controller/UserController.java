package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.mapper.UserMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserUseCase;

public class UserController {

    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    public UserController(UserUseCase userUseCase, UserMapper userMapper) {
        this.userUseCase = userUseCase;
        this.userMapper = userMapper;
    }

    public User create(CreateUserInputDTO input) {
        User user = userMapper.toEntity(input);
        return userUseCase.create(user);
    }

    public User update(UpdateUserInputDTO input, String id) {
        User user = userMapper.toEntity(
                new CreateUserInputDTO(
                        id,
                        input.name(),
                        input.email(),
                        input.password(),
                        input.userTypeId()
                )
        );

        return userUseCase.update(user);
    }

    public User getById(String id) {
        return userUseCase.getById(id);
    }

    public void delete(String id) {
        userUseCase.delete(id);
    }
}
