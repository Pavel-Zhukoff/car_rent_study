package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelService {

    
    void deleteById(Long aLong);

    
    void delete(CarModel carModel);

    
    <S extends CarModel> S save(S s);

    
    Optional<CarModel> findById(Long aLong);

    
    boolean existsById(Long aLong);

    
    List<CarModel> findAll();

    
    List<CarModel> findAll(Sort sort);
}
