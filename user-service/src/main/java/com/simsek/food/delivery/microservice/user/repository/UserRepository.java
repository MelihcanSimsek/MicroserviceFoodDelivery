package com.simsek.food.delivery.microservice.user.repository;

import com.simsek.food.delivery.microservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
