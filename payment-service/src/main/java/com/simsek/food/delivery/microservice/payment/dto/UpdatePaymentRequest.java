package com.simsek.food.delivery.microservice.payment.dto;

import java.math.BigDecimal;

public record UpdatePaymentRequest(Long userId, BigDecimal balanceForAdding) {
}
