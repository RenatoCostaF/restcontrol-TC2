package com.restcontrol.restcontrol_TC2.domain.adapter.input;

import java.util.UUID;

public record DeleteRestaurantInput(
        UUID restaurantId,
        UUID runningUser
) {
}
