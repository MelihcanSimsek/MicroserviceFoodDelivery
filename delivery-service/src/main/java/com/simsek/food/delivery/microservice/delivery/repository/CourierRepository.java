package com.simsek.food.delivery.microservice.delivery.repository;

import com.simsek.food.delivery.microservice.delivery.dto.CourierResponse;
import com.simsek.food.delivery.microservice.delivery.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier,Long> {

}
