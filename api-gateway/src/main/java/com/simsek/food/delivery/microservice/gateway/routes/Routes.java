package com.simsek.food.delivery.microservice.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> menuServiceRoute()
    {
        return GatewayRouterFunctions.route("menu_service")
                .route(RequestPredicates.path("/api/menu/**"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> menuServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("menu_service_swagger")
                .route(RequestPredicates.path("/aggregate/menu-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> restaurantServiceRoute()
    {
        return GatewayRouterFunctions.route("restaurant_service")
                .route(RequestPredicates.path("/api/restaurant/**"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> restaurantServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("restaurant_service_swagger")
                .route(RequestPredicates.path("/aggregate/restaurant-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoute()
    {
        return GatewayRouterFunctions.route("payment_service")
                .route(RequestPredicates.path("/api/payment/**"), HandlerFunctions.http("http://localhost:8083"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("payment_service_swagger")
                .route(RequestPredicates.path("/aggregate/payment-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8083"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> deliveryServiceRoute()
    {
        return GatewayRouterFunctions.route("delivery_service")
                .route(RequestPredicates.path("/api/courier/**"), HandlerFunctions.http("http://localhost:8084"))
                .route(RequestPredicates.path("/api/courier-order/**"), HandlerFunctions.http("http://localhost:8084"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> deliveryServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("delivery_service_swagger")
                .route(RequestPredicates.path("/aggregate/delivery-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8084"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute()
    {
        return GatewayRouterFunctions.route("order_service")
                .route(RequestPredicates.path("/api/order/**"), HandlerFunctions.http("http://localhost:8086"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("order_service_swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8086"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> roleServiceRoute()
    {
        return GatewayRouterFunctions.route("role_service")
                .route(RequestPredicates.path("/api/role/**"), HandlerFunctions.http("http://localhost:8087"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> roleServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("role_service_swagger")
                .route(RequestPredicates.path("/aggregate/role-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8087"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute()
    {
        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/user/**"), HandlerFunctions.http("http://localhost:8088"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwaggerRoute()
    {
        return GatewayRouterFunctions.route("user_service_swagger")
                .route(RequestPredicates.path("/aggregate/user-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8088"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
