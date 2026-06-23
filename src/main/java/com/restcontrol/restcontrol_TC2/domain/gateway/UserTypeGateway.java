package com.restcontrol.restcontrol_TC2.domain.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

import java.util.Optional;

public interface UserTypeGateway {

    UserType create(UserType userType);

    UserType update(UserType userType, String id);

    Optional<UserType> getById(String id);

    void delete(String id);
}
