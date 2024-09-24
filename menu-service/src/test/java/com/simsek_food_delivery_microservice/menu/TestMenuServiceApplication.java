package com.simsek_food_delivery_microservice.menu;

import org.springframework.boot.SpringApplication;

public class TestMenuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MenuServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
