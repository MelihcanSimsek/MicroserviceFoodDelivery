package com.simsek.food.delivery.microservice.order.controller;

import com.simsek.food.delivery.microservice.order.dto.CreateOrderRequest;
import com.simsek.food.delivery.microservice.order.dto.OrderResponse;
import com.simsek.food.delivery.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody List<CreateOrderRequest> createOrderRequestList,@RequestParam String email)
    {
        orderService.placeOrder(createOrderRequestList,email);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllByUserID(@RequestParam Long userId)
    {
        return orderService.getAllOrdersByUserId(userId);
    }

    @GetMapping("/get-orders-by-order-number")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByOrderNumber(@RequestParam UUID orderNumber)
    {
        return orderService.getOrdersByOrderNumber(orderNumber);
    }

}
