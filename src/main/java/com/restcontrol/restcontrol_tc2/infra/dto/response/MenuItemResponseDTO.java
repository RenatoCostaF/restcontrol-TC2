package com.restcontrol.restcontrol_tc2.infra.dto.response;

public record MenuItemResponseDTO(
        String id,
        String name,
        String description,
        Double price,
        Boolean availableOnlyInRestaurant,
        String imageUrl,
        String restaurantId,
        Boolean active
) {
}
