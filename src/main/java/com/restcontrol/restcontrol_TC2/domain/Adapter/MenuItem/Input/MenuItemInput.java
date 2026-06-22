package com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input;

public record MenuItemInput(
        String name,
        String description,
        Double price,
        Boolean availableForDelivery,
        String imageUrl,
        String restaurantId,
        Boolean isActive
) {
}
