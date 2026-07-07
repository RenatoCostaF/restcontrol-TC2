package com.restcontrol.restcontrol_tc2.support;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import org.bson.types.ObjectId;

public final class MenuItemTestFixtures {

    public static final String VALID_MENU_ITEM_ID = new ObjectId().toHexString();
    public static final String VALID_RESTAURANT_ID = new ObjectId().toHexString();
    public static final String VALID_OWNER_ID = new ObjectId().toHexString();
    public static final String VALID_NAME = "Margherita Pizza";
    public static final String VALID_DESCRIPTION = "Classic pizza with tomato and mozzarella";
    public static final Double VALID_PRICE = 29.90;
    public static final String VALID_IMAGE_URL = "https://example.com/pizza.jpg";

    private MenuItemTestFixtures() {
    }

    public static MenuItem validMenuItem() {
        return new MenuItem(
                VALID_MENU_ITEM_ID,
                VALID_NAME,
                VALID_DESCRIPTION,
                VALID_PRICE,
                false,
                VALID_IMAGE_URL,
                VALID_RESTAURANT_ID,
                true
        );
    }

    public static MenuItem validMenuItem(String id) {
        return new MenuItem(
                id,
                VALID_NAME,
                VALID_DESCRIPTION,
                VALID_PRICE,
                false,
                VALID_IMAGE_URL,
                VALID_RESTAURANT_ID,
                true
        );
    }

    public static Restaurant validRestaurant() {
        return new Restaurant(
                VALID_RESTAURANT_ID,
                "Pizza Place",
                "123 Main St",
                "Italian",
                "09:00-22:00",
                VALID_OWNER_ID
        );
    }

    public static CreateMenuItemInputDTO createMenuItemInput() {
        return new CreateMenuItemInputDTO(
                null,
                VALID_NAME,
                VALID_DESCRIPTION,
                VALID_PRICE,
                false,
                VALID_IMAGE_URL,
                VALID_RESTAURANT_ID,
                true
        );
    }

    public static UpdateMenuItemInputDTO updateMenuItemInput() {
        return new UpdateMenuItemInputDTO(
                "Pepperoni Pizza",
                "Pizza with pepperoni",
                34.90,
                true,
                "https://example.com/pepperoni.jpg",
                VALID_RESTAURANT_ID,
                false
        );
    }
}
