package com.restcontrol.restcontrol_tc2.domain.dto;

public record UpdateRestaurantInputDTO(
        String name,
        String address,
        String cuisineType,
        String openingHours,
        String ownerId
) {
}
