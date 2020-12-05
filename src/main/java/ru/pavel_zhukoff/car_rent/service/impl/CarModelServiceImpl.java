package ru.pavel_zhukoff.car_rent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarModel;
import ru.pavel_zhukoff.car_rent.repository.CarModelRepository;
import ru.pavel_zhukoff.car_rent.service.CarModelService;

import java.util.List;
import java.util.Optional;

@Service
public class CarModelServiceImpl implements CarModelService {

    @Autowired
    private CarModelRepository repository;

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public void delete(CarModel carModel) {
        repository.delete(carModel);
    }

    @Override
    public <S extends CarModel> S save(S s) {
        return repository.save(s);
    }

    @Override
    public Optional<CarModel> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public List<CarModel> findAll() {
        return repository.findAll();
    }

    @Override
    public List<CarModel> findAll(Sort sort) {
        return repository.findAll(sort);
    }
}
