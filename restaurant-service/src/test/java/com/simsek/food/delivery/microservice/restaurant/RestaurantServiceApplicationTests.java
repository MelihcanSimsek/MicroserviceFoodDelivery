package com.simsek.food.delivery.microservice.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class RestaurantServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
