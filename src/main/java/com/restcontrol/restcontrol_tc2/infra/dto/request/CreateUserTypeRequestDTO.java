package com.restcontrol.restcontrol_tc2.infra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserTypeRequestDTO(
        @NotBlank String name,
        @NotBlank String code
) {
}
