package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidMenuItemException;

public class MenuItem {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Boolean availableForDelivery;
    private String imageUrl;
    private String restaurantId;
    private Boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailableForDelivery() {
        return availableForDelivery;
    }

    public void setAvailableForDelivery(Boolean availableForDelivery) {
        this.availableForDelivery = availableForDelivery;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public static Boolean canActivate(MenuItem item) {
        if (item == null) {
            return false;
        }

        if (item.name == null || item.name.isBlank()) {
            return false;
        }

        if (item.price == null || item.price <= 0) {
            return false;
        }

        if (item.restaurantId == null || item.restaurantId.isBlank()) {
            return false;
        }

        return true;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidMenuItemException("Name is required");
        }
    }

    private static void validatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new InvalidMenuItemException("Price must be greater than zero");
        }
    }

    private static void validateRestaurantId(String restaurantId) {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new InvalidMenuItemException("Restaurant id is required");
        }
    }

    public static MenuItem create(
            String name,
            String description,
            Double price,
            Boolean availableForDelivery,
            String imageUrl,
            String restaurantId,
            Boolean isActive
    ) {
        validateName(name);
        validatePrice(price);
        validateRestaurantId(restaurantId);

        MenuItem item = new MenuItem();
        item.name = name;
        item.description = description;
        item.price = price;
        item.availableForDelivery = availableForDelivery != null ? availableForDelivery : false;
        item.imageUrl = imageUrl;
        item.restaurantId = restaurantId;
        item.isActive = isActive != null ? isActive : false;

        if (item.isActive && !canActivate(item)) {
            throw new InvalidMenuItemException("Menu item cannot be activated with the provided data");
        }

        return item;
    }

}
