package com.simsek.food.delivery.microservice.role.controller;

import com.simsek.food.delivery.microservice.role.dto.CreateRoleRequest;
import com.simsek.food.delivery.microservice.role.dto.RoleResponse;
import com.simsek.food.delivery.microservice.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/initialize")
    @ResponseStatus(HttpStatus.CREATED)
    public void initializedRole(@RequestParam Long userId)
    {
        roleService.initializedRole(userId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CreateRoleRequest createRoleRequest)
    {
        roleService.add(createRoleRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id)
    {
        roleService.delete(id);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleResponse> getAll(@RequestParam Long userId)
    {
        return roleService.getAllRoleByUserId(userId);
    }

}
