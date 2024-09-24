package com.simsek.food.delivery.microservice.user.service;

import com.simsek.food.delivery.microservice.user.client.PaymentClient;
import com.simsek.food.delivery.microservice.user.client.RoleClient;
import com.simsek.food.delivery.microservice.user.dto.*;
import com.simsek.food.delivery.microservice.user.model.User;
import com.simsek.food.delivery.microservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleClient roleClient;
    private final PaymentClient paymentClient;

    @Transactional
    public UserResponse add(CreateUserRequest userRequest)
    {
        User user = new User();
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setPhone(userRequest.phone());
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setStatus(true);
        user.setAddress(userRequest.address());

        userRepository.save(user);

        log.info("User added Id-> {} email-> {} name-> {}.",
                user.getId(),
                user.getEmail(),
                user.getFirstName()+" "+user.getLastName());

        try {
            roleClient.initializedRole(user.getId());
            paymentClient.initPayment(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return mapToUserResponse(user);

    }

    public UserResponse update(UpdateUserRequest userRequest)
    {
        User user = userRepository.findById(userRequest.id()).orElseThrow(() -> new RuntimeException("User not found" + userRequest.id()));
        user.setEmail(userRequest.email());
        user.setPhone(userRequest.phone());
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setAddress(userRequest.address());

        userRepository.save(user);

        log.info("User updated Id-> {} email-> {} name-> {}.",
                user.getId(),
                user.getEmail(),
                user.getFirstName()+" "+user.getLastName());

        return mapToUserResponse(user);
    }

    public UserResponse delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found "+id.toString()));
        user.setStatus(false);
        userRepository.save(user);

        log.info("User safe deleted Id-> {} email-> {} name-> {}.",
                user.getId(),
                user.getEmail(),
                user.getFirstName()+" "+user.getLastName());

        return mapToUserResponse(user);
    }

    public Boolean checkUserIsExists(String email)
    {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public UserDetailResponse getUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        List<RoleResponse> roleResponses = roleClient.getAll(user.getId());

        return mapToUserDetailResponse(user,roleResponses);
    }

    public UserDetailResponse getUserByEmail(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<RoleResponse> roleResponses = roleClient.getAll(user.getId());

        return mapToUserDetailResponse(user,roleResponses);
    }

    private UserDetailResponse mapToUserDetailResponse(User user,List<RoleResponse> roleResponses)
    {
        return new UserDetailResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getStatus(),
                roleResponses);
    }

    private UserResponse mapToUserResponse(User user)
    {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getStatus());
    }

}
