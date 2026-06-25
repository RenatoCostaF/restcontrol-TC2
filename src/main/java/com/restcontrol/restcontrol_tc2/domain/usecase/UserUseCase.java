package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.User;

public interface UserUseCase {

    User create(User user);

    User update(User user);

    User getById(String id);

    void delete(String id);
}
