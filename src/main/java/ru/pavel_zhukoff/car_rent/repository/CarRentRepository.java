package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarRent;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRentRepository extends JpaRepository<CarRent, Long> {
    @Override
    void delete(CarRent carRent);

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends CarRent> S save(S s);

    @Override
    Optional<CarRent> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<CarRent> findAll();

    List<CarRent> findAllByActiveTrue();

    List<CarRent> findAllByCar(Car car);
}
