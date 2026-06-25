package com.restcontrol.restcontrol_tc2.domain.port.output;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantOutputPort {

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant, String id);

    Optional<Restaurant> getByName(String name);

    Optional<Restaurant> getById(String id);

    void delete(String id);
}
