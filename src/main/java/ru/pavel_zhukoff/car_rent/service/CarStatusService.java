package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.CarStatus;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CarStatusService {

    
    List<CarStatus> findAll();

    
    List<CarStatus> findAllActive();
    
    CarStatus save(CarStatus s);

    CarStatus update(CarStatus u);

    Optional<CarStatus> findById(Long aLong);

    Optional<CarStatus> findByName(String name);
    
    boolean existsById(Long aLong);

    
    void deleteById(Long aLong);

    
    void delete(CarStatus carStatus);
}
