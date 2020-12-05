package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.CarColor;
import ru.pavel_zhukoff.car_rent.model.CarColor;

import java.util.List;
import java.util.Optional;

public interface CarColorService {
    
    List<CarColor> findAll();

    List<CarColor> findAllActive();

    CarColor save(CarColor s);

    CarColor update(CarColor u);

    Optional<CarColor> findByName(String name);
    
    Optional<CarColor> findById(Long aLong);

    boolean existsById(Long aLong);

    void deleteById(Long aLong);

    void delete(CarColor carColor);
}
