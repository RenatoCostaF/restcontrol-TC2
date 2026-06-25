package com.restcontrol.restcontrol_tc2.infra.dto.response;

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
