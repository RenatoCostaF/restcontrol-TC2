package com.restcontrol.restcontrol_TC2.domain.gateway.User;

import com.restcontrol.restcontrol_TC2.domain.entity.User;

import java.util.UUID;

public interface UpdateUserInterface {
    void updateUser(User user, UUID id);
}
