package com.restcontrol.restcontrol_tc2.infra.config;

import com.restcontrol.restcontrol_tc2.domain.controller.UserController;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.mapper.UserMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserUseCase;
import com.restcontrol.restcontrol_tc2.domain.usecase.impl.UserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    public UserMapper userDomainMapper() {
        return new UserMapper();
    }

    @Bean
    public UserUseCase userUseCase(UserGateway userGateway) {
        return new UserUseCaseImpl(userGateway);
    }

    @Bean
    public UserController userController(
            UserUseCase userUseCase,
            UserMapper userDomainMapper
    ) {
        return new UserController(userUseCase, userDomainMapper);
    }
}
