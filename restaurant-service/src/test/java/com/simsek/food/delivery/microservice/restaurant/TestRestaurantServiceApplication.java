package com.simsek.food.delivery.microservice.restaurant;

import org.springframework.boot.SpringApplication;

public class TestRestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(RestaurantServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
