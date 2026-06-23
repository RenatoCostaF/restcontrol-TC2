package com.restcontrol.restcontrol_TC2.infra.config;

import com.restcontrol.restcontrol_TC2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_TC2.domain.useCase.RestaurantUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    public RestaurantUseCase restaurantUseCase(RestaurantGateway restaurantGateway) {
        return new RestaurantUseCase(restaurantGateway);
    }
}
