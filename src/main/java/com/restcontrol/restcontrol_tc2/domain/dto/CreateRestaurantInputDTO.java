package com.restcontrol.restcontrol_tc2.domain.dto;

public record CreateRestaurantInputDTO(
        String id,
        String name,
        String city,
        String zipcode,
        String street,
        String state,
        String specialty,
        String ownerId
) {

}
