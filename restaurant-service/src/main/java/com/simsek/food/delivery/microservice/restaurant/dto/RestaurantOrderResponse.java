package com.simsek.food.delivery.microservice.restaurant.dto;

import com.simsek.food.delivery.microservice.restaurant.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOrderResponse {
    private Long id;
    private Long userId;
    private UUID orderNumber;
    private String email;
    private String restaurantCode;
    private String menuId;
    private BigDecimal price;
    private Integer quantity;
    private OrderStatus status;
    private Date creationDate;
}
