package com.restcontrol.restcontrol_TC2.infra.config;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserTypeUseCase;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeConfig {

    private final UserTypeGateway userTypeGateway;

    public UserTypeConfig(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserTypeUseCase userTypeUseCase(UserTypeGateway userTypeGateway) {
        return new UserTypeUseCase(userTypeGateway);
    }
}
