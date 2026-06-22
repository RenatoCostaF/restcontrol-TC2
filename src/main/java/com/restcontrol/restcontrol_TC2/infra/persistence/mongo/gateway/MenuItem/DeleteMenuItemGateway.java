package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.DeleteMenuItemInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteMenuItemGateway implements DeleteMenuItemInterface {

    private final MenuItemRepository menuItemRepository;

    public DeleteMenuItemGateway(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void deleteMenuItem(String id) {
        menuItemRepository.deleteById(id);
    }
}
