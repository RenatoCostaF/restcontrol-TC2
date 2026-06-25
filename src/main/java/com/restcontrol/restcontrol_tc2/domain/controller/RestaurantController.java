package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.RestaurantUseCase;

public class RestaurantController {

    private final RestaurantUseCase restaurantUseCase;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(RestaurantUseCase restaurantUseCase, RestaurantMapper restaurantMapper) {
        this.restaurantUseCase = restaurantUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    public Restaurant create(CreateRestaurantInputDTO input) {
        Restaurant restaurant = restaurantMapper.toEntity(input);
        return restaurantUseCase.create(restaurant);
    }

    public Restaurant update(UpdateRestaurantInputDTO input, String id) {
        Restaurant restaurant = restaurantMapper.toEntity(input, id);
        return restaurantUseCase.update(restaurant);
    }

    public Restaurant getByName(String name) {
        return restaurantUseCase.getByName(name);
    }

    public Restaurant getById(String id) {
        return restaurantUseCase.getById(id);
    }

    public void delete(String id, String userId) {
        restaurantUseCase.delete(id, userId);
    }
}
