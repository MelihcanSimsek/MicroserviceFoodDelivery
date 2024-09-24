package com.simsek.food.delivery.microservice.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI userServiceAPI()
    {
        return new OpenAPI()
                .info(new Info().title("User Service API")
                        .description("This is a REST API for User Service")
                        .version("v0.0.1")
                        .license(new License().name("Spring Boot Microservice License")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the User Service Github Documentation")
                        .url("https://github.com/MelihcanSimsek/FoodDeliveryMicroservice"));
    }
}
