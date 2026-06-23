package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.MenuItemDocument;
import com.restcontrol.restcontrol_TC2.infra.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.MenuItemRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MenuItemGatewayImpl implements MenuItemGateway {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemGatewayImpl(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        MenuItemDocument document = menuItemMapper.toDocument(menuItem);
        MenuItemDocument savedDocument = menuItemRepository.save(document);
        return menuItemMapper.toDomain(savedDocument);
    }

    @Override
    public MenuItem update(MenuItem menuItem, String id) {
        MenuItemDocument document = menuItemMapper.toDocument(menuItem);
        document.setId(new ObjectId(id));
        MenuItemDocument savedDocument = menuItemRepository.save(document);
        return menuItemMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<MenuItem> getById(String id) {
        return menuItemRepository.findById(new ObjectId(id)).map(menuItemMapper::toDomain);
    }

    @Override
    public void delete(String id) {
        menuItemRepository.deleteById(new ObjectId(id));
    }
}
