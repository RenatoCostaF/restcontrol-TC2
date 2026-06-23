package com.restcontrol.restcontrol_TC2.infra.config;

import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItemUseCase;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemConfig {

    private final MenuItemGateway menuItemGateway;

    public MenuItemConfig(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItemUseCase menuItemUseCase(MenuItemGateway menuItemGateway) {
        return new MenuItemUseCase(menuItemGateway);
    }
}
