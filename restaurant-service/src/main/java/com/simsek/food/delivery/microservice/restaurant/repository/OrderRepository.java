package com.simsek.food.delivery.microservice.restaurant.repository;

import com.simsek.food.delivery.microservice.restaurant.model.Order;
import com.simsek.food.delivery.microservice.restaurant.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<List<Order>> findByRestaurantCodeAndStatus(String restaurantCode, OrderStatus status);
}
