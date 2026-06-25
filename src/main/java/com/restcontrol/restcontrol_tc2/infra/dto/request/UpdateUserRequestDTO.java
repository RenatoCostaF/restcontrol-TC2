package com.restcontrol.restcontrol_tc2.infra.dto.request;

public record UpdateUserRequestDTO(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
