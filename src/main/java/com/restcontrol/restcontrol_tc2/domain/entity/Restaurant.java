package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantException;

public class Restaurant {

    private String id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private String ownerId;

    public Restaurant(String id, String name, String address, String cuisineType, String openingHours, String ownerId) {

        isValidName(name);
        isValidAddress(address);
        isValidCuisineType(cuisineType);
        isValidOpeningHours(openingHours);
        isValidOwnerId(ownerId);

        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void ensureOwnedBy(String userId) {
        if (!ownerId.equals(userId)) {
            throw new ActionNotAllowedForRunningUser("Only the restaurant owner can delete the restaurant!");
        }
    }

    private static void isValidName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidRestaurantException("Name cannot be null");
        }
    }

    private static void isValidAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new InvalidRestaurantException("Address cannot be null");
        }
    }

    private static void isValidCuisineType(String cuisineType) {
        if (cuisineType == null || cuisineType.isBlank()) {
            throw new InvalidRestaurantException("Cuisine type cannot be null");
        }
    }

    private static void isValidOpeningHours(String openingHours) {
        if (openingHours == null || openingHours.isBlank()) {
            throw new InvalidRestaurantException("Opening hours cannot be null");
        }
    }

    private static void isValidOwnerId(String ownerId) {
        if (ownerId == null || ownerId.isBlank()) {
            throw new InvalidRestaurantException("OwnerId cannot be null");
        }
    }

}
