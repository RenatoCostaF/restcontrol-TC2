package com.restcontrol.restcontrol_tc2.domain.port.input;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.CreateRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.DeleteRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;

public interface RestaurantInputPort {

    Restaurant create(CreateRestaurantInput restaurantInput);

    Restaurant update(UpdateRestaurantInput updateRestaurantInput, String id);

    Restaurant getById(String id);

    void delete(DeleteRestaurantInput restaurantInput);
}
