package com.restcontrol.restcontrol_TC2.domain.dto.User.Response;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

public record UserResponseDTO(
        String name,
        String email,
        UserType userType
) {
}
