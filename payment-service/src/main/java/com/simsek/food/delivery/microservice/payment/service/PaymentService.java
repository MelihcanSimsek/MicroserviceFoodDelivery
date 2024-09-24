package com.simsek.food.delivery.microservice.payment.service;

import com.simsek.food.delivery.microservice.payment.dto.PaymentResponse;
import com.simsek.food.delivery.microservice.payment.dto.UpdatePaymentRequest;
import com.simsek.food.delivery.microservice.order.event.OrderEvent;
import com.simsek.food.delivery.microservice.payment.model.Payment;
import com.simsek.food.delivery.microservice.payment.model.enums.OrderStatus;
import com.simsek.food.delivery.microservice.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Transactional
    public void initPayment(Long userId) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setBalance(BigDecimal.ZERO);

        paymentRepository.save(payment);

        log.info("Payment initialized for userID:{} balance:{} date:{}",
                payment.getUserId(),
                payment.getBalance(),
                payment.getCreationDate());
    }

    @Transactional
    @CacheEvict(value = "payments", key = "#paymentRequest.userId()")
    public void updatePayment(UpdatePaymentRequest paymentRequest) {
        Payment payment = paymentRepository.findByUserId(paymentRequest.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        BigDecimal oldBalance = payment.getBalance();
        payment.setBalance(oldBalance.add(paymentRequest.balanceForAdding()));
        payment.setUpdateDate((new Date()));
        paymentRepository.save(payment);

        log.info("Payment updated for userID:{} oldBalance:{} to newBalance:{} date:{}",
                payment.getUserId(),
                oldBalance,
                payment.getBalance(),
                payment.getUpdateDate());
    }

    @Cacheable(value = "payments", key = "#userId")
    public PaymentResponse getUserPayment(Long userId) {
        Payment payment = paymentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToPaymentResponse(payment);
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getUserId(), payment.getBalance());
    }

    @Transactional
    @CacheEvict(value = "payments", key = "#orderEvent.getUserId()")
    private void updateUserBalanceForOrderCreated(OrderEvent orderEvent) {
        try {
            Payment payment = paymentRepository.findByUserId(orderEvent.getUserId())
                    .orElseThrow(() -> new RuntimeException("Payment not found"));
            if (payment.getBalance().compareTo(orderEvent.getPrice()) < 0) {
                throw new RuntimeException("User Balance not enough for ordering");
            }

            payment.setBalance(payment.getBalance().subtract(orderEvent.getPrice()));
            payment.setUpdateDate(new Date());

            paymentRepository.save(payment);
            log.info("User balance updated for ordering. userId:{} balance:{} date:{}",
                    orderEvent.getUserId(),
                    payment.getBalance(),
                    payment.getUpdateDate());

            orderEvent.setStatus(OrderStatus.PAYMENT_COMPLETED.name());
            orderEvent.setCreationDate(new Date());
            kafkaTemplate.send("payment-completed", orderEvent);

        } catch (Exception e) {
            throwPaymentFailedEvent(orderEvent);
            throwOrderFailedEvent(orderEvent);
        }
    }

    @Transactional
    @CacheEvict(value = "payments", key = "#orderEvent.getUserId()")
    private void updateUserBalanceForOrderFailed(OrderEvent orderEvent) {
        Payment payment = paymentRepository.findByUserId(orderEvent.getUserId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setBalance(payment.getBalance().add(orderEvent.getPrice()));
        payment.setUpdateDate(new Date());
        paymentRepository.save(payment);

        log.info("User balance updated for order fail. userId:{} balance:{} date:{}",
                orderEvent.getUserId(),
                payment.getBalance(),
                payment.getUpdateDate());

        throwOrderFailedEvent(orderEvent);
    }

    private void throwOrderFailedEvent(OrderEvent orderEvent) {
        orderEvent.setStatus(OrderStatus.ORDER_FAILED.name());
        orderEvent.setCreationDate(new Date());
        kafkaTemplate.send("order-failed", orderEvent);
    }

    private void throwPaymentFailedEvent(OrderEvent orderEvent) {
        orderEvent.setStatus(OrderStatus.PAYMENT_FAILED.name());
        orderEvent.setCreationDate(new Date());
        kafkaTemplate.send("payment-failed", orderEvent);
    }

    @KafkaListener(topics = {"order-created"})
    public void consumeOrderCreated(OrderEvent orderEvent) {
        log.info("Kafka order created event taken from listener: orderNumber:{} date: {}",
                orderEvent.getOrderNumber(),
                orderEvent.getCreationDate());
        updateUserBalanceForOrderCreated(orderEvent);
    }

    @KafkaListener(topics = {"restaurant-rejected", "delivery-failed"})
    public void consumeOrderFailed(OrderEvent orderEvent) {
        log.info("Kafka order failed event taken from listener: orderNumber: {} date: {}",
                orderEvent.getOrderNumber(),
                orderEvent.getCreationDate());
        updateUserBalanceForOrderFailed(orderEvent);
    }


}
