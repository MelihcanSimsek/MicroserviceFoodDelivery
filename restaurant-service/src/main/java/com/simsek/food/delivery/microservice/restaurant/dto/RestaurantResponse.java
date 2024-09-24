package com.simsek.food.delivery.microservice.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse implements Serializable{
    private Long Id;
    private String restaurantCode;
    private String name;
    private String address;
    private String city;
    private String country;
    private String email;
    private String phone;
    private Date creationDate;
}
