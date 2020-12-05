package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.model.CarColor;
import ru.pavel_zhukoff.car_rent.repository.CarColorRepository;
import ru.pavel_zhukoff.car_rent.service.CarColorService;

import java.util.List;
import java.util.Optional;

@Service
public class CarColorServiceImpl implements CarColorService {

    private final CarColorRepository repository;

    public CarColorServiceImpl(CarColorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarColor> findAll() {
        return repository.findAll();
    }

    public List<CarColor> findAllActive() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public CarColor save(CarColor s) {
        return repository.save(s);
    }

    @Override
    public CarColor update(CarColor u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "CarColor");
        }
        return this.save(u);
    }

    @Override
    public Optional<CarColor> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public Optional<CarColor> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        if (this.existsById(aLong)) {
            this._delete(this.findById(aLong).get());
        } else {
            throw new ObjectNotFoundException(aLong, "CarColor");
        }
    }

    @Override
    public void delete(CarColor carColor) {
        if (this.existsById(carColor.getId())) {
            this._delete(carColor);
        } else {
            throw new ObjectNotFoundException(carColor.getId(), "CarColor");
        }
    }

    private void _delete(CarColor carColor) {
        carColor.setActive(false);
        this.save(carColor);
    }
}
