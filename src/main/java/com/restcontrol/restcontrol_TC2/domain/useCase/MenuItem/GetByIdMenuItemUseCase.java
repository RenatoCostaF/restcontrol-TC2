package com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.GetByIdMenuItemInterface;

public class GetByIdMenuItemUseCase {

    private final GetByIdMenuItemInterface getByIdMenuItemGateway;

    public GetByIdMenuItemUseCase(GetByIdMenuItemInterface getByIdMenuItemGateway) {
        this.getByIdMenuItemGateway = getByIdMenuItemGateway;
    }

    public MenuItem execute(String id) {
        var menuItem = getByIdMenuItemGateway.getById(id);

        if (menuItem.isEmpty()) {
            throw new MenuItemNotFoundException("Menu item not found");
        }

        return menuItem.get();
    }
}
