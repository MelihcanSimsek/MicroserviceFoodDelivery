package com.simsek_food_delivery_microservice.menu.dto;

import java.math.BigDecimal;

public record UpdateMenuRequest(String id, String restaurantCode, String name, String description, BigDecimal price) {
}
