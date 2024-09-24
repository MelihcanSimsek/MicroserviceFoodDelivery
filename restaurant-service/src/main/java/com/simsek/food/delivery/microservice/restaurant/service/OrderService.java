package com.simsek.food.delivery.microservice.restaurant.service;

import com.simsek.food.delivery.microservice.restaurant.dto.RestaurantOrderResponse;
import com.simsek.food.delivery.microservice.restaurant.dto.UpdateRestaurantOrderRequest;
import com.simsek.food.delivery.microservice.order.event.OrderEvent;
import com.simsek.food.delivery.microservice.restaurant.model.Order;
import com.simsek.food.delivery.microservice.restaurant.model.enums.OrderStatus;
import com.simsek.food.delivery.microservice.restaurant.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @KafkaListener(topics = {"payment-completed"})
    public void consumePaymentCompleted(OrderEvent orderEvent) {
        log.info("Kafka event taken from listener orderNumber: {} date: {}", orderEvent.getOrderNumber(), new Date());
        processPaymentCompletedEvent(orderEvent);
    }

    private void processPaymentCompletedEvent(OrderEvent orderEvent) {
        Order order = new Order();
        order.setOrderNumber(orderEvent.getOrderNumber());
        order.setEmail(orderEvent.getEmail());
        order.setPrice(orderEvent.getPrice());
        order.setQuantity(orderEvent.getQuantity());
        order.setMenuId(orderEvent.getMenuId());
        order.setRestaurantCode(orderEvent.getRestaurantCode());
        order.setUserId(orderEvent.getUserId());
        order.setStatus(OrderStatus.PENDING);
        order.setCreationDate(new Date());

        orderRepository.save(order);
    }

    public void updateRestaurantOrder(UpdateRestaurantOrderRequest orderRequest)
    {
        Order order = orderRepository.findById(orderRequest.id())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(orderRequest.status().name()));
        orderRepository.save(order);

        OrderEvent event = new OrderEvent(order.getUserId(),
                order.getOrderNumber(),
                order.getEmail(),
                order.getRestaurantCode(),
                order.getMenuId(),
                order.getPrice(),
                order.getQuantity(),
                "",
                new Date());
        if(OrderStatus.RESTAURANT_ACCEPTED.equals(orderRequest.status()))
        {
            event.setStatus(OrderStatus.RESTAURANT_ACCEPTED.name());
            kafkaTemplate.send("restaurant-accepted",event);
        }
        else
        {
            event.setStatus(OrderStatus.RESTAURANT_REJECTED.name());
            kafkaTemplate.send("restaurant-rejected",event);
        }
    }

    public List<RestaurantOrderResponse> getAllPendingRestaurantOrderByRestaurantCode(String restaurantCode) {
        List<Order> orderList = orderRepository.findByRestaurantCodeAndStatus(restaurantCode,OrderStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("Orders not found"));

        return orderList.stream().sorted(Comparator.comparing(Order::getCreationDate))
                .map(this::mapToRestaurantOrderResponse).toList();
    }

    public List<RestaurantOrderResponse> getAllAcceptedRestaurantOrderByRestaurantCode(String restaurantCode) {
        List<Order> orderList = orderRepository.findByRestaurantCodeAndStatus(restaurantCode,OrderStatus.RESTAURANT_ACCEPTED)
                .orElseThrow(() -> new RuntimeException("Orders not found"));

        return orderList.stream().sorted(Comparator.comparing(Order::getCreationDate).reversed())
                .map(this::mapToRestaurantOrderResponse).toList();
    }

    private RestaurantOrderResponse mapToRestaurantOrderResponse(Order order) {
        return new RestaurantOrderResponse(order.getId(),
                order.getUserId(),
                order.getOrderNumber(),
                order.getEmail(),
                order.getRestaurantCode(),
                order.getMenuId(),
                order.getPrice(),
                order.getQuantity(),
                order.getStatus(),
                order.getCreationDate());
    }
}


