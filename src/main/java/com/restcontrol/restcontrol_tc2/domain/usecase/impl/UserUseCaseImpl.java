package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserUseCase;

public class UserUseCaseImpl implements UserUseCase {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public UserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public User create(User user) {
        validateUserTypeExists(user.getUserTypeId());
        return userGateway.create(user);
    }

    @Override
    public User update(User user) {
        userGateway.getById(user.getId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        validateUserTypeExists(user.getUserTypeId());
        return userGateway.update(user);
    }

    @Override
    public User getById(String id) {
        return userGateway.getById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public void delete(String id) {
        userGateway.getById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        userGateway.delete(id);
    }

    private void validateUserTypeExists(String userTypeId) {
        userTypeGateway.getById(userTypeId)
                .orElseThrow(() -> new UserTypeNotFoundException("UserType not found"));
    }

}
