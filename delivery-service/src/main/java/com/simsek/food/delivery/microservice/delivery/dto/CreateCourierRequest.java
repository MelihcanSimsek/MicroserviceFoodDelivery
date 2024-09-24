package com.simsek.food.delivery.microservice.delivery.dto;

import java.util.Date;

public record CreateCourierRequest(
        String firstName,
        String lastName,
        String identityNumber,
        String phone,
        String plateNumber,
        Date birthDate
) {
}
