package com.restcontrol.restcontrol_TC2.domain.gateway.User;

import com.restcontrol.restcontrol_TC2.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface GetByIdUserInterface {
    Optional<User> getById(UUID id);
}
