package com.restcontrol.restcontrol_tc2.domain.dto;

public record CreateRestaurantInputDTO(
        String id,
        String name,
        String address,
        String cuisineType,
        String openingHours,
        String ownerId
) {

}
