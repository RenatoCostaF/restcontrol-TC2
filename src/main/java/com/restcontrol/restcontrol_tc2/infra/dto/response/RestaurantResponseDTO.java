package com.restcontrol.restcontrol_tc2.infra.dto.response;

public record RestaurantResponseDTO(
        String id,
        String name,
        String address,
        String cuisineType,
        String openingHours,
        String ownerId
) {
}
