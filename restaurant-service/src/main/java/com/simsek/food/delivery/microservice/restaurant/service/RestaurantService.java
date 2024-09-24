package com.simsek.food.delivery.microservice.restaurant.service;

import com.simsek.food.delivery.microservice.restaurant.dto.CreateRestaurantRequest;
import com.simsek.food.delivery.microservice.restaurant.dto.RestaurantResponse;
import com.simsek.food.delivery.microservice.restaurant.dto.UpdateRestaurantRequest;
import com.simsek.food.delivery.microservice.restaurant.model.Restaurant;
import com.simsek.food.delivery.microservice.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @CacheEvict(cacheNames = "restaurants",allEntries = true)
    public void createRestaurant(CreateRestaurantRequest restaurantRequest)
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setRestaurantCode(restaurantRequest.name()+"_"+restaurantRequest.country()+"_"+restaurantRequest.city()+"_"+ UUID.randomUUID().toString());
        restaurant.setCity(restaurantRequest.city());
        restaurant.setEmail(restaurantRequest.email());
        restaurant.setAddress(restaurantRequest.address());
        restaurant.setCountry(restaurantRequest.country());
        restaurant.setPhone(restaurantRequest.phone());

        restaurantRepository.save(restaurant);

        log.info("Restaurant {} is created",restaurant.getRestaurantCode());
    }

    @CacheEvict(cacheNames = "restaurants",allEntries = true)
    public void deleteRestaurant(Long id)
    {
        restaurantRepository.deleteById(id);
        log.info("Restaurant {} is deleted",id);
    }

    @CacheEvict(cacheNames = "restaurants", key = "#restaurantRequest.restaurantCode()", allEntries = true)
    public void updateRestaurant(UpdateRestaurantRequest restaurantRequest)
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantRequest.Id());
        restaurant.setName(restaurantRequest.name());
        restaurant.setRestaurantCode(restaurantRequest.restaurantCode());
        restaurant.setCity(restaurantRequest.city());
        restaurant.setEmail(restaurantRequest.email());
        restaurant.setAddress(restaurantRequest.address());
        restaurant.setCountry(restaurantRequest.country());
        restaurant.setPhone(restaurantRequest.phone());

        restaurantRepository.save(restaurant);

        log.info("Restaurant {} is updated",restaurant.getRestaurantCode());
    }


    @Cacheable(value = "restaurants", key = "#restaurantCode")
    public RestaurantResponse getRestaurantByRestaurantCode(String restaurantCode)
    {
        Restaurant restaurant = restaurantRepository.findByRestaurantCode(restaurantCode).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        log.info("Cache not used for this request");
        return  mapToRestaurantResponse(restaurant);
    }

    @Cacheable(cacheNames = "restaurants", key = "#page + '-' + #size")
    public List<RestaurantResponse> getRestaurants(int page,int size)
    {
        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by("name").ascending());
        List<Restaurant> restaurantList = restaurantRepository.findAll(pageRequest).getContent();
        return restaurantList.stream().map(this::mapToRestaurantResponse).toList();
    }

    private RestaurantResponse mapToRestaurantResponse(Restaurant restaurant)
    {
        return new RestaurantResponse(restaurant.getId(),
                restaurant.getRestaurantCode(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCity(),
                restaurant.getCountry(),
                restaurant.getEmail(),
                restaurant.getPhone(),
                restaurant.getCreationDate());
    }
}
