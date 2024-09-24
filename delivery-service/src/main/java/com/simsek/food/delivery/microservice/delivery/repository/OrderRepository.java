package com.simsek.food.delivery.microservice.delivery.repository;

import com.simsek.food.delivery.microservice.delivery.model.Order;
import com.simsek.food.delivery.microservice.delivery.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository  extends JpaRepository<Order,Long> {
    Order findByOrderNumber(UUID orderNumber);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCourierIdAndStatus(Long courierId,OrderStatus status);
}
