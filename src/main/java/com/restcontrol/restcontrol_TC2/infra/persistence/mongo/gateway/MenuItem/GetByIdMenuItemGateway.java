package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.GetByIdMenuItemInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.MenuItem.MenuItemMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetByIdMenuItemGateway implements GetByIdMenuItemInterface {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public GetByIdMenuItemGateway(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public Optional<MenuItem> getById(String id) {
        return menuItemRepository.findById(id).map(menuItemMapper::toDomain);
    }
}
