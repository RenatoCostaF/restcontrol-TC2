package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantException;

public class Restaurant {

    private String id;
    private String name;
    private String city;
    private String zipcode;
    private String street;
    private String state;
    private String specialty;
    private String ownerId;

    public Restaurant(String id, String name, String city, String zipcode, String street, String state, String specialty, String ownerId) {

        isValidName(name);
        isValidOwnerId(ownerId);
        isValidState(state);
        isValidZipCode(zipcode);

        this.id = id;
        this.name = name;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
        this.state = state;
        this.specialty = specialty;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStreet() {
        return street;
    }

    public String getState() {
        return state;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getOwnerId() {
        return ownerId;
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


}
