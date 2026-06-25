package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;

public interface RestaurantUseCase {

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    Restaurant getByName(String name);

    Restaurant getById(String id);

    void delete(String id, String userId);
}
