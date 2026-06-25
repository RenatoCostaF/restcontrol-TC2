package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.mapper.UserTypeMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserTypeUseCase;

import java.util.List;

public class UserTypeController {

    private final UserTypeUseCase userTypeUseCase;
    private final UserTypeMapper userTypeMapper;

    public UserTypeController(UserTypeUseCase userTypeUseCase, UserTypeMapper userTypeMapper) {
        this.userTypeUseCase = userTypeUseCase;
        this.userTypeMapper = userTypeMapper;
    }

    public UserType create(CreateUserTypeInputDTO input) {
        UserType userType = userTypeMapper.toEntity(input);
        return userTypeUseCase.create(userType);
    }

    public UserType update(UpdateUserTypeInputDTO input, String id) {
        UserType userType = userTypeMapper.toEntity(input, id);
        return userTypeUseCase.update(userType);
    }

    public List<UserType> listAll() {
        return userTypeUseCase.listAll();
    }

    public UserType getById(String id) {
        return userTypeUseCase.getById(id);
    }

    public void delete(String id) {
        userTypeUseCase.delete(id);
    }
}
