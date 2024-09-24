package com.simsek.food.delivery.microservice.delivery.service;

import com.simsek.food.delivery.microservice.delivery.dto.OrderResponse;
import com.simsek.food.delivery.microservice.order.event.OrderEvent;
import com.simsek.food.delivery.microservice.delivery.model.Courier;
import com.simsek.food.delivery.microservice.delivery.model.Order;
import com.simsek.food.delivery.microservice.delivery.model.enums.OrderStatus;
import com.simsek.food.delivery.microservice.delivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CourierService courierService;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void assignCourier(Long courierId, UUID orderNumber) {
        Courier courier = courierService.getById(courierId);
        Order order = orderRepository.findByOrderNumber(orderNumber);
        order.setCourier(courier);
        order.setStatus(OrderStatus.ASSIGNED);
        order.setUpdateDate(new Date());

        log.info("Order {} was assigned by Courier {}", order.getOrderNumber(), order.getCourier().getId());

        orderRepository.save(order);

        OrderEvent orderEvent = createOrderEvent(order, OrderStatus.DELIVERY_STARTED);
        kafkaTemplate.send("delivery-started", orderEvent);
    }

    public void completeDelivery(UUID orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        order.setUpdateDate(new Date());
        order.setStatus(OrderStatus.DELIVERED);

        log.info("Order {} was completed by Courier {}", order.getOrderNumber(), order.getCourier().getId());

        orderRepository.save(order);

        OrderEvent orderEvent = createOrderEvent(order, OrderStatus.ORDER_COMPLETED);
        kafkaTemplate.send("order-completed", orderEvent);
    }

    public void cancelDelivery(UUID orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        order.setUpdateDate(new Date());
        order.setStatus(OrderStatus.CANCELED);

        orderRepository.save(order);

        log.info("Order {} was canceled by Courier {}", order.getOrderNumber(), order.getCourier().getId());

        OrderEvent orderEvent = createOrderEvent(order, OrderStatus.DELIVERY_FAILED);
        kafkaTemplate.send("delivery-failed", orderEvent);
    }

    private OrderEvent createOrderEvent(Order order, OrderStatus status) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderNumber(order.getOrderNumber());
        orderEvent.setCreationDate(new Date());
        orderEvent.setEmail(order.getEmail());
        orderEvent.setQuantity(order.getQuantity());
        orderEvent.setPrice(order.getPrice());
        orderEvent.setMenuId(order.getMenuId());
        orderEvent.setUserId(order.getUserId());
        orderEvent.setRestaurantCode(order.getRestaurantCode());
        orderEvent.setStatus(status.name());
        return orderEvent;
    }

    public List<OrderResponse> getAllOrderByPending() {
        List<Order> orderList = orderRepository.findByStatus(OrderStatus.PENDING);
        return orderList.stream().map(this::mapToOrderResponse).toList();
    }

    public List<OrderResponse> getAllOrderByAssigned(Long courierId) {
        List<Order> orderList = orderRepository.findByCourierIdAndStatus(courierId, OrderStatus.ASSIGNED);
        return orderList.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber().toString(), order.getRestaurantCode(), order.getUserId(), order.getPrice()
                , order.getStatus(), order.getCreationDate());
    }

    private void processRestaurantAccept(OrderEvent event) {
        Order order = new Order();
        order.setOrderNumber(event.getOrderNumber());
        order.setStatus(OrderStatus.PENDING);
        order.setPrice(event.getPrice());
        order.setRestaurantCode(event.getRestaurantCode());
        order.setMenuId(event.getMenuId());
        order.setQuantity(event.getQuantity());
        order.setUserId(event.getUserId());
        order.setEmail(event.getEmail());
        order.setCreationDate(new Date());

        orderRepository.save(order);
    }

    @KafkaListener(topics = {"restaurant-accepted"})
    public void consumeRestaurantAccepted(OrderEvent orderEvent) {
        log.info("Kafka event taken from listener. orderNumber: {} date: {}", orderEvent.getOrderNumber(), new Date());
        processRestaurantAccept(orderEvent);
    }
}
