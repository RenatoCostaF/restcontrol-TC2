package com.restcontrol.restcontrol_tc2.infra.service;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.MenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.port.input.MenuItemInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.MenuItemOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;
import org.springframework.stereotype.Service;

@Service
public class MenuItemApplicationService implements MenuItemInputPort {

    private final MenuItemUseCase menuItemUseCase;

    public MenuItemApplicationService(MenuItemOutputPort menuItemOutputPort) {
        this.menuItemUseCase = new MenuItemUseCase(menuItemOutputPort);
    }

    @Override
    public MenuItem create(MenuItemInput menuItemInput) {
        return menuItemUseCase.create(menuItemInput);
    }

    @Override
    public MenuItem update(UpdateMenuItemInput updateMenuItemInput, String id) {
        return menuItemUseCase.update(updateMenuItemInput, id);
    }

    @Override
    public MenuItem getById(String id) {
        return menuItemUseCase.getById(id);
    }

    @Override
    public void delete(String id) {
        menuItemUseCase.delete(id);
    }
}
