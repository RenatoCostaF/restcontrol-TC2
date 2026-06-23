package com.restcontrol.restcontrol_TC2.infra.config;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserUseCase userUseCase(UserGateway userGateway) {
        return new UserUseCase(userGateway);
    }
}
