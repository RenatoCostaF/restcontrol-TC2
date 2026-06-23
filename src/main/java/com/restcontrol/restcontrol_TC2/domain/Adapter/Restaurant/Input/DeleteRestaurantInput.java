package com.restcontrol.restcontrol_TC2.domain.Adapter.Restaurant.Input;

import java.util.UUID;

public record DeleteRestaurantInput(
        UUID restaurantId,
        UUID runningUser
) {
}
