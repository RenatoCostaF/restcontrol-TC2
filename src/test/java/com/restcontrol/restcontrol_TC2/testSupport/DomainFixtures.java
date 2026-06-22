package com.restcontrol.restcontrol_TC2.testSupport;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;

import java.util.UUID;

public final class DomainFixtures {

    private DomainFixtures() {
    }

    public static UserType validUserType() {
        var userType = UserType.create("Administrator");
        userType.setId(UUID.randomUUID().toString());
        return userType;
    }

    public static MenuItem validMenuItem() {
        var menuItem = MenuItem.create(
                "Pizza Margherita",
                "Classic tomato and mozzarella",
                29.90,
                true,
                "https://example.com/pizza.jpg",
                UUID.randomUUID().toString(),
                false
        );
        menuItem.setId(UUID.randomUUID().toString());
        return menuItem;
    }

    public static MenuItem inactiveMenuItem() {
        return MenuItem.create(
                "Burger",
                "Beef burger",
                19.90,
                false,
                null,
                UUID.randomUUID().toString(),
                false
        );
    }
}
