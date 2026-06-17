package com.restcontrol.restcontrol_TC2.application.dto.response;

import java.util.UUID;

public record UserTypeResponseDTO(
        UUID id,
        String name
) {
}
