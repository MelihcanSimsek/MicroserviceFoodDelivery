package com.simsek.food.delivery.microservice.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponse implements Serializable{
    private Long id;
    private Long userId;
    private BigDecimal balance;
}
