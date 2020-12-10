package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Car car);

    @Override
    <S extends Car> S save(S s);

    @Override
    Optional<Car> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<Car> findAll();

    List<Car> findAllByActiveTrue();
}
