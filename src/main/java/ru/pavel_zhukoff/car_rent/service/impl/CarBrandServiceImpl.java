package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.repository.CarBrandRepository;
import ru.pavel_zhukoff.car_rent.service.CarBrandService;

import java.util.List;
import java.util.Optional;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository repository;

    public CarBrandServiceImpl(CarBrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarBrand> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<CarBrand> findAllActive() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public CarBrand save(CarBrand s) {
        return this.repository.save(s);
    }

    @Override
    public CarBrand update(CarBrand u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "CarBrand");
        }
        return this.save(u);
    }

    @Override
    public Optional<CarBrand> findById(Long aLong) {
        return this.repository.findById(aLong);
    }

    @Override
    public Optional<CarBrand> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public boolean existsById(Long aLong) {
        return this.repository.existsById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        if (this.existsById(aLong)) {
            this._delete(this.findById(aLong).get());
        } else {
            throw new ObjectNotFoundException(aLong, "CarBrand");
        }
    }

    @Override
    public void delete(CarBrand carBrand) {
        if (this.existsById(carBrand.getId())) {
            this._delete(carBrand);
        } else {
            throw new ObjectNotFoundException(carBrand.getId(), "CarBrand");
        }
    }

    private void _delete(CarBrand carBrand) {
        carBrand.setActive(false);
        this.save(carBrand);
    }
}

