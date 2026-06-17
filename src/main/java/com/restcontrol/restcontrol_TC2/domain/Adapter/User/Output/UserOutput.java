package com.restcontrol.restcontrol_TC2.domain.adapter.User.Output;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

public record UserOutput(
        String name,
        String email,
        UserType userType
) {
}
