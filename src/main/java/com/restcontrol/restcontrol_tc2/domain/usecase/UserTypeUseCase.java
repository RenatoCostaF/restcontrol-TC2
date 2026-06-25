package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;

import java.util.List;

public interface UserTypeUseCase {

    UserType create(UserType userType);

    UserType update(UserType userType);

    List<UserType> listAll();

    UserType getById(String id);

    void delete(String id);
}
