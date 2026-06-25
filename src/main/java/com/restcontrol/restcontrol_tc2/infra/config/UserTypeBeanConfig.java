package com.restcontrol.restcontrol_tc2.infra.config;

import com.restcontrol.restcontrol_tc2.domain.controller.UserTypeController;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.mapper.UserTypeMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserTypeUseCase;
import com.restcontrol.restcontrol_tc2.domain.usecase.impl.UserTypeUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeBeanConfig {

    @Bean
    public UserTypeMapper userTypeDomainMapper() {
        return new UserTypeMapper();
    }

    @Bean
    public UserTypeUseCase userTypeUseCase(UserTypeGateway userTypeGateway) {
        return new UserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public UserTypeController userTypeController(
            UserTypeUseCase userTypeUseCase,
            UserTypeMapper userTypeDomainMapper
    ) {
        return new UserTypeController(userTypeUseCase, userTypeDomainMapper);
    }
}
