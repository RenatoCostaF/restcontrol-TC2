package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;

public class MenuItemController {

    private final MenuItemUseCase menuItemUseCase;
    private final MenuItemMapper menuItemMapper;

    public MenuItemController(MenuItemUseCase menuItemUseCase, MenuItemMapper menuItemMapper) {
        this.menuItemUseCase = menuItemUseCase;
        this.menuItemMapper = menuItemMapper;
    }

    public MenuItem create(CreateMenuItemInputDTO input) {
        MenuItem menuItem = menuItemMapper.toEntity(input);
        return menuItemUseCase.create(menuItem);
    }

    public MenuItem update(UpdateMenuItemInputDTO input, String id) {
        MenuItem menuItem = menuItemMapper.toEntity(input, id);
        return menuItemUseCase.update(menuItem);
    }

    public MenuItem getById(String id) {
        return menuItemUseCase.getById(id);
    }

    public void delete(String id) {
        menuItemUseCase.delete(id);
    }

}
