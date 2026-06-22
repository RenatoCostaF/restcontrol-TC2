package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.MenuItemDocument;
import org.springframework.stereotype.Component;

@Component("mongoMenuItemMapper")
public class MenuItemMapper {

    public MenuItemDocument toDocument(MenuItem menuItem) {
        MenuItemDocument document = new MenuItemDocument();
        if (menuItem.getId() != null) {
            document.setId(menuItem.getId());
        }
        document.setName(menuItem.getName());
        document.setDescription(menuItem.getDescription());
        document.setPrice(menuItem.getPrice());
        document.setAvailableForDelivery(menuItem.getAvailableForDelivery());
        document.setImageUrl(menuItem.getImageUrl());
        document.setRestaurantId(menuItem.getRestaurantId());
        document.setActive(menuItem.getActive());
        return document;
    }

    public MenuItem toDomain(MenuItemDocument document) {
        MenuItem menuItem = MenuItem.create(
                document.getName(),
                document.getDescription(),
                document.getPrice(),
                document.getAvailableForDelivery(),
                document.getImageUrl(),
                document.getRestaurantId(),
                document.getActive()
        );

        if (document.getId() != null) {
            menuItem.setId(document.getId());
        }

        return menuItem;
    }
}
