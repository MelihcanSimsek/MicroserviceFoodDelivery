package com.simsek.food.delivery.microservice.delivery.dto;

import com.simsek.food.delivery.microservice.delivery.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

public record OrderResponse(
        Long id,
        String orderNumber,
        String restaurantCode,
        Long userId,
        BigDecimal price,
        OrderStatus status,
        Date creationDate
) {
}
