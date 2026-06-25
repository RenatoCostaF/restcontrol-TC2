package com.restcontrol.restcontrol_tc2.infra.dto.response;

public record UpdateUserResponseDTO(
        String name,
        String email,
        String userTypeId
) {
}
