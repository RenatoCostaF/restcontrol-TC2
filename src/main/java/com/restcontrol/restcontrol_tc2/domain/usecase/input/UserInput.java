package com.restcontrol.restcontrol_tc2.domain.usecase.input;

public record UserInput(
        String name,
        String email,
        String password,
        String userTypeId
) {
}
