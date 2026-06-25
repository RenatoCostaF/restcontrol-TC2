package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserTypeUseCase;

import java.util.List;

public class UserTypeUseCaseImpl implements UserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public UserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType create(UserType userType) {
        return userTypeGateway.create(userType);
    }

    @Override
    public UserType update(UserType userType) {
        userTypeGateway.getById(userType.getId()).orElseThrow(() -> new UserTypeNotFoundException("UserType not found"));
        return userTypeGateway.update(userType);
    }

    @Override
    public List<UserType> listAll() {
        return userTypeGateway.listAll();
    }

    @Override
    public UserType getById(String id) {
        return userTypeGateway.getById(id).orElseThrow(() -> new UserTypeNotFoundException("UserType not found"));
    }

    @Override
    public void delete(String id) {
        userTypeGateway.getById(id).orElseThrow(() -> new UserTypeNotFoundException("UserType not found"));
        userTypeGateway.delete(id);
    }

}
