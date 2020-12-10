package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelService {

    
    void deleteById(Long aLong);

    
    void delete(CarModel carModel);


    CarModel save(CarModel s);

    CarModel update(CarModel u);
    
    Optional<CarModel> findById(Long aLong);

    Optional<CarModel> findByName(String name);

    boolean existsById(Long aLong);

    
    List<CarModel> findAll();

    
    List<CarModel> findAllActive();
}
