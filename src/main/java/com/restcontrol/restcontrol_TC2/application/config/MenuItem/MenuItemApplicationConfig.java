package com.restcontrol.restcontrol_TC2.application.config.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.CreateMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.DeleteMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.GetByIdMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.UpdateMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.CreateMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.DeleteMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.GetByIdMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.UpdateMenuItemUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemApplicationConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(CreateMenuItemInterface gateway) {
        return new CreateMenuItemUseCase(gateway);
    }

    @Bean
    public GetByIdMenuItemUseCase getByIdMenuItemUseCase(GetByIdMenuItemInterface gateway) {
        return new GetByIdMenuItemUseCase(gateway);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(
            UpdateMenuItemInterface updateGateway,
            GetByIdMenuItemInterface getByIdGateway
    ) {
        return new UpdateMenuItemUseCase(updateGateway, getByIdGateway);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(DeleteMenuItemInterface gateway) {
        return new DeleteMenuItemUseCase(gateway);
    }
}
