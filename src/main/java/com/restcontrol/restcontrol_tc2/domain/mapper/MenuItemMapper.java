package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;

public class MenuItemMapper {

    public MenuItem toEntity(CreateMenuItemInputDTO input) {
        return new MenuItem(
                input.id(),
                input.name(),
                input.description(),
                input.price(),
                input.availableOnlyInRestaurant(),
                input.imageUrl(),
                input.restaurantId(),
                input.active()
        );
    }

    public MenuItem toEntity(UpdateMenuItemInputDTO input, String id) {
        return new MenuItem(
                id,
                input.name(),
                input.description(),
                input.price(),
                input.availableOnlyInRestaurant(),
                input.imageUrl(),
                input.restaurantId(),
                input.active()
        );
    }
}
