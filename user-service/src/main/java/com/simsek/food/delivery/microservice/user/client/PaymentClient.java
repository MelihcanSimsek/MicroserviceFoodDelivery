package com.simsek.food.delivery.microservice.user.client;


import com.simsek.food.delivery.microservice.user.dto.InitPaymentRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/api/payment")
public interface PaymentClient {

    @PostExchange(url = "/init/{userId}")
    public void initPayment(@PathVariable Long userId);
}
