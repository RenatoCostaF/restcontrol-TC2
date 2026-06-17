package com.restcontrol.restcontrol_TC2.application.dto.request;

public record UpdateUserRequestDTO(
        String name,
        String email,
        String password,
        UserTypeRequestDTO userType
) {
}
