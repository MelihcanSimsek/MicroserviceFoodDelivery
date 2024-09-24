package com.simsek.food.delivery.microservice.restaurant.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI restaurantServiceAPI()
    {
        return new OpenAPI()
                .info(new Info().title("Restaurant Service API")
                        .description("This is a REST API for Restaurant Service")
                        .version("v0.0.1")
                        .license(new License().name("Spring Boot Microservice License")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Restaurant Service Github Documentation")
                        .url("https://github.com/MelihcanSimsek/FoodDeliveryMicroservice"));
    }
}
