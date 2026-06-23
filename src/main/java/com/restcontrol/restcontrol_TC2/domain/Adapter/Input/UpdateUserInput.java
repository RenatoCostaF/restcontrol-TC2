package com.restcontrol.restcontrol_TC2.domain.adapter.input;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

public record UpdateUserInput(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
