package com.restcontrol.restcontrol_TC2.domain.useCase.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.Adapter.Restaurant.Input.CreateRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantDuplicateIdentified;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.CreateRestaurantInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.GetByNameRestaurantInterface;

public class CreateRestaurantUseCase {

    private final CreateRestaurantInterface createRestaurantInterface;
    private final GetByNameRestaurantInterface getByNameRestaurantInterface;

    public CreateRestaurantUseCase(GetByNameRestaurantInterface getRestaurantByNameGateway, CreateRestaurantInterface createRestaurantGateway){
        this.createRestaurantInterface = createRestaurantGateway;
        this.getByNameRestaurantInterface = getRestaurantByNameGateway;
    }

    public Restaurant execute(CreateRestaurantInput restaurantInput){
        var existingRestaurant = getByNameRestaurantInterface.getByName(restaurantInput.name);

        if (!existingRestaurant.isEmpty()){
            throw new RestaurantDuplicateIdentified("Duplicate restaurant name found!");
        }

        var newRestaurant = Restaurant.create(
            restaurantInput.name(),
            restaurantInput.city(),
            restaurantInput.zipcode(),
            restaurantInput.street(),
            restaurantInput.state(),
            restaurantInput.specialty(),
            restaurantInput.ownerId()
        );

        return createRestaurantInterface.createRestaurant(newRestaurant);
    }


}
