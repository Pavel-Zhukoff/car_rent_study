package ru.pavel_zhukoff.car_rent.service;

import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarRent;

import java.util.List;
import java.util.Optional;

public interface CarRentService {

    void delete(CarRent carRent);

    void deleteById(Long aLong);

    CarRent save(CarRent s);

    CarRent update(CarRent u);
    
    Optional<CarRent> findById(Long aLong);
    
    boolean existsById(Long aLong);
    
    List<CarRent> findAll();

    List<CarRent> findAllActive();

    List<CarRent> findAllByCar(Car car);
}
