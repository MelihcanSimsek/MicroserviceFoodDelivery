package com.simsek.food.delivery.microservice.payment.controller;

import com.simsek.food.delivery.microservice.payment.dto.InitPaymentRequest;
import com.simsek.food.delivery.microservice.payment.dto.PaymentResponse;
import com.simsek.food.delivery.microservice.payment.dto.UpdatePaymentRequest;
import com.simsek.food.delivery.microservice.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/init/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void initPayment(@PathVariable Long userId)
    {
        paymentService.initPayment(userId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UpdatePaymentRequest paymentRequest)
    {
        paymentService.updatePayment(paymentRequest);
    }

    @GetMapping("/get/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse getPayment(@PathVariable("userId") Long userId)
    {
        return paymentService.getUserPayment(userId);
    }


}
