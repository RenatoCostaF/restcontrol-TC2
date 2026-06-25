package com.restcontrol.restcontrol_tc2.domain.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;

import java.util.Optional;

public interface UserTypeGateway {

    UserType create(UserType userType);

    UserType update(UserType userType);

    Optional<UserType> getById(String id);

    void delete(String id);

}
