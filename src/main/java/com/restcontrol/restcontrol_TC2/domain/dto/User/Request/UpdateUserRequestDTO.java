package com.restcontrol.restcontrol_TC2.domain.dto.User.Request;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

public record UpdateUserRequestDTO(
        String name,
        String email,
        String password,
        UserType userType
) {
}
