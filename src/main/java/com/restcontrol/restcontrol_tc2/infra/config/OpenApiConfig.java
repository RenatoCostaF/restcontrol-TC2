package com.restcontrol.restcontrol_tc2.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restControlOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("RestControl TC2 API")
                        .description("Projeto desenvolvido para o Tech-Challenge - Fase 2")
                        .version("v0.0.1")
                        .license(new License().name("restcontrol-tc2").url("https://github.com/RenatoCostaF/restcontrol-TC2"))
                );
    }
}
