package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidObjectIdException;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.infra.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.MenuItemDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.MenuItemRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public MenuItem update(MenuItem menuItem) {
        MenuItemDocument document = menuItemMapper.toDocument(menuItem);
        MenuItemDocument savedDocument = menuItemRepository.save(document);
        return menuItemMapper.toDomain(savedDocument);
    }

    @Override
    public List<MenuItem> listAll() {
        return menuItemRepository.findAll()
                .stream()
                .map(menuItemMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<MenuItem> getById(String id) {
        return menuItemRepository.findById(toObjectId(id)).map(menuItemMapper::toDomain);
    }

    @Override
    public void delete(String id) {
        menuItemRepository.deleteById(toObjectId(id));
    }

    private ObjectId toObjectId(String id) {
        if (id == null || id.isBlank() || !ObjectId.isValid(id)) {
            throw new InvalidObjectIdException("Invalid id: " + id);
        }

        return new ObjectId(id);
    }
}
