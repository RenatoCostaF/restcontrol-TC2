package com.restcontrol.restcontrol_tc2.infra.config;

import com.restcontrol.restcontrol_tc2.domain.controller.MenuItemController;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.domain.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;
import com.restcontrol.restcontrol_tc2.domain.usecase.impl.MenuItemUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemBeanConfig {

    @Bean
    public MenuItemMapper menuItemDomainMapper() {
        return new MenuItemMapper();
    }

    @Bean
    public MenuItemUseCase menuItemUseCase(MenuItemGateway menuItemGateway) {
        return new MenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public MenuItemController menuItemController(
            MenuItemUseCase menuItemUseCase,
            MenuItemMapper menuItemDomainMapper
    ) {
        return new MenuItemController(menuItemUseCase, menuItemDomainMapper);
    }
}
