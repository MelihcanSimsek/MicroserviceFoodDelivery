package com.simsek.food.delivery.microservice.delivery.dto;

import com.simsek.food.delivery.microservice.delivery.model.enums.OrderStatus;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String orderNumber,
        String restaurantCode,
        Long userId,
        BigDecimal price,
        OrderStatus status
) {
}



