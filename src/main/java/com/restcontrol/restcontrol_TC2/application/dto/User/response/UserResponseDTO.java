package com.restcontrol.restcontrol_TC2.application.dto.User.response;

import com.restcontrol.restcontrol_TC2.application.dto.UserType.response.UserTypeResponseDTO;

public record UserResponseDTO(
        String name,
        String email,
        UserTypeResponseDTO userType
) {
}
