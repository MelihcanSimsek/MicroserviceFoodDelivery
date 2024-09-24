package com.simsek.food.delivery.microservice.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PaymentServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
