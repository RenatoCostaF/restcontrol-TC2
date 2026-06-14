package com.restcontrol.restcontrol_TC2.domain.entity;

import java.util.UUID;

public class Restaurant {

    private UUID id;
    private String name;
    private String city;
    private String zipcode;
    private String street;
    private String state;
    private String specialty;
    private String ownerId;

    public Restaurant(){

    }

    public Restaurant(
            UUID id,
            String name,
            String city,
            String zipcode,
            String street,
            String state,
            String specialty,
            String ownerId
    ){
        this.name = name;
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
        this.state = state;
        this.specialty = specialty;
        this.ownerId = ownerId;
    }

    public UUID getId() {
        return id;
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

    private static boolean isValidZipCode(String zipcode){
        return zipcode.length() == 5;
    }
    private static boolean isValidState(String state){
        return state.length() == 2;
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
        if (name == null || zipcode == null || ownerId == null){
            throw new IllegalArgumentException("Name, zipcode and ownerId are required");
        }

        if(!isValidZipCode(zipcode)){
            throw new IllegalArgumentException("Review Zipcode");
        }

        if (!isValidState(state)){
            throw new IllegalArgumentException("Review State");
        }

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
