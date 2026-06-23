package com.restcontrol.restcontrol_TC2.infra.dto.response;

public record UserResponseDTO(
        String name,
        String email,
        String userTypeId
) {
}
