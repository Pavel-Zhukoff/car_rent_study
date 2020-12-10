package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarModel;
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
    public CarRent save(CarRent s) {
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
    public List<CarRent> findAllActive() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public List<CarRent> findAllByCar(Car car) {
        return repository.findAllByCar(car);
    }

    @Override
    public CarRent update(CarRent u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "CarRent");
        }
        return this.save(u);
    }

    @Override
    public void deleteById(Long aLong) {
        if (this.existsById(aLong)) {
            this._delete(this.findById(aLong).get());
        } else {
            throw new ObjectNotFoundException(aLong, "CarRent");
        }
    }

    @Override
    public void delete(CarRent carRent) {
        if (this.existsById(carRent.getId())) {
            this._delete(carRent);
        } else {
            throw new ObjectNotFoundException(carRent.getId(), "CarRent");
        }
    }

    private void _delete(CarRent carRent) {
        carRent.setActive(false);
        this.save(carRent);
    }
}
