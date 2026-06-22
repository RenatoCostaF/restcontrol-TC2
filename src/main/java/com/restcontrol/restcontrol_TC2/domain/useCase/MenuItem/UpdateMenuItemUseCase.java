package com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.GetByIdMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.UpdateMenuItemInterface;

public class UpdateMenuItemUseCase {

    private final UpdateMenuItemInterface updateMenuItemGateway;
    private final GetByIdMenuItemInterface getByIdMenuItemGateway;

    public UpdateMenuItemUseCase(
            UpdateMenuItemInterface updateMenuItemGateway,
            GetByIdMenuItemInterface getByIdMenuItemGateway
    ) {
        this.updateMenuItemGateway = updateMenuItemGateway;
        this.getByIdMenuItemGateway = getByIdMenuItemGateway;
    }

    public MenuItem execute(UpdateMenuItemInput updateMenuItemInput, String id) {
        var menuItem = getByIdMenuItemGateway.getById(id);

        if (menuItem.isEmpty()) {
            throw new MenuItemNotFoundException("Menu item not found");
        }

        var currentMenuItem = menuItem.get();

        var newMenuItem = MenuItem.create(
                updateMenuItemInput.name() != null && !updateMenuItemInput.name().isBlank()
                        ? updateMenuItemInput.name()
                        : currentMenuItem.getName(),

                updateMenuItemInput.description() != null
                        ? updateMenuItemInput.description()
                        : currentMenuItem.getDescription(),

                updateMenuItemInput.price() != null
                        ? updateMenuItemInput.price()
                        : currentMenuItem.getPrice(),

                updateMenuItemInput.availableForDelivery() != null
                        ? updateMenuItemInput.availableForDelivery()
                        : currentMenuItem.getAvailableForDelivery(),

                updateMenuItemInput.imageUrl() != null
                        ? updateMenuItemInput.imageUrl()
                        : currentMenuItem.getImageUrl(),

                updateMenuItemInput.restaurantId() != null && !updateMenuItemInput.restaurantId().isBlank()
                        ? updateMenuItemInput.restaurantId()
                        : currentMenuItem.getRestaurantId(),

                updateMenuItemInput.isActive() != null
                        ? updateMenuItemInput.isActive()
                        : currentMenuItem.getActive()
        );
        newMenuItem.setId(currentMenuItem.getId());

        return updateMenuItemGateway.updateMenuItem(newMenuItem, id);
    }
}
