package com.restcontrol.restcontrol_TC2.infra.config;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserTypeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeConfig {

    @Bean
    public UserTypeUseCase userTypeUseCase(UserTypeGateway userTypeGateway) {
        return new UserTypeUseCase(userTypeGateway);
    }
}
