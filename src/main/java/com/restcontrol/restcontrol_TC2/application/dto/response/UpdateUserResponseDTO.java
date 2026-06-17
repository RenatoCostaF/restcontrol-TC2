package com.restcontrol.restcontrol_TC2.application.dto.response;

public record UpdateUserResponseDTO(
        String name,
        String email,
        UserTypeResponseDTO userType
) {
}
