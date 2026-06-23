package com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;

import java.util.Optional;

public interface GetByNameRestaurantInterface {
    Optional<Restaurant> getByName(String name);
}
