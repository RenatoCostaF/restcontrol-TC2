package com.restcontrol.restcontrol_tc2.infra.dto.response;

public record UserResponseDTO(
        String id,
        String name,
        String email,
        String userTypeId
) {
}
