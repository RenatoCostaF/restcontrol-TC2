package com.restcontrol.restcontrol_TC2.domain.useCase;

import com.restcontrol.restcontrol_TC2.domain.adapter.input.CreateRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.DeleteRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantDuplicateIdentified;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.RestaurantGateway;

public class RestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public RestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }


    public Restaurant create(CreateRestaurantInput restaurantInput) {
        var existingRestaurant = restaurantGateway.getByName(restaurantInput.name());

        if (!existingRestaurant.isEmpty()) {
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

        return restaurantGateway.create(newRestaurant);
    }

    public Restaurant update(UpdateRestaurantInput updateRestaurantInput, String id) {
        var existing = restaurantGateway.getById(id);

        if (existing.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        var currentRestaurant = existing.get();

        var updated = Restaurant.create(
                updateRestaurantInput.name() != null && !updateRestaurantInput.name().isBlank()
                        ? updateRestaurantInput.name()
                        : currentRestaurant.getName(),

                updateRestaurantInput.city() != null && !updateRestaurantInput.city().isBlank()
                        ? updateRestaurantInput.city()
                        : currentRestaurant.getCity(),

                updateRestaurantInput.zipcode() != null && !updateRestaurantInput.zipcode().isBlank()
                        ? updateRestaurantInput.zipcode()
                        : currentRestaurant.getZipcode(),

                updateRestaurantInput.street() != null && !updateRestaurantInput.street().isBlank()
                        ? updateRestaurantInput.street()
                        : currentRestaurant.getStreet(),

                updateRestaurantInput.state() != null && !updateRestaurantInput.state().isBlank()
                        ? updateRestaurantInput.state()
                        : currentRestaurant.getState(),

                updateRestaurantInput.specialty() != null && !updateRestaurantInput.specialty().isBlank()
                        ? updateRestaurantInput.specialty()
                        : currentRestaurant.getSpecialty(),

                updateRestaurantInput.ownerId() != null && !updateRestaurantInput.ownerId().isBlank()
                        ? updateRestaurantInput.ownerId()
                        : currentRestaurant.getOwnerId());
        updated.setId(currentRestaurant.getId());

        return restaurantGateway.update(updated, id);
    }

    public Restaurant getByName(String name) {
        var restaurant = restaurantGateway.getByName(name);

        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }

    public Restaurant getById(String id) {
        var restaurant = restaurantGateway.getById(id);

        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }

    public void delete(DeleteRestaurantInput restaurantInput) {
        var existingRestaurant = restaurantGateway.getById(restaurantInput.restaurantId());

        if (existingRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException("The restaurant you are trying to delete doesn't exist");
        } else if (!existingRestaurant.get().getOwnerId().equals(restaurantInput.runningUser())) {
            throw new ActionNotAllowedForRunningUser("Only the restaurant owner can delete the restaurant!");
        }

        restaurantGateway.delete(restaurantInput.restaurantId());
    }
}
