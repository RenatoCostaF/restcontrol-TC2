package com.restcontrol.restcontrol_TC2.application.dto.MenuItem.request;

public record UpdateMenuItemRequestDTO(
        String name,
        String description,
        Double price,
        Boolean availableForDelivery,
        String imageUrl,
        String restaurantId,
        Boolean isActive
) {
}
