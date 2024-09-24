package com.simsek.food.delivery.microservice.notification.repository;

import com.simsek.food.delivery.microservice.notification.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
}
