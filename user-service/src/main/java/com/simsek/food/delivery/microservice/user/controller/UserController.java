package com.simsek.food.delivery.microservice.user.controller;

import com.simsek.food.delivery.microservice.user.dto.CreateUserRequest;
import com.simsek.food.delivery.microservice.user.dto.UpdateUserRequest;
import com.simsek.food.delivery.microservice.user.dto.UserDetailResponse;
import com.simsek.food.delivery.microservice.user.dto.UserResponse;
import com.simsek.food.delivery.microservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse add(@RequestBody CreateUserRequest userRequest)
    {
        return userService.add(userRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update(@RequestBody UpdateUserRequest userRequest)
    {
        return userService.update(userRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse delete(@RequestParam Long id)
    {
        return userService.delete(id);
    }

    @GetMapping("/check-user-exists")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkUserIsExists(@RequestParam String email)
    {
        return userService.checkUserIsExists(email);
    }

    @GetMapping("get-user-by-id")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailResponse getUserById(@RequestParam Long userId)
    {
        return userService.getUserById(userId);
    }

    @GetMapping("get-user-by-email")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailResponse getUserByEmail(@RequestParam String email)
    {
        return userService.getUserByEmail(email);
    }




}
