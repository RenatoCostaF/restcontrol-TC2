package com.restcontrol.restcontrol_TC2.domain.adapter.input;

public record CreateRestaurantInput (
    String name,
    String city,
    String zipcode,
    String street,
    String state,
    String specialty,
    String ownerId
){

}
