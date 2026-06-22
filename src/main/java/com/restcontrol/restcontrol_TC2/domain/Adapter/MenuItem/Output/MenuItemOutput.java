package com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Output;

public record MenuItemOutput(
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
