package com.restcontrol.restcontrol_tc2.domain.dto;

public record CreateUserInputDTO(
        String id,
        String name,
        String email,
        String password,
        String userTypeId
) {
}
