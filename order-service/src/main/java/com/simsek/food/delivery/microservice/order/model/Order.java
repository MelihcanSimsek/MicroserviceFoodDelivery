package com.simsek.food.delivery.microservice.order.model;

import com.simsek.food.delivery.microservice.order.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private UUID orderNumber;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "restaurant_code")
    private String restaurantCode;

    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "creation_date")
    private Date creationDate;

}
