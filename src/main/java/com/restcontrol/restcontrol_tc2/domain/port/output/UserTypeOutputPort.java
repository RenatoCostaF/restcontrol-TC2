package com.restcontrol.restcontrol_tc2.domain.port.output;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;

import java.util.Optional;

public interface UserTypeOutputPort {

    UserType create(UserType userType);

    UserType update(UserType userType, String id);

    Optional<UserType> getById(String id);

    void delete(String id);
}
