package com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;


public interface GetByIdRestaurantInterface {
    Optional<Restaurant> getById(UUID id);
}
