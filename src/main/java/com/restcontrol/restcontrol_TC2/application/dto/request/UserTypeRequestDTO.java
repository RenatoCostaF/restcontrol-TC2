package com.restcontrol.restcontrol_TC2.application.dto.request;

import java.util.UUID;

public record UserTypeRequestDTO(
        UUID id,
        String name
) {
}
