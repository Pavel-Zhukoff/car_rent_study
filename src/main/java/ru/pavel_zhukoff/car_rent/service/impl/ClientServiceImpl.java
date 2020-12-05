package ru.pavel_zhukoff.car_rent.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.repository.CarRepository;
import ru.pavel_zhukoff.car_rent.service.CarService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements CarService {

    private final CarRepository repository;

    public ClientServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public void delete(Car car) {
        repository.delete(car);
    }

    @Override
    public <S extends Car> S save(S s) {
        return repository.save(s);
    }

    @Override
    public Optional<Car> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Car> findAll(Sort sort) {
        return repository.findAll(sort);
    }
}
