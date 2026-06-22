package com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;

import java.util.Optional;

public interface GetByIdMenuItemInterface {
    Optional<MenuItem> getById(String id);
}
