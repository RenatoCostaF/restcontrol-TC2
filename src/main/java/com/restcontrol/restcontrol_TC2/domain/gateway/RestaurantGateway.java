package com.restcontrol.restcontrol_TC2.domain.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantGateway {

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant, UUID id);

    Optional<Restaurant> getByName(String name);

    Optional<Restaurant> getById(String id);

    void delete(String id);
}
