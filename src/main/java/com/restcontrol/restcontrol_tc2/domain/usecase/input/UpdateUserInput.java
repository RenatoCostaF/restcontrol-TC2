package com.restcontrol.restcontrol_tc2.domain.usecase.input;

public record UpdateUserInput(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
