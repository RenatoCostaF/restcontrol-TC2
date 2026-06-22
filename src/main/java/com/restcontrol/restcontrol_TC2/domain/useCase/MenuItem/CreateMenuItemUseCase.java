package com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input.MenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.CreateMenuItemInterface;

public class CreateMenuItemUseCase {

    private final CreateMenuItemInterface createMenuItemGateway;

    public CreateMenuItemUseCase(CreateMenuItemInterface createMenuItemGateway) {
        this.createMenuItemGateway = createMenuItemGateway;
    }

    public MenuItem execute(MenuItemInput menuItemInput) {
        var newMenuItem = MenuItem.create(
                menuItemInput.name(),
                menuItemInput.description(),
                menuItemInput.price(),
                menuItemInput.availableForDelivery(),
                menuItemInput.imageUrl(),
                menuItemInput.restaurantId(),
                menuItemInput.isActive()
        );

        return createMenuItemGateway.createMenuItem(newMenuItem);
    }
}
