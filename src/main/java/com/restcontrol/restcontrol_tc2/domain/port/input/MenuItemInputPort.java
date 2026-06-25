package com.restcontrol.restcontrol_tc2.domain.port.input;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.MenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;

public interface MenuItemInputPort {

    MenuItem create(MenuItemInput menuItemInput);

    MenuItem update(UpdateMenuItemInput updateMenuItemInput, String id);

    MenuItem getById(String id);

    void delete(String id);
}
