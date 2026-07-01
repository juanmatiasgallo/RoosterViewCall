package com.roostercode.roosterviewcall.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI roosterViewCallOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RoosterViewCall API")
                        .description("Queue and kitchen management platform for food businesses")
                        .version("v0.1.0"));
    }
}
