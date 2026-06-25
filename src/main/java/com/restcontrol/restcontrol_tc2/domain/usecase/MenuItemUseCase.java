package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;

import java.util.List;

public interface MenuItemUseCase {

    MenuItem create(MenuItem menuItem);

    MenuItem update(MenuItem menuItem);

    List<MenuItem> listAll();

    MenuItem getById(String id);

    void delete(String id);
}
