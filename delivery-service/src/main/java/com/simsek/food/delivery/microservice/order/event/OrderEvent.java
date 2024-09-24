package com.simsek.food.delivery.microservice.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long userId;
    private UUID orderNumber;
    private String email;
    private String restaurantCode;
    private String menuId;
    private BigDecimal price;
    private Integer quantity;
    private String status;
    private Date creationDate;
}
