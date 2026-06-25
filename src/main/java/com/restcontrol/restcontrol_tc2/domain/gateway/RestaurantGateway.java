package com.restcontrol.restcontrol_tc2.domain.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    List<Restaurant> listAll();

    Optional<Restaurant> getByName(String name);

    Optional<Restaurant> getById(String id);

    void delete(String id);

}
