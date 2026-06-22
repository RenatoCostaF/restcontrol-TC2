package com.restcontrol.restcontrol_TC2.application.config.UserType;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.CreateUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.DeleteUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.GetByIdUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.UpdateUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.CreateUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.DeleteUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.GetByIdUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.UpdateUserTypeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeApplicationConfig {

    @Bean
    public CreateUserTypeUseCase createUserTypeUseCase(CreateUserTypeInterface gateway) {
        return new CreateUserTypeUseCase(gateway);
    }

    @Bean
    public GetByIdUserTypeUseCase getByIdUserTypeUseCase(GetByIdUserTypeInterface gateway) {
        return new GetByIdUserTypeUseCase(gateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUseCase(
            UpdateUserTypeInterface updateGateway,
            GetByIdUserTypeInterface getByIdGateway
    ) {
        return new UpdateUserTypeUseCase(updateGateway, getByIdGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUseCase(DeleteUserTypeInterface gateway) {
        return new DeleteUserTypeUseCase(gateway);
    }
}
