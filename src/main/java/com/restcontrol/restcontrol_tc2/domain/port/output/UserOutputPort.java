package com.restcontrol.restcontrol_tc2.domain.port.output;

import com.restcontrol.restcontrol_tc2.domain.entity.User;

import java.util.Optional;

public interface UserOutputPort {

    User create(User user);

    User update(User user, String id);

    Optional<User> getById(String id);

    void delete(String id);
}
