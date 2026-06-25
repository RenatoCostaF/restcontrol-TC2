package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidMenuItemException;

public class MenuItem {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Boolean availableForDelivery;
    private String imageUrl;
    private String restaurantId;
    private Boolean isActive;

    public MenuItem(String id, String name, String description, Double price, Boolean availableForDelivery, String imageUrl, String restaurantId, Boolean isActive) {

        isValidateName(name);
        isValidatePrice(price);
        isValidateRestaurantId(restaurantId);

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableForDelivery = availableForDelivery;
        this.imageUrl = imageUrl;
        this.restaurantId = restaurantId;
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getAvailableForDelivery() {
        return availableForDelivery;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    private static void isValidateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidMenuItemException("Name is required");
        }
    }

    private static void isValidatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new InvalidMenuItemException("Price must be greater than zero");
        }
    }

    private static void isValidateRestaurantId(String restaurantId) {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new InvalidMenuItemException("Restaurant id is required");
        }
    }


}
