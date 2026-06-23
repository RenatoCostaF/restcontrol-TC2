package com.restcontrol.restcontrol_TC2.domain.useCase;

import com.restcontrol.restcontrol_TC2.domain.adapter.input.MenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItemGateway;

public class MenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public MenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem create(MenuItemInput menuItemInput) {
        var newMenuItem = MenuItem.create(
                menuItemInput.name(),
                menuItemInput.description(),
                menuItemInput.price(),
                menuItemInput.availableForDelivery(),
                menuItemInput.imageUrl(),
                menuItemInput.restaurantId(),
                menuItemInput.isActive()
        );

        return menuItemGateway.create(newMenuItem);
    }

    public MenuItem update(UpdateMenuItemInput updateMenuItemInput, String id) {
        var menuItem = menuItemGateway.getById(id);

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

        return menuItemGateway.update(newMenuItem, id);
    }

    public MenuItem getById(String id) {
        var menuItem = menuItemGateway.getById(id);

        if (menuItem.isEmpty()) {
            throw new MenuItemNotFoundException("Menu item not found");
        }

        return menuItem.get();
    }

    public void delete(String id) {
        menuItemGateway.delete(id);
    }
}
