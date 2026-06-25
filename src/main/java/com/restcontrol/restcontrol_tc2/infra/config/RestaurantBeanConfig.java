package com.restcontrol.restcontrol_tc2.infra.config;

import com.restcontrol.restcontrol_tc2.domain.controller.RestaurantController;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.UserTypeGateway;
import com.restcontrol.restcontrol_tc2.domain.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.RestaurantUseCase;
import com.restcontrol.restcontrol_tc2.domain.usecase.impl.RestaurantUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantBeanConfig {

    @Bean
    public RestaurantMapper restaurantDomainMapper() {
        return new RestaurantMapper();
    }

    @Bean
    public RestaurantUseCase restaurantUseCase(
            RestaurantGateway restaurantGateway,
            UserGateway userGateway,
            UserTypeGateway userTypeGateway
    ) {
        return new RestaurantUseCaseImpl(restaurantGateway, userGateway, userTypeGateway);
    }

    @Bean
    public RestaurantController restaurantController(
            RestaurantUseCase restaurantUseCase,
            RestaurantMapper restaurantDomainMapper
    ) {
        return new RestaurantController(restaurantUseCase, restaurantDomainMapper);
    }
}
