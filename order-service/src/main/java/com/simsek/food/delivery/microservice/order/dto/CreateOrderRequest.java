package com.simsek.food.delivery.microservice.order.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(
        Long userId,
        String restaurantCode,
        String menuId,
        BigDecimal price,
        Integer quantity
) {
}
