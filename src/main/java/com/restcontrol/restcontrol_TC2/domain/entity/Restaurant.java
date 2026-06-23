package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidRestaurantException;

public class Restaurant {

    private String id;
    private String name;
    private String city;
    private String zipcode;
    private String street;
    private String state;
    private String specialty;
    private String ownerId;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    private static void isValidName(String name) {
        if (name == null) {
            throw new InvalidRestaurantException("Name cannot be null");
        }
    }

    private static void isValidOwnerId(String ownerId) {
        if (ownerId == null) {
            throw new InvalidRestaurantException("OwnerId cannot be null");
        }
    }

    private static void isValidState(String state) {
        if (state.length() != 2) {
            throw new InvalidRestaurantException("Review State");
        }
    }

    private static void isValidZipCode(String zipcode) {
        if (zipcode.length() != 8) {
            throw new InvalidRestaurantException("Review Zipcode");
        }
    }

    public static Restaurant create(
            String name,
            String city,
            String zipcode,
            String street,
            String state,
            String specialty,
            String ownerId
    ) {

        isValidOwnerId(ownerId);
        isValidName(name);
        isValidZipCode(zipcode);
        isValidState(state);

        Restaurant restaurant = new Restaurant();
        restaurant.name = name;
        restaurant.city = city;
        restaurant.street = street;
        restaurant.zipcode = zipcode;
        restaurant.state = state;
        restaurant.specialty = specialty;
        restaurant.ownerId = ownerId;

        return restaurant;
    }
}
