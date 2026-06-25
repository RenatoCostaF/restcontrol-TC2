package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.RestaurantUseCase;

public class RestaurantUseCaseImpl implements RestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public RestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        return restaurantGateway.create(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        restaurantGateway.getById(restaurant.getId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        return restaurantGateway.update(restaurant);
    }

    public Restaurant getByName(String name) {
        return restaurantGateway.getByName(name).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

    public Restaurant getById(String id) {
        return restaurantGateway.getById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

    @Override
    public void delete(String id, String userId) {
        Restaurant restaurant = restaurantGateway.getById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        User user = userGateway.getById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        // ALOCAR PRA DENTRO DA ENTIRY RESTAURANTE ESSA VALIDACAO DENTRE OUTRAS NECESSARIAS
        if (!restaurant.getOwnerId().equals(userId)) {
            throw new ActionNotAllowedForRunningUser("Only the restaurant owner can delete the restaurant!");
        }

        restaurantGateway.delete(id);
    }

}
