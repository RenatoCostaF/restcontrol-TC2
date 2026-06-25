package com.restcontrol.restcontrol_tc2.domain.dto;

public record CreateMenuItemInputDTO(
        String id,
        String name,
        String description,
        Double price,
        Boolean availableOnlyInRestaurant,
        String imageUrl,
        String restaurantId,
        Boolean isActive
) {
}
