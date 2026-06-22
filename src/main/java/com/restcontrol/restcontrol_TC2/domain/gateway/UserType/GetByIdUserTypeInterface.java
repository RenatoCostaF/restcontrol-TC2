package com.restcontrol.restcontrol_TC2.domain.gateway.UserType;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

import java.util.Optional;

public interface GetByIdUserTypeInterface {
    Optional<UserType> getById(String id);
}
