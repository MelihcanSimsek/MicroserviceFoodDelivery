package com.simsek.food.delivery.microservice.role.dto;

import com.simsek.food.delivery.microservice.role.model.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse implements Serializable {
    private Long id;
    private RoleStatus name;
}
