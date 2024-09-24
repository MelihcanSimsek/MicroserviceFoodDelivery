package com.simsek_food_delivery_microservice.menu.repository;

import com.simsek_food_delivery_microservice.menu.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuRepository extends MongoRepository<Menu,String> {
    List<Menu> findByRestaurantCode(String restaurantCode);
}
