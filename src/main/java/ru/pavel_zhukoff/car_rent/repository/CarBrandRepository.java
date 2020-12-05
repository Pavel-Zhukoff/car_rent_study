package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarBrand;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    @Override
    List<CarBrand> findAll();

    @Override
    List<CarBrand> findAll(Sort sort);

    List<CarBrand> findAllByActiveTrue();

    @Override
    <S extends CarBrand> S save(S s);

    @Override
    Optional<CarBrand> findById(Long aLong);

    Optional<CarBrand> findByName(String name);

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(CarBrand carBrand);
}
