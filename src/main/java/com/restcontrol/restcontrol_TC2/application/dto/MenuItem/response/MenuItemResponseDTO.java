package com.restcontrol.restcontrol_TC2.application.dto.MenuItem.response;

public record MenuItemResponseDTO(
        String id,
        String name,
        String description,
        Double price,
        Boolean availableForDelivery,
        String imageUrl,
        String restaurantId,
        Boolean isActive
) {
}
