package com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.DeleteMenuItemInterface;

public class DeleteMenuItemUseCase {

    private final DeleteMenuItemInterface deleteMenuItemGateway;

    public DeleteMenuItemUseCase(DeleteMenuItemInterface deleteMenuItemGateway) {
        this.deleteMenuItemGateway = deleteMenuItemGateway;
    }

    public void execute(String id) {
        deleteMenuItemGateway.deleteMenuItem(id);
    }
}
