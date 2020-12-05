package ru.pavel_zhukoff.car_rent.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarRent;
import ru.pavel_zhukoff.car_rent.repository.CarRentRepository;
import ru.pavel_zhukoff.car_rent.service.CarRentService;

import java.util.List;
import java.util.Optional;

@Service
public class CarRentServiceImpl implements CarRentService {

    private final CarRentRepository repository;

    public CarRentServiceImpl(CarRentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(CarRent carRent) {
        repository.delete(carRent);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public <S extends CarRent> S save(S s) {
        return repository.save(s);
    }

    @Override
    public Optional<CarRent> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public List<CarRent> findAll() {
        return repository.findAll();
    }

    @Override
    public List<CarRent> findAll(Sort sort) {
        return repository.findAll(sort);
    }
}
