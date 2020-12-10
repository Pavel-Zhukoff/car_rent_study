package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarModel;
import ru.pavel_zhukoff.car_rent.repository.CarRepository;
import ru.pavel_zhukoff.car_rent.service.CarService;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository repository;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Car save(Car s) {
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
    public List<Car> findAllActive() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public Car update(Car u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "Car");
        }
        return this.save(u);
    }

    @Override
    public void deleteById(Long aLong) {
        if (this.existsById(aLong)) {
            this._delete(this.findById(aLong).get());
        } else {
            throw new ObjectNotFoundException(aLong, "Car");
        }
    }

    @Override
    public void delete(Car carModel) {
        if (this.existsById(carModel.getId())) {
            this._delete(carModel);
        } else {
            throw new ObjectNotFoundException(carModel.getId(), "Car");
        }
    }

    private void _delete(Car car) {
        car.setActive(false);
        this.save(car);
    }
}
