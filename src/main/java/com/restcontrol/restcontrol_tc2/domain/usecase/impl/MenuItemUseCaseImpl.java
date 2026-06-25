package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;

public class MenuItemUseCaseImpl implements MenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public MenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        validateRestaurantExists(menuItem.getRestaurantId());
        return menuItemGateway.create(menuItem);
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        menuItemGateway.getById(menuItem.getId()).orElseThrow(() -> new MenuItemNotFoundException("Menu item not found"));
        validateRestaurantExists(menuItem.getRestaurantId());
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

    private void validateRestaurantExists(String restaurantId) {
        restaurantGateway.getById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

}
