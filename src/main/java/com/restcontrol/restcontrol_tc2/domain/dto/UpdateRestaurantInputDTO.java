package com.restcontrol.restcontrol_tc2.domain.dto;

public record UpdateRestaurantInputDTO(
        String name,
        String city,
        String zipcode,
        String street,
        String state,
        String specialty,
        String ownerId
) {
}
