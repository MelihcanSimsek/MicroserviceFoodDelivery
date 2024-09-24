package com.simsek.food.delivery.microservice.delivery.dto;

import java.util.Date;

public record CourierResponse(Long id, String firstName, String lastName, String phone, String identityNumber,
                              Date birthDate ,String plateNumber, Boolean isWorking) {
}
