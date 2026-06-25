package com.restcontrol.restcontrol_tc2.domain.port.output;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;

import java.util.Optional;

public interface MenuItemOutputPort {

    MenuItem create(MenuItem menuItem);

    MenuItem update(MenuItem menuItem, String id);

    Optional<MenuItem> getById(String id);

    void delete(String id);
}
