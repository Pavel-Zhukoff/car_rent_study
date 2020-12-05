package ru.pavel_zhukoff.car_rent.service;


import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.CarBrand;

import java.util.List;
import java.util.Optional;

public interface CarBrandService {
    
    List<CarBrand> findAll();

    List<CarBrand> findAllActive();

    CarBrand save(CarBrand s);

    CarBrand update(CarBrand u);

    Optional<CarBrand> findById(Long aLong);

    Optional<CarBrand> findByName(String name);

    boolean existsById(Long aLong);
    
    void deleteById(Long aLong);
    
    void delete(CarBrand carBrand);
}
