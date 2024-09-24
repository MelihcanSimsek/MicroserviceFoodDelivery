package com.simsek_food_delivery_microservice.menu.service;


import com.simsek_food_delivery_microservice.menu.dto.CreateMenuRequest;
import com.simsek_food_delivery_microservice.menu.dto.MenuResponse;
import com.simsek_food_delivery_microservice.menu.dto.UpdateMenuRequest;
import com.simsek_food_delivery_microservice.menu.model.Menu;
import com.simsek_food_delivery_microservice.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;

    public void createMenu(CreateMenuRequest menuRequest)
    {
        Menu menu = Menu.builder()
                .restaurantCode(menuRequest.restaurantCode())
                .description(menuRequest.description())
                .price(menuRequest.price())
                .name(menuRequest.name())
                .build();

        menuRepository.save(menu);

        log.info("Menu {} is created",menu.getId());
    }

    public void updateMenu(UpdateMenuRequest menuRequest)
    {
        Menu menu = Menu.builder()
                .id(menuRequest.id())
                .restaurantCode(menuRequest.restaurantCode())
                .description(menuRequest.description())
                .price(menuRequest.price())
                .name(menuRequest.name())
                .build();

        menuRepository.save(menu);

        log.info("Menu {} is updated",menu.getId());
    }

    public void deleteMenu(String id)
    {
        Optional<Menu> menu = menuRepository.findById(id);
        menuRepository.delete(menu.get());

        log.info("Menu {} is deleted",menu.get().getId());
    }

    public List<MenuResponse> getAllMenu(String restaurantCode)
    {
        List<Menu> menuList = menuRepository.findByRestaurantCode(restaurantCode);
        return  menuList.stream().map(this::mapToMenuResponse).toList();
    }

    private MenuResponse mapToMenuResponse(Menu menu)
    {
        return new MenuResponse(menu.getId(),
                menu.getRestaurantCode(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice());
    }

}
