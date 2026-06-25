package com.restcontrol.restcontrol_tc2.infra.dto.request;

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
