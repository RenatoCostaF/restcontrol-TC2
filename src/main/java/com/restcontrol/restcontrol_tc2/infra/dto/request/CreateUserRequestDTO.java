package com.restcontrol.restcontrol_tc2.infra.dto.request;

public record CreateUserRequestDTO(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
