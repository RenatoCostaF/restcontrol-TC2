package com.restcontrol.restcontrol_tc2.domain.usecase.input;

public record UpdateMenuItemInput(
        String name,
        String description,
        Double price,
        Boolean availableForDelivery,
        String imageUrl,
        String restaurantId,
        Boolean isActive
) {
}
