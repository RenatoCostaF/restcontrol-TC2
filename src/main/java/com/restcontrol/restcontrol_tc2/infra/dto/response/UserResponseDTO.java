package com.restcontrol.restcontrol_tc2.infra.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de usuário retornada pela API")
public record UserResponseDTO(
        @Schema(description = "Identificador único do usuário", example = "507f1f77bcf86cd799439011")
        String id,

        @Schema(description = "Nome completo do usuário", example = "João Silva")
        String name,

        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        String email,

        @Schema(description = "Identificador do tipo de usuário", example = "507f1f77bcf86cd799439011")
        String userTypeId
) {
}
