package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarColor;
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
    public CarModel save(CarModel s) {
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
    public CarModel update(CarModel u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "CarModel");
        }
        return this.save(u);
    }

    @Override
    public Optional<CarModel> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<CarModel> findAllActive() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public void deleteById(Long aLong) {
        if (this.existsById(aLong)) {
            this._delete(this.findById(aLong).get());
        } else {
            throw new ObjectNotFoundException(aLong, "CarModel");
        }
    }

    @Override
    public void delete(CarModel carModel) {
        if (this.existsById(carModel.getId())) {
            this._delete(carModel);
        } else {
            throw new ObjectNotFoundException(carModel.getId(), "CarModel");
        }
    }

    private void _delete(CarModel carModel) {
        carModel.setActive(false);
        this.save(carModel);
    }
}
