package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidMenuItemException;

public class MenuItem {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Boolean availableOnlyInRestaurant;
    private String imageUrl;
    private String restaurantId;
    private Boolean isActive;

    public MenuItem(String id, String name, String description, Double price, Boolean availableOnlyInRestaurant, String imageUrl, String restaurantId, Boolean isActive) {

        isValidateName(name);
        isValidateDescription(description);
        isValidatePrice(price);
        isValidateRestaurantId(restaurantId);
        isValidateImageUrl(imageUrl);
        isValidateAvailability(availableOnlyInRestaurant);
        isValidateActive(isActive);

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyInRestaurant = availableOnlyInRestaurant;
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

    public Boolean getAvailableOnlyInRestaurant() {
        return availableOnlyInRestaurant;
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

    private static void isValidateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidMenuItemException("Description is required");
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

    private static void isValidateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new InvalidMenuItemException("Image path is required");
        }
    }

    private static void isValidateAvailability(Boolean availableOnlyInRestaurant) {
        if (availableOnlyInRestaurant == null) {
            throw new InvalidMenuItemException("Availability is required");
        }
    }

    private static void isValidateActive(Boolean isActive) {
        if (isActive == null) {
            throw new InvalidMenuItemException("Active status is required");
        }
    }


}
