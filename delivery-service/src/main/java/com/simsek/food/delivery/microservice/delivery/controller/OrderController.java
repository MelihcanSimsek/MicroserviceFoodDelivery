package com.simsek.food.delivery.microservice.delivery.controller;


import com.simsek.food.delivery.microservice.delivery.dto.OrderResponse;
import com.simsek.food.delivery.microservice.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courier-order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/assign")
    @ResponseStatus(HttpStatus.OK)
    public void assign(@RequestParam Long courierId,@RequestParam UUID orderNumber)
    {
        orderService.assignCourier(courierId,orderNumber);
    }

    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.OK)
    public void complete(@RequestParam UUID orderNumber)
    {
        orderService.completeDelivery(orderNumber);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancel(@RequestParam UUID orderNumber)
    {
        orderService.cancelDelivery(orderNumber);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAll()
    {
        return orderService.getAllOrderByPending();
    }

    @GetMapping("/get-assigned-order")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllAssignedOrder(@RequestParam Long courierId)
    {
        return orderService.getAllOrderByAssigned(courierId);
    }

}
