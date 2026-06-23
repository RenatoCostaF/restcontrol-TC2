package com.restcontrol.restcontrol_TC2.domain.useCase.Restaurant;

import com.restcontrol.restcontrol_TC2.domain.Adapter.Restaurant.Input.UpdateRestaurantInput;
import com.restcontrol.restcontrol_TC2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_TC2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.GetByIdRestaurantInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.Restaurant.UpdateByIdRestaurantInterface;

import java.util.UUID;

public class UpdateByIdRestaurantUseCase {

    private final GetByIdRestaurantInterface getByIdRestaurantInterface;
    private final UpdateByIdRestaurantInterface updateByIdRestaurantInterface;

    public UpdateByIdRestaurantUseCase(
            GetByIdRestaurantInterface getByIdRestaurantInterface,
            UpdateByIdRestaurantInterface updateByIdRestaurantInterface
    ) {
        this.getByIdRestaurantInterface = getByIdRestaurantInterface;
        this.updateByIdRestaurantInterface = updateByIdRestaurantInterface;
    }

    public Restaurant execute(UpdateRestaurantInput updateInput) {
        var existing = getByIdRestaurantInterface.getById(updateInput.id());

        if (existing.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        var currentRestaurant = existing.get();

        var updated = Restaurant.create(
                updateInput.name() != null && !updateInput.name().isBlank()
                        ? updateInput.name()
                        : currentRestaurant.getName(),

                updateInput.city() != null && !updateInput.city().isBlank()
                        ? updateInput.city()
                        : currentRestaurant.getCity(),

                updateInput.zipcode() != null && !updateInput.zipcode().isBlank()
                        ? updateInput.zipcode()
                        : currentRestaurant.getZipcode(),

                updateInput.street() != null && !updateInput.street().isBlank()
                        ? updateInput.street()
                        : currentRestaurant.getStreet(),

                updateInput.state() != null && !updateInput.state().isBlank()
                        ? updateInput.state()
                        : currentRestaurant.getState(),

                updateInput.specialty() != null && !updateInput.specialty().isBlank()
                        ? updateInput.specialty()
                        : currentRestaurant.getSpecialty(),

                updateInput.ownerId() != null && !updateInput.ownerId().isBlank()
                        ? updateInput.ownerId()
                        : currentRestaurant.getOwnerId()
        );

        return updateByIdRestaurantInterface.restaurantUpdate(updated, updateInput.id())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found after update"));
    }
}
