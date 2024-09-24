package com.simsek.food.delivery.microservice.restaurant.controller;

import com.simsek.food.delivery.microservice.restaurant.dto.RestaurantOrderResponse;
import com.simsek.food.delivery.microservice.restaurant.dto.UpdateRestaurantOrderRequest;
import com.simsek.food.delivery.microservice.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant-order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateRestaurantOrder(@RequestBody UpdateRestaurantOrderRequest orderRequest)
    {
        orderService.updateRestaurantOrder(orderRequest);
    }

    @GetMapping("/get-all-pending")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantOrderResponse> getAllPendingByRestaurantCode(@RequestParam String restaurantCode)
    {
        return orderService.getAllPendingRestaurantOrderByRestaurantCode(restaurantCode);
    }

    @GetMapping("/get-all-accepted")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantOrderResponse> getAllAcceptedByRestaurantCode(@RequestParam String restaurantCode)
    {
        return orderService.getAllAcceptedRestaurantOrderByRestaurantCode(restaurantCode);
    }

}
