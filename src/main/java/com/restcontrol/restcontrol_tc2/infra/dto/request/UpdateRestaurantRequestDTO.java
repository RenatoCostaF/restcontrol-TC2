package com.restcontrol.restcontrol_tc2.infra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRestaurantRequestDTO(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String cuisineType,
        @NotBlank String openingHours,
        @NotBlank String ownerId
) {
}
