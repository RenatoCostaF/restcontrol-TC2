package com.restcontrol.restcontrol_tc2.infra.dto.response;

public record UpdateRestaurantResponseDTO(
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
