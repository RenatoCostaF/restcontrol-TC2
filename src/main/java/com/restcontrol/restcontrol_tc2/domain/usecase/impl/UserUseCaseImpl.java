package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserUseCase;

public class UserUseCaseImpl implements UserUseCase {

    private final UserGateway userGateway;

    public UserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User create(User user) {
        return userGateway.create(user);
    }

    @Override
    public User update(User user) {
        userGateway.getById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userGateway.update(user);
    }

    @Override
    public User getById(String id) {
        return userGateway.getById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void delete(String id) {
        userGateway.getById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userGateway.delete(id);
    }

}
