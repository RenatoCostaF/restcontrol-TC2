package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.CreateMenuItemInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.MenuItemDocument;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.MenuItem.MenuItemMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateMenuItemGateway implements CreateMenuItemInterface {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public CreateMenuItemGateway(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        MenuItemDocument document = menuItemMapper.toDocument(menuItem);
        MenuItemDocument savedDocument = menuItemRepository.save(document);
        return menuItemMapper.toDomain(savedDocument);
    }
}
