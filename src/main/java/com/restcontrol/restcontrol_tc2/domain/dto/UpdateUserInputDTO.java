package com.restcontrol.restcontrol_tc2.domain.dto;

public record UpdateUserInputDTO(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
