package com.restcontrol.restcontrol_TC2.domain.useCase.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.GetByNameRestaurantInterface;

public class GetByNameRestaurantUseCase {
    private final GetByNameRestaurantInterface getByNameRestaurantInterface;

    public GetByNameRestaurantUseCase(GetByNameRestaurantInterface getByNameRestaurantInterface) {
        this.getByNameRestaurantInterface = getByNameRestaurantInterface;
    }

    public Restaurant execute(String name) {
        var restaurant = getByNameRestaurantInterface.getByName(name);

        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }
}
