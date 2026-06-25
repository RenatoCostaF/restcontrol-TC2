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

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    private void setState(String state) {
        this.state = state;
    }

    public String getSpecialty() {
        return specialty;
    }

    private void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getOwnerId() {
        return ownerId;
    }

    private void setOwnerId(String ownerId) {
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

    public static Restaurant restore(
            String id,
            String name,
            String city,
            String zipcode,
            String street,
            String state,
            String specialty,
            String ownerId
    ) {
        Restaurant restaurant = create(name, city, zipcode, street, state, specialty, ownerId);
        restaurant.setId(id);
        return restaurant;
    }

    public void rename(String name) {
        isValidName(name);
        setName(name);
    }

    public void relocateTo(String city, String street, String state, String zipcode) {
        isValidState(state);
        isValidZipCode(zipcode);
        setCity(city);
        setStreet(street);
        setState(state);
        setZipcode(zipcode);
    }

    public void changeSpecialty(String specialty) {
        setSpecialty(specialty);
    }

    public void transferOwnership(String ownerId) {
        isValidOwnerId(ownerId);
        setOwnerId(ownerId);
    }

    public void changeCity(String city) {
        setCity(city);
    }

    public void changeStreet(String street) {
        setStreet(street);
    }

    public void changeState(String state) {
        isValidState(state);
        setState(state);
    }

    public void changeZipcode(String zipcode) {
        isValidZipCode(zipcode);
        setZipcode(zipcode);
    }
}
