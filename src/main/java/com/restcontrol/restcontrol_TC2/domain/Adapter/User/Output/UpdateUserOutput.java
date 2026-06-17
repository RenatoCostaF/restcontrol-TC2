package com.restcontrol.restcontrol_TC2.domain.adapter.User.Output;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

public record UpdateUserOutput(
        String name,
        String email,
        UserType userType
) {
}
