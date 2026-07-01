package com.restcontrol.restcontrol_tc2.infra.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Payload para atualização de item de cardápio")
public record UpdateMenuItemRequestDTO(
        @Schema(description = "Nome do item", example = "X-Burger")
        @NotBlank String name,

        @Schema(description = "Descrição do item", example = "Hambúrguer artesanal com queijo e bacon")
        @NotBlank String description,

        @Schema(description = "Preço do item (deve ser maior que zero)", example = "29.90")
        @NotNull @Positive Double price,

        @Schema(description = "Indica se o item está disponível apenas no restaurante", example = "false")
        @NotNull Boolean availableOnlyInRestaurant,

        @Schema(description = "URL da imagem do item", example = "https://cdn.example.com/images/x-burger.jpg")
        @NotBlank String imageUrl,

        @Schema(description = "Identificador do restaurante", example = "507f1f77bcf86cd799439011")
        @NotBlank String restaurantId,

        @Schema(description = "Indica se o item está ativo no cardápio", example = "true")
        @NotNull Boolean active
) {
}
