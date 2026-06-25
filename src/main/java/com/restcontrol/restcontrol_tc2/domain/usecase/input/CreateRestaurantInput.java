package com.restcontrol.restcontrol_tc2.domain.usecase.input;

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
