package com.restcontrol.restcontrol_tc2.domain.usecase.input;

public record DeleteRestaurantInput(
        String restaurantId,
        String runningUser
) {
}
