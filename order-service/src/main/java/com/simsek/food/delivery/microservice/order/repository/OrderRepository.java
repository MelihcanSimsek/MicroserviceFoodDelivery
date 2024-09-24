package com.simsek.food.delivery.microservice.order.repository;

import com.simsek.food.delivery.microservice.order.dto.OrderResponse;
import com.simsek.food.delivery.microservice.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<List<Order>> findByOrderNumber(UUID orderNumber);
    List<Order> findByUserId(Long userId);
}
