package com.restcontrol.restcontrol_tc2.infra.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateMenuItemRequestDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull @Positive Double price,
        @NotNull Boolean availableForDelivery,
        @NotBlank String imageUrl,
        @NotBlank String restaurantId,
        @NotNull Boolean isActive
) {
}
