package com.simsek.food.delivery.microservice.user.dto;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String address,
        String phone
) {
}
