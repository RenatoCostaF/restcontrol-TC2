package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;

public class MenuItemUseCaseImpl implements MenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public MenuItemUseCaseImpl(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        return menuItemGateway.create(menuItem);
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        menuItemGateway.getById(menuItem.getId()).orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));
        return menuItemGateway.update(menuItem);
    }

    @Override
    public MenuItem getById(String id) {
        return menuItemGateway.getById(id).orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));

    }

    @Override
    public void delete(String id) {
        menuItemGateway.getById(id).orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));
        menuItemGateway.delete(id);
    }

}
