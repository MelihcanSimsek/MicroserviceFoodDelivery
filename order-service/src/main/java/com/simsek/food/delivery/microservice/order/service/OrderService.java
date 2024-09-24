package com.simsek.food.delivery.microservice.order.service;

import com.simsek.food.delivery.microservice.order.dto.CreateOrderRequest;
import com.simsek.food.delivery.microservice.order.dto.OrderResponse;
import com.simsek.food.delivery.microservice.order.event.OrderEvent;
import com.simsek.food.delivery.microservice.order.model.Order;
import com.simsek.food.delivery.microservice.order.model.enums.OrderStatus;
import com.simsek.food.delivery.microservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Transactional
    @CacheEvict(value = "orders",key = "#createOrderRequestList.get(0).userId()")
    public void placeOrder(List<CreateOrderRequest> createOrderRequestList,String email)
    {
         createOrderRequestList.forEach(createOrderRequest -> {
             Order order = new Order();
             order.setOrderNumber(UUID.randomUUID());
             order.setUserId(createOrderRequest.userId());
             order.setPrice(createOrderRequest.price());
             order.setQuantity(createOrderRequest.quantity());
             order.setRestaurantCode(createOrderRequest.restaurantCode());
             order.setMenuId(createOrderRequest.menuId());
             order.setStatus(OrderStatus.ORDER_CREATED);
             order.setCreationDate(new Date());
             orderRepository.save(order);

             log.info("User:{} is placed order. order_number:{} restaurant_code:{} at date:{} ",
                     order.getUserId(),
                     order.getOrderNumber(),
                     order.getRestaurantCode(),
                     order.getCreationDate());

             OrderEvent orderEvent = new OrderEvent(order.getUserId(),
                     order.getOrderNumber(),
                     email,
                     order.getRestaurantCode(),
                     order.getMenuId(),
                     order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())),
                     order.getQuantity(),
                     order.getStatus().name(),
                     order.getCreationDate());

             kafkaTemplate.send("order-created",orderEvent);
         });
    }

    @Transactional
    @CacheEvict(value = "orders",key ="#userId" )
    private void changeOrderState(Long userId,UUID orderNumber,OrderStatus status,Date creationDate)
    {
        List<Order> order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Order oldOrder = order.getFirst();
        Order newOrder = new Order();
        newOrder.setOrderNumber(orderNumber);
        newOrder.setStatus(status);
        newOrder.setUserId(userId);
        newOrder.setOrderNumber(orderNumber);
        newOrder.setQuantity(oldOrder.getQuantity());
        newOrder.setPrice(oldOrder.getPrice());
        newOrder.setMenuId(oldOrder.getMenuId());
        newOrder.setRestaurantCode(oldOrder.getRestaurantCode());
        newOrder.setCreationDate(creationDate);

        orderRepository.save(newOrder);

        log.info("Order state changed User:{}  order_number:{} restaurant_code:{} status: {} at date:{}. ",
                newOrder.getUserId(),
                newOrder.getOrderNumber(),
                newOrder.getRestaurantCode(),
                newOrder.getStatus(),
                newOrder.getCreationDate());
    }

    @KafkaListener(topics = {"delivery-failed", "delivery-started", "order-completed", "order-failed",
            "payment-completed", "payment-failed", "restaurant-accepted", "restaurant-rejected"} )
    public void consumeOrderEvents(OrderEvent orderEvent) {
        log.info("Kafka event taken from listener: {} {} ",orderEvent.getOrderNumber(),orderEvent.getStatus());
        changeOrderState(orderEvent.getUserId(), orderEvent.getOrderNumber()
                ,OrderStatus.valueOf(orderEvent.getStatus()),orderEvent.getCreationDate());
    }

    public List<OrderResponse> getOrdersByOrderNumber(UUID orderNumber)
    {
        List<Order> orderList = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderList.stream().sorted(Comparator.comparing(Order::getCreationDate).reversed())
                .map(this::mapToOrderResponse).toList();
    }

    @Cacheable(value = "orders",key = "#userId")
    public List<OrderResponse> getAllOrdersByUserId(Long userId)
    {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream().sorted(Comparator.comparing(Order::getCreationDate).reversed())
                .map(this::mapToOrderResponse).toList();
    }


    private OrderResponse mapToOrderResponse(Order order)
    {
        return new OrderResponse(order.getId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getRestaurantCode(),
                order.getMenuId(),
                order.getPrice(),
                order.getQuantity(),
                order.getStatus(),
                order.getCreationDate());
    }
}
