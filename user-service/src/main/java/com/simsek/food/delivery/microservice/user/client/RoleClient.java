package com.simsek.food.delivery.microservice.user.client;

import com.simsek.food.delivery.microservice.user.dto.RoleResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "/api/role")
public interface RoleClient {

    @GetExchange(url = "/get-all")
    List<RoleResponse> getAll(@RequestParam Long userId);

    @PostExchange(url = "/initialize")
    void initializedRole(@RequestParam Long userId);

}
