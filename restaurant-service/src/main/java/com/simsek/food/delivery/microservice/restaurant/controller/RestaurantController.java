package com.simsek.food.delivery.microservice.restaurant.controller;

import com.simsek.food.delivery.microservice.restaurant.dto.CreateRestaurantRequest;
import com.simsek.food.delivery.microservice.restaurant.dto.RestaurantResponse;
import com.simsek.food.delivery.microservice.restaurant.dto.UpdateRestaurantRequest;
import com.simsek.food.delivery.microservice.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateRestaurantRequest restaurantRequest)
    {
        restaurantService.createRestaurant(restaurantRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UpdateRestaurantRequest restaurantRequest)
    {
        restaurantService.updateRestaurant(restaurantRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestBody Long id)
    {
        restaurantService.deleteRestaurant(id);
    }

    @GetMapping("/get-all/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantResponse> getAll(@PathVariable("page") int page,@PathVariable("size") int size)
    {
        return restaurantService.getRestaurants(page,size);
    }

    @GetMapping("/get-by-restaurant-code/{restaurantCode}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantResponse getByRestaurantCode(@PathVariable("restaurantCode") String restaurantCode)
    {
        return restaurantService.getRestaurantByRestaurantCode(restaurantCode);
    }



}


