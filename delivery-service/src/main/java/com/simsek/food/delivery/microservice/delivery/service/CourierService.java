package com.simsek.food.delivery.microservice.delivery.service;

import com.simsek.food.delivery.microservice.delivery.dto.CourierResponse;
import com.simsek.food.delivery.microservice.delivery.dto.CreateCourierRequest;
import com.simsek.food.delivery.microservice.delivery.dto.UpdateCourierRequest;
import com.simsek.food.delivery.microservice.delivery.model.Courier;
import com.simsek.food.delivery.microservice.delivery.model.enums.CourierStatus;
import com.simsek.food.delivery.microservice.delivery.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;

    public void add(CreateCourierRequest courierRequest)
    {
        Courier courier = new Courier();
        courier.setFirstName(courierRequest.firstName());
        courier.setLastName(courierRequest.lastName());
        courier.setPhone(courierRequest.phone());
        courier.setIdentityNumber(courierRequest.identityNumber());
        courier.setPlateNumber(courierRequest.plateNumber());
        courier.setIsWorking(true);
        courier.setBirthDate(courierRequest.birthDate());
        courier.setCreationDate(new Date());
        courier.setUpdateDate(new Date());
        courier.setStatus(CourierStatus.OFFLINE);

        courierRepository.save(courier);

        log.info("Courier id:{} firstname:{} lastname:{} is added.",courier.getId(),courier.getFirstName(),courier.getLastName());
    }

    public void delete(Long id)
    {
        courierRepository.deleteById(id);

        log.info("Courier {} is deleted.",id);
    }

    public void update(UpdateCourierRequest courierRequest)
    {
        Courier courier = courierRepository.findById(courierRequest.id())
                .orElseThrow(() -> new RuntimeException("Courier not found with id: " + courierRequest.id()));
        courier.setUpdateDate(new Date());
        courier.setFirstName(courierRequest.firstName());
        courier.setLastName(courierRequest.lastName());
        courier.setPhone(courierRequest.phone());
        courier.setPlateNumber(courierRequest.plateNumber());
        courier.setIsWorking(courierRequest.isWorking());
        courier.setStatus(courier.getStatus());

        courierRepository.save(courier);

        log.info("Courier {} is updated",courier.getId());
    }

    public void updateCourierStatus(Long id, String status) {
        CourierStatus courierStatus;
        try {
            courierStatus = CourierStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found with id: " + id));
        courier.setStatus(courierStatus);
        courier.setUpdateDate(new Date());
        courierRepository.save(courier);

        log.info("Courier {} {} {} is {}   Date: {}", courier.getId(), courier.getFirstName(), courier.getLastName(), courierStatus,courier.getUpdateDate());
    }



    public CourierResponse getCourierById(Long id)
    {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found with id: " + id));
        return mapToCourierResponse(courier);
    }

    public List<CourierResponse> getAllCourier(Integer page,Integer size)
    {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("firstName").ascending());
        List<Courier> courierList = courierRepository.findAll(pageRequest).getContent();
        return courierList.stream().map(this::mapToCourierResponse).toList();
    }

    private CourierResponse mapToCourierResponse(Courier courier)
    {
        return new CourierResponse(courier.getId(),
                courier.getFirstName(),
                courier.getLastName(),
                courier.getPhone(),
                courier.getIdentityNumber(),
                courier.getBirthDate(),
                courier.getPlateNumber(),
                courier.getIsWorking());
    }

    public Courier getById(Long id)
    {
        return courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found with id: " + id));
    }

}
