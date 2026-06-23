package com.restcontrol.restcontrol_TC2.domain.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.entity.User;

import java.util.Optional;

public interface MenuItemGateway {

    MenuItem create(MenuItem menuItem);

    MenuItem update(MenuItem menuItem, String id);

    Optional<MenuItem> getById(String id);

    void delete(String id);
}
