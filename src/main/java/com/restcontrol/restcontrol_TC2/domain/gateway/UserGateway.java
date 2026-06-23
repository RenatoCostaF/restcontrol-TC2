package com.restcontrol.restcontrol_TC2.domain.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.User;

import java.util.Optional;

public interface UserGateway {

    User create(User user);

    User update(User user, String id);

    Optional<User> getById(String id);

    void delete(String id);
}
