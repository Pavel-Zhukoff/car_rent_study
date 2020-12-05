package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarColor;
import ru.pavel_zhukoff.car_rent.model.CarStatus;
import ru.pavel_zhukoff.car_rent.repository.CarStatusRepository;
import ru.pavel_zhukoff.car_rent.service.CarStatusService;

import java.util.List;
import java.util.Optional;

@Service
public class CarStatusServiceImpl implements CarStatusService {

    private final CarStatusRepository repository;

    public CarStatusServiceImpl(CarStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarStatus> findAll() {
        return repository.findAll();
    }

    @Override
    public CarStatus update(CarStatus u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "CarColor");
        }
        return this.save(u);
    }

    @Override
    public Optional<CarStatus> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<CarStatus> findAllActive() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public CarStatus save(CarStatus s) {
        return repository.save(s);
    }

    @Override
    public Optional<CarStatus> findById(Long aLong) {
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
    public void delete(CarStatus carStatus) {
        if (this.existsById(carStatus.getId())) {
            this._delete(carStatus);
        } else {
            throw new ObjectNotFoundException(carStatus.getId(), "CarColor");
        }
    }

    private void _delete(CarStatus carStatus) {
        carStatus.setActive(false);
        this.save(carStatus);
    }
}
