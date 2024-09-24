package com.simsek_food_delivery_microservice.menu.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI menuServiceAPI()
    {
        return new OpenAPI()
                .info(new Info().title("Menu Service API")
                        .description("This is a REST API for Menu Service")
                        .version("v0.0.1")
                        .license(new License().name("Spring Boot Microservice License")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Menu Service Github Documentation")
                        .url("https://github.com/MelihcanSimsek/FoodDeliveryMicroservice"));
    }
}
