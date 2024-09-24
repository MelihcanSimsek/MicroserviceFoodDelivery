package com.simsek.food.delivery.microservice.order.dto;

import com.simsek.food.delivery.microservice.order.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse implements Serializable {
    private Long id;
    private UUID orderNumber;
    private Long userId;
    private String restaurantCode;
    private String menuId;
    private BigDecimal price;
    private Integer quantity;
    private OrderStatus status;
    private  Date creationDate;
}
