package com.restcontrol.restcontrol_tc2.infra.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload para criação de tipo de usuário")
public record CreateUserTypeRequestDTO(
        @Schema(description = "Nome do tipo de usuário", example = "Cliente")
        @NotBlank String name,
        @Schema(description = "Código único do tipo de usuário", example = "CUSTOMER")
        @NotBlank String code
) {
}
