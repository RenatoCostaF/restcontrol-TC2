package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemMapperTest {

    private MenuItemMapper menuItemMapper;

    @BeforeEach
    void setUp() {
        menuItemMapper = new MenuItemMapper();
    }

    @Test
    void shouldMapCreateMenuItemInputToEntity() {
        CreateMenuItemInputDTO input = MenuItemTestFixtures.createMenuItemInput();

        MenuItem menuItem = menuItemMapper.toEntity(input);

        assertEquals(input.id(), menuItem.getId());
        assertEquals(input.name(), menuItem.getName());
        assertEquals(input.description(), menuItem.getDescription());
        assertEquals(input.price(), menuItem.getPrice());
        assertEquals(input.availableOnlyInRestaurant(), menuItem.getAvailableOnlyInRestaurant());
        assertEquals(input.imageUrl(), menuItem.getImageUrl());
        assertEquals(input.restaurantId(), menuItem.getRestaurantId());
        assertEquals(input.active(), menuItem.getActive());
    }

    @Test
    void shouldMapUpdateMenuItemInputToEntity() {
        String id = new ObjectId().toHexString();
        UpdateMenuItemInputDTO input = MenuItemTestFixtures.updateMenuItemInput();

        MenuItem menuItem = menuItemMapper.toEntity(input, id);

        assertEquals(id, menuItem.getId());
        assertEquals(input.name(), menuItem.getName());
        assertEquals(input.description(), menuItem.getDescription());
        assertEquals(input.price(), menuItem.getPrice());
        assertEquals(input.availableOnlyInRestaurant(), menuItem.getAvailableOnlyInRestaurant());
        assertEquals(input.imageUrl(), menuItem.getImageUrl());
        assertEquals(input.restaurantId(), menuItem.getRestaurantId());
        assertEquals(input.active(), menuItem.getActive());
    }
}
