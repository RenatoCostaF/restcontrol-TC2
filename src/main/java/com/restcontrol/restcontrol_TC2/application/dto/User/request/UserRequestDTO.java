package com.restcontrol.restcontrol_TC2.application.dto.User.request;

import com.restcontrol.restcontrol_TC2.application.dto.UserType.request.UserTypeRequestDTO;

public record UserRequestDTO(
        String name,
        String email,
        String password,
        UserTypeRequestDTO userType
) {
}
