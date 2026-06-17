package com.restcontrol.restcontrol_TC2.application.dto.request;

public record UserRequestDTO(
        String name,
        String email,
        String password,
        UserTypeRequestDTO userType
) {
}
