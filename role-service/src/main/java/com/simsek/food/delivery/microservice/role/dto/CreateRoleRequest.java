package com.simsek.food.delivery.microservice.role.dto;

import com.simsek.food.delivery.microservice.role.model.enums.RoleStatus;

public record CreateRoleRequest(Long userId, RoleStatus status) {
}
