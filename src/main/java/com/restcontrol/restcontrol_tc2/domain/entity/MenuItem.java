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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    private void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailableForDelivery() {
        return availableForDelivery;
    }

    private void setAvailableForDelivery(Boolean availableForDelivery) {
        this.availableForDelivery = availableForDelivery;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    private void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getActive() {
        return isActive;
    }

    private void setActive(Boolean active) {
        isActive = active;
    }

    public static Boolean canActivate(MenuItem item) {
        return item != null
                && item.name != null
                && !item.name.isBlank()
                && item.price != null
                && item.price > 0
                && item.restaurantId != null
                && !item.restaurantId.isBlank();
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
        item.availableForDelivery = Boolean.TRUE.equals(availableForDelivery);
        item.imageUrl = imageUrl;
        item.restaurantId = restaurantId;
        item.isActive = Boolean.TRUE.equals(isActive);


        if (Boolean.TRUE.equals(item.isActive) && !Boolean.TRUE.equals(canActivate(item))) {
            throw new InvalidMenuItemException("Menu item cannot be activated with the provided data");
        }

        return item;
    }

    public static MenuItem restore(
            String id,
            String name,
            String description,
            Double price,
            Boolean availableForDelivery,
            String imageUrl,
            String restaurantId,
            Boolean isActive
    ) {
        MenuItem item = create(name, description, price, availableForDelivery, imageUrl, restaurantId, isActive);
        item.setId(id);
        return item;
    }

    public void rename(String name) {
        validateName(name);
        setName(name);
    }

    public void changeDescription(String description) {
        setDescription(description);
    }

    public void changePrice(Double price) {
        validatePrice(price);
        setPrice(price);
    }

    public void changeDeliveryAvailability(Boolean availableForDelivery) {
        setAvailableForDelivery(availableForDelivery);
    }

    public void changeImageUrl(String imageUrl) {
        setImageUrl(imageUrl);
    }

    public void assignToRestaurant(String restaurantId) {
        validateRestaurantId(restaurantId);
        setRestaurantId(restaurantId);
    }

    public void activate() {
        setActive(true);
        if (!Boolean.TRUE.equals(canActivate(this))) {
            throw new InvalidMenuItemException("Menu item cannot be activated with the provided data");
        }
    }

    public void deactivate() {
        setActive(false);
    }

}
