package com.simsek.food.delivery.microservice.delivery.controller;

import com.simsek.food.delivery.microservice.delivery.dto.CourierResponse;
import com.simsek.food.delivery.microservice.delivery.dto.CreateCourierRequest;
import com.simsek.food.delivery.microservice.delivery.dto.UpdateCourierRequest;
import com.simsek.food.delivery.microservice.delivery.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CreateCourierRequest courierRequest)
    {
        courierService.add(courierRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UpdateCourierRequest courierRequest)
    {
        courierService.update(courierRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id)
    {
        courierService.delete(id);
    }

    @PutMapping("/status/")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatus(@RequestParam Long id,@RequestParam String status)
    {
        courierService.updateCourierStatus(id,status);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourierResponse getCourierById(@PathVariable("id") Long id)
    {
        return courierService.getCourierById(id);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<CourierResponse> getAllCourier(@RequestParam("page") Integer page, @RequestParam("size") Integer size)
    {
        return courierService.getAllCourier(page,size);
    }

}
