package com.restcontrol.restcontrol_TC2.application.dto.response;

public record UserResponseDTO(
        String name,
        String email,
        UserTypeResponseDTO userType
) {
}
