package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;

public class RestaurantMapper {

    public Restaurant toEntity(CreateRestaurantInputDTO input) {
        return new Restaurant(
                input.id(),
                input.name(),
                input.address(),
                input.cuisineType(),
                input.openingHours(),
                input.ownerId()
        );
    }

    public Restaurant toEntity(UpdateRestaurantInputDTO input, String id) {
        return new Restaurant(
                id,
                input.name(),
                input.address(),
                input.cuisineType(),
                input.openingHours(),
                input.ownerId()
        );
    }
}
