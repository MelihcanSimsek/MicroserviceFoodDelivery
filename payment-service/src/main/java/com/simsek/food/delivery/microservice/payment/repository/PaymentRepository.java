package com.simsek.food.delivery.microservice.payment.repository;

import com.simsek.food.delivery.microservice.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByUserId(Long userId);
}
