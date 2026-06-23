package com.restcontrol.restcontrol_TC2.domain.useCase.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.Adapter.Restaurant.Input.DeleteRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.DeleteRestaurantInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.GetByIdRestaurantInterface;

public class DeleteRestaurantUseCase {

    private final GetByIdRestaurantInterface getByIdRestaurantInterface;
    private final DeleteRestaurantInterface deleteRestaurantInterface;

    public DeleteRestaurantUseCase (GetByIdRestaurantInterface getByIdGateway, DeleteRestaurantInterface deleteRestaurantgateway){
        this.getByIdRestaurantInterface = getByIdGateway;
        this.deleteRestaurantInterface = deleteRestaurantgateway;
    }

    public void execute(DeleteRestaurantInput restaurantInput){
        var existingRestaurant = getByIdRestaurantInterface.getById(restaurantInput.restaurantId());

        if (existingRestaurant.isEmpty()){
            throw new RestaurantNotFoundException("The restaurant you are trying to delete doesn't exist");
        } else if (!existingRestaurant.get().getOwnerId().equals(restaurantInput.runningUser())){
            throw new ActionNotAllowedForRunningUser("Only the restaurant owner can delete the restaurant!");
        }

        deleteRestaurantInterface.deleteRestaurant(restaurantInput.restaurantId());
    }
}
