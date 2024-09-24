package com.simsek.food.delivery.microservice.role.service;

import com.simsek.food.delivery.microservice.role.dto.CreateRoleRequest;
import com.simsek.food.delivery.microservice.role.dto.RoleResponse;
import com.simsek.food.delivery.microservice.role.model.Role;
import com.simsek.food.delivery.microservice.role.model.enums.RoleStatus;
import com.simsek.food.delivery.microservice.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public void initializedRole(Long userId)
    {
        Role role = new Role();
        role.setUserId(userId);
        role.setName(RoleStatus.USER);

        roleRepository.save(role);
    }

    @CacheEvict(value = "roles",key = "#createRoleRequest.userId()")
    public void add(CreateRoleRequest createRoleRequest)
    {
        checkUserHasAlreadyRole(createRoleRequest.userId(), createRoleRequest.status());

        Role role = new Role();
        role.setName(createRoleRequest.status());
        role.setUserId(createRoleRequest.userId());

        roleRepository.save(role);

        log.info("User:{} took a role {}",role.getUserId(),role.getName());
    }

    public void delete(Long id)
    {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

        roleRepository.delete(role);

        log.info("User role deleted user:{} role:{}",role.getUserId(),role.getName());
    }

    @Cacheable(value = "roles",key = "#userId")
    public List<RoleResponse> getAllRoleByUserId(Long userId)
    {
        List<Role> roleList = roleRepository.findByUserId(userId);

        return roleList.stream().map(this::mapToRoleResponse).toList();
    }

    private RoleResponse mapToRoleResponse(Role role)
    {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        return roleResponse;
    }

    private void checkUserHasAlreadyRole(Long userId,RoleStatus status)
    {
        Role role = roleRepository.findByUserIdAndName(userId,status);
        if(role != null)
        {
            throw new RuntimeException("User already has a role "+role.getUserId());
        }
    }
}
