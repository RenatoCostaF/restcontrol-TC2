package com.restcontrol.restcontrol_TC2.domain.useCase.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.GetByIdRestaurantInterface;

import java.util.UUID;

public class GetByIdRestaurantUseCase {
    private final GetByIdRestaurantInterface getByIdRestaurantGateway;

    public GetByIdRestaurantUseCase(GetByIdRestaurantInterface getByIdRestaurantGateway) {
        this.getByIdRestaurantGateway = getByIdRestaurantGateway;
    }

    public Restaurant execute(UUID id) {
        var restaurant = getByIdRestaurantGateway.getById(id);

        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }
}
