package com.restcontrol.restcontrol_TC2.domain.adapter.input;

public record UpdateRestaurantInput(
        String name,
        String city,
        String zipcode,
        String street,
        String state,
        String specialty,
        String ownerId
) {
}
