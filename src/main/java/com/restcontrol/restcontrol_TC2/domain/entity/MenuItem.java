package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidMenuItemException;

import java.util.UUID;

public class MenuItem {

    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Boolean availableForDelivery;
    private String imageUrl;
    private String restaurantId;
    private Boolean isActive;

    public UUID getId() {
        return id;
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
        //TO IMPLEMENT LOGIC - CANT ACTIVATE UPON CERTAIN CONDITIONS
        return true;
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
        if (name == null) {
            throw new InvalidMenuItemException("Name is required");
        }

        MenuItem item = new MenuItem();
        item.name = name;
        item.description = description;
        item.price = price;
        item.availableForDelivery = availableForDelivery;
        item.imageUrl = imageUrl;
        item.isActive = isActive;
        item.restaurantId = restaurantId;

        return item;
    }
}
