package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.port.input.MenuItemInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.MenuItemOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.MenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateMenuItemInput;

public class MenuItemUseCase implements MenuItemInputPort {

    private final MenuItemOutputPort menuItemOutputPort;

    public MenuItemUseCase(MenuItemOutputPort menuItemOutputPort) {
        this.menuItemOutputPort = menuItemOutputPort;
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

        return menuItemOutputPort.create(newMenuItem);
    }

    public MenuItem update(UpdateMenuItemInput updateMenuItemInput, String id) {
        var menuItem = menuItemOutputPort.getById(id).orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));

        updateNameIfPresent(menuItem, updateMenuItemInput);
        updateDescriptionIfPresent(menuItem, updateMenuItemInput);
        updatePriceIfPresent(menuItem, updateMenuItemInput);
        updateDeliveryAvailabilityIfPresent(menuItem, updateMenuItemInput);
        updateImageUrlIfPresent(menuItem, updateMenuItemInput);
        updateRestaurantIfPresent(menuItem, updateMenuItemInput);
        updateActivationStatusIfPresent(menuItem, updateMenuItemInput);

        return menuItemOutputPort.update(menuItem, id);
    }

    public MenuItem getById(String id) {
        var menuItem = menuItemOutputPort.getById(id);

        if (menuItem.isEmpty()) {
            throw new MenuItemNotFoundException("Menu item not found");
        }

        return menuItem.get();
    }

    public void delete(String id) {
        var menuItem = menuItemOutputPort.getById(id);

        if (menuItem.isEmpty()) {
            throw new MenuItemNotFoundException("The menu you are trying to delete doesn't exist");
        }
        menuItemOutputPort.delete(id);
    }

    private void updateNameIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (hasText(input.name())) {
            menuItem.rename(input.name());
        }
    }

    private void updateDescriptionIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (input.description() != null) {
            menuItem.changeDescription(input.description());
        }
    }

    private void updatePriceIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (input.price() != null) {
            menuItem.changePrice(input.price());
        }
    }

    private void updateDeliveryAvailabilityIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (input.availableForDelivery() != null) {
            menuItem.changeDeliveryAvailability(input.availableForDelivery());
        }
    }

    private void updateImageUrlIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (input.imageUrl() != null) {
            menuItem.changeImageUrl(input.imageUrl());
        }
    }

    private void updateRestaurantIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (hasText(input.restaurantId())) {
            menuItem.assignToRestaurant(input.restaurantId());
        }
    }

    private void updateActivationStatusIfPresent(MenuItem menuItem, UpdateMenuItemInput input) {
        if (Boolean.TRUE.equals(input.isActive())) {
            menuItem.activate();
            return;
        }

        if (Boolean.FALSE.equals(input.isActive())) {
            menuItem.deactivate();
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

}
