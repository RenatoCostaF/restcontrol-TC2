package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;

import java.util.List;

public class MenuItemUseCaseImpl implements MenuItemUseCase {

    private static final String MENU_ITEM_NOT_FOUND_MESSAGE = "Menu item not found";

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
        menuItemGateway.getById(menuItem.getId()).orElseThrow(() -> new MenuItemNotFoundException(MENU_ITEM_NOT_FOUND_MESSAGE));
        validateRestaurantExists(menuItem.getRestaurantId());
        return menuItemGateway.update(menuItem);
    }

    @Override
    public List<MenuItem> listAll() {
        return menuItemGateway.listAll();
    }

    @Override
    public MenuItem getById(String id) {
        return menuItemGateway.getById(id).orElseThrow(() -> new MenuItemNotFoundException(MENU_ITEM_NOT_FOUND_MESSAGE));

    }

    @Override
    public void delete(String id) {
        menuItemGateway.getById(id).orElseThrow(() -> new MenuItemNotFoundException(MENU_ITEM_NOT_FOUND_MESSAGE));
        menuItemGateway.delete(id);
    }

    private void validateRestaurantExists(String restaurantId) {
        restaurantGateway.getById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

}
