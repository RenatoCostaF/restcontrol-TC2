package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantDuplicateIdentified;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.port.input.RestaurantInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.RestaurantOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.CreateRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.DeleteRestaurantInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateRestaurantInput;

public class RestaurantUseCase implements RestaurantInputPort {

    private final RestaurantOutputPort restaurantOutputPort;

    public RestaurantUseCase(RestaurantOutputPort restaurantOutputPort) {
        this.restaurantOutputPort = restaurantOutputPort;
    }


    public Restaurant create(CreateRestaurantInput restaurantInput) {
        var existingRestaurant = restaurantOutputPort.getByName(restaurantInput.name());

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

        return restaurantOutputPort.create(newRestaurant);
    }

    public Restaurant update(UpdateRestaurantInput updateRestaurantInput, String id) {
        var restaurant = restaurantOutputPort.getById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        updateNameIfPresent(restaurant, updateRestaurantInput);
        updateAddress(restaurant, updateRestaurantInput);
        updateSpecialtyIfPresent(restaurant, updateRestaurantInput);
        updateOwnerIfPresent(restaurant, updateRestaurantInput);

        return restaurantOutputPort.update(restaurant, id);
    }

    public Restaurant getByName(String name) {
        var restaurant = restaurantOutputPort.getByName(name);

        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }

    public Restaurant getById(String id) {
        var restaurant = restaurantOutputPort.getById(id);

        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }

    public void delete(DeleteRestaurantInput restaurantInput) {
        var existingRestaurant = restaurantOutputPort.getById(restaurantInput.restaurantId());

        if (existingRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException("The restaurant you are trying to delete doesn't exist");
        } else if (!existingRestaurant.get().getOwnerId().equals(restaurantInput.runningUser())) {
            throw new ActionNotAllowedForRunningUser("Only the restaurant owner can delete the restaurant!");
        }

        restaurantOutputPort.delete(restaurantInput.restaurantId());
    }

    private void updateNameIfPresent(Restaurant restaurant, UpdateRestaurantInput input) {
        if (hasText(input.name())) {
            restaurant.rename(input.name());
        }
    }

    private void updateAddress(Restaurant restaurant, UpdateRestaurantInput input) {
        if (hasCompleteAddress(input)) {
            restaurant.relocateTo(
                    input.city(),
                    input.street(),
                    input.state(),
                    input.zipcode()
            );
            return;
        }

        updateAddressPartially(restaurant, input);
    }

    private boolean hasCompleteAddress(UpdateRestaurantInput input) {
        return hasText(input.city())
                && hasText(input.street())
                && hasText(input.state())
                && hasText(input.zipcode());
    }

    private void updateAddressPartially(Restaurant restaurant, UpdateRestaurantInput input) {
        if (hasText(input.city())) {
            restaurant.changeCity(input.city());
        }

        if (hasText(input.street())) {
            restaurant.changeStreet(input.street());
        }

        if (hasText(input.state())) {
            restaurant.changeState(input.state());
        }

        if (hasText(input.zipcode())) {
            restaurant.changeZipcode(input.zipcode());
        }
    }

    private void updateSpecialtyIfPresent(Restaurant restaurant, UpdateRestaurantInput input) {
        if (hasText(input.specialty())) {
            restaurant.changeSpecialty(input.specialty());
        }
    }

    private void updateOwnerIfPresent(Restaurant restaurant, UpdateRestaurantInput input) {
        if (hasText(input.ownerId())) {
            restaurant.transferOwnership(input.ownerId());
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
