package com.simsek.food.delivery.microservice.restaurant.dto;

import java.util.Date;

public record UpdateRestaurantRequest(
        Long Id,
        String restaurantCode,
        String name,
        String address,
        String city,
        String country,
        String phone,
        String email
) {
}
