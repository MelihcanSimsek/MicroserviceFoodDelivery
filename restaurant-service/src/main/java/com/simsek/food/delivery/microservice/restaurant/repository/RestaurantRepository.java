package com.simsek.food.delivery.microservice.restaurant.repository;

import com.simsek.food.delivery.microservice.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findByRestaurantCode(String restaurantCode);
}
