package com.restcontrol.restcontrol_tc2.infra.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de item de cardápio retornada pela API")
public record MenuItemResponseDTO(
        @Schema(description = "Identificador único do item", example = "507f1f77bcf86cd799439011")
        String id,

        @Schema(description = "Nome do item", example = "X-Burger")
        String name,

        @Schema(description = "Descrição do item", example = "Hambúrguer artesanal com queijo e bacon")
        String description,

        @Schema(description = "Preço do item", example = "29.90")
        Double price,

        @Schema(description = "Indica se o item está disponível apenas no restaurante", example = "false")
        Boolean availableOnlyInRestaurant,

        @Schema(description = "URL da imagem do item", example = "https://cdn.example.com/images/x-burger.jpg")
        String imageUrl,

        @Schema(description = "Identificador do restaurante", example = "507f1f77bcf86cd799439012")
        String restaurantId,

        @Schema(description = "Indica se o item está ativo no cardápio", example = "true")
        Boolean active
) {
}
