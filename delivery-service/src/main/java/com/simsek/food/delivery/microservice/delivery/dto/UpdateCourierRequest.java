package com.simsek.food.delivery.microservice.delivery.dto;

public record UpdateCourierRequest(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String plateNumber,
        Boolean isWorking
) {
}
