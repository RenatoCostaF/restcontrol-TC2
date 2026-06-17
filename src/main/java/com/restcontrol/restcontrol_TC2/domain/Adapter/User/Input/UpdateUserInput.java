package com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

public record UpdateUserInput(
        String name,
        String email,
        String password,
        UserType userType
) {
}
