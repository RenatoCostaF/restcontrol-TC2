package com.restcontrol.restcontrol_tc2.infra.dto.request;

public record UpdateRestaurantRequestDTO(
        String name,
        String city,
        String zipcode,
        String street,
        String state,
        String specialty,
        String ownerId
) {
}
