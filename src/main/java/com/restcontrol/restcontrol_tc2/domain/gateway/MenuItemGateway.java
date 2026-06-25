package com.restcontrol.restcontrol_tc2.domain.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemGateway {

    MenuItem create(MenuItem menuItem);

    MenuItem update(MenuItem menuItem);

    List<MenuItem> listAll();

    Optional<MenuItem> getById(String id);

    void delete(String id);
}
