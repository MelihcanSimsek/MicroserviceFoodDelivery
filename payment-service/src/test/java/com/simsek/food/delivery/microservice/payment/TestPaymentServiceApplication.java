package com.simsek.food.delivery.microservice.payment;

import org.springframework.boot.SpringApplication;

public class TestPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PaymentServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
