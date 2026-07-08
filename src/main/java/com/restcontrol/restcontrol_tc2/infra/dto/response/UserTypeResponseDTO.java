package com.restcontrol.restcontrol_tc2.infra.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de tipo de usuário retornada pela API")
public record UserTypeResponseDTO(
        @Schema(description = "Identificador único do tipo de usuário", example = "507f1f77bcf86cd799439011")
        String id,
        @Schema(description = "Nome do tipo de usuário", example = "Cliente")
        String name,
        @Schema(description = "Código único do tipo de usuário", example = "CUSTOMER")
        String code
) {
}
