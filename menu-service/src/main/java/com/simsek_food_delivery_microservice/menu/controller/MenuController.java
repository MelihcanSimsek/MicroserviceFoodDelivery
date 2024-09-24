package com.simsek_food_delivery_microservice.menu.controller;

import com.simsek_food_delivery_microservice.menu.dto.CreateMenuRequest;
import com.simsek_food_delivery_microservice.menu.dto.MenuResponse;
import com.simsek_food_delivery_microservice.menu.dto.UpdateMenuRequest;
import com.simsek_food_delivery_microservice.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateMenuRequest menuRequest)
    {
        menuService.createMenu(menuRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UpdateMenuRequest menuRequest)
    {
        menuService.updateMenu(menuRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam String id)
    {
        menuService.deleteMenu(id);
    }

    @GetMapping("/get-all/{restaurantCode}")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuResponse> getAll(@PathVariable("restaurantCode") String restaurantCode)
    {
        return menuService.getAllMenu(restaurantCode);
    }

}
