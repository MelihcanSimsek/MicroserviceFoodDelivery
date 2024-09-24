package com.simsek.food.delivery.microservice.restaurant.dto;

import com.simsek.food.delivery.microservice.restaurant.model.enums.OrderStatus;

public record UpdateRestaurantOrderRequest(Long id, OrderStatus status) {
}
