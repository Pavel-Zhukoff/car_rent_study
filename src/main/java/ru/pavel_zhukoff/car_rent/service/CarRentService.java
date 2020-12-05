package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.CarRent;

import java.util.List;
import java.util.Optional;

public interface CarRentService {

    
    void delete(CarRent carRent);

    void deleteById(Long aLong);
    
    <S extends CarRent> S save(S s);

    
    Optional<CarRent> findById(Long aLong);

    
    boolean existsById(Long aLong);

    
    List<CarRent> findAll();

    
    List<CarRent> findAll(Sort sort);
}
