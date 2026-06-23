package com.restcontrol.restcontrol_TC2.infra.dto.request;

public record UserRequestDTO(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
