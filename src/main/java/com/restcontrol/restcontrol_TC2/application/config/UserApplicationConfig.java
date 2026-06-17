package com.restcontrol.restcontrol_TC2.application.config;

import com.restcontrol.restcontrol_TC2.domain.gateway.User.CreateUserInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.DeleteUserInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.GetByIdUserInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.UpdateUserInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.CreateUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.DeleteUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.GetByIdUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserApplicationConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(CreateUserInterface gateway) {
        return new CreateUserUseCase(gateway);
    }

    @Bean
    public GetByIdUserUseCase getByIdUserUseCase(GetByIdUserInterface gateway) {
        return new GetByIdUserUseCase(gateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            UpdateUserInterface updateGateway,
            GetByIdUserInterface getByIdGateway
    ) {
        return new UpdateUserUseCase(updateGateway, getByIdGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(DeleteUserInterface gateway) {
        return new DeleteUserUseCase(gateway);
    }
}
