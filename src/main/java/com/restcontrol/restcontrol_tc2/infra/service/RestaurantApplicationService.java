package com.restcontrol.restcontrol_tc2.infra.service;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.CreateRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.DeleteRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.port.input.RestaurantInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.RestaurantOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.RestaurantUseCase;
import org.springframework.stereotype.Service;

@Service
public class RestaurantApplicationService implements RestaurantInputPort {

    private final RestaurantUseCase restaurantUseCase;

    public RestaurantApplicationService(RestaurantOutputPort restaurantOutputPort) {
        this.restaurantUseCase = new RestaurantUseCase(restaurantOutputPort);
    }

    @Override
    public Restaurant create(CreateRestaurantInput restaurantInput) {
        return restaurantUseCase.create(restaurantInput);
    }

    @Override
    public Restaurant update(UpdateRestaurantInput updateRestaurantInput, String id) {
        return restaurantUseCase.update(updateRestaurantInput, id);
    }

    @Override
    public Restaurant getById(String id) {
        return restaurantUseCase.getById(id);
    }

    @Override
    public void delete(DeleteRestaurantInput restaurantInput) {
        restaurantUseCase.delete(restaurantInput);
    }
}
