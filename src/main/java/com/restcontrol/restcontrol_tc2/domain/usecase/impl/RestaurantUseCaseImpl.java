package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantOwnerTypeException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.RestaurantUseCase;

import java.util.List;

public class RestaurantUseCaseImpl implements RestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public RestaurantUseCaseImpl(
            RestaurantGateway restaurantGateway,
            UserGateway userGateway,
            UserTypeGateway userTypeGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        validateOwnerIsRestaurantOwner(restaurant.getOwnerId());
        return restaurantGateway.create(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        restaurantGateway.getById(restaurant.getId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        validateOwnerIsRestaurantOwner(restaurant.getOwnerId());
        return restaurantGateway.update(restaurant);
    }

    @Override
    public List<Restaurant> listAll() {
        return restaurantGateway.listAll();
    }

    @Override
    public Restaurant getByName(String name) {
        return restaurantGateway.getByName(name).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

    @Override
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

    private void validateOwnerIsRestaurantOwner(String ownerId) {
        User owner = userGateway.getById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserType userType = userTypeGateway.getById(owner.getUserTypeId())
                .orElseThrow(() -> new UserTypeNotFoundException("UserType not found"));

        if (!userType.isRestaurantOwner()) {
            throw new InvalidRestaurantOwnerTypeException(
                    "Only users with type code '" + UserType.RESTAURANT_OWNER_CODE + "' can own restaurants"
            );
        }
    }

}
