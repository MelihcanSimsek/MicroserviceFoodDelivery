package com.simsek.food.delivery.microservice.user.dto;

public record UpdateUserRequest(
        Long id,
        String firstName,
        String lastName,
        String email,
        String address,
        String phone
) {
}
