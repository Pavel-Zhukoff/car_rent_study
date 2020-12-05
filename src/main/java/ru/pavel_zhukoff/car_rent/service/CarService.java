package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    
    void deleteById(Long aLong);

    
    void delete(Car car);

    
    <S extends Car> S save(S s);

    
    Optional<Car> findById(Long aLong);

    
    boolean existsById(Long aLong);

    
    List<Car> findAll();

    
    List<Car> findAll(Sort sort);
    
}

