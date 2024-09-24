package com.simsek.food.delivery.microservice.role.repository;

import com.simsek.food.delivery.microservice.role.model.Role;
import com.simsek.food.delivery.microservice.role.model.enums.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByUserIdAndName(Long userId, RoleStatus name);
    List<Role> findByUserId(Long userId);
}
