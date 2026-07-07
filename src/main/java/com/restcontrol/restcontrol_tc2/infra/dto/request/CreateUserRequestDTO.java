package com.restcontrol.restcontrol_tc2.infra.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Payload para criação de usuário")
public record CreateUserRequestDTO(
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        @NotBlank String name,

        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        @Email @NotBlank String email,

        @Schema(description = "Senha com no mínimo 8 caracteres", example = "senha1234", minLength = 8)
        @NotBlank @Size(min = 8) String password,

        @Schema(description = "Identificador do tipo de usuário", example = "507f1f77bcf86cd799439011")
        @NotBlank String userTypeId
) {
}
