package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.model.CarColor;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarColorRepository extends JpaRepository<CarColor, Long> {
    @Override
    List<CarColor> findAll();

    @Override
    List<CarColor> findAll(Sort sort);

    List<CarColor> findAllByActiveTrue();

    @Override
    <S extends CarColor> S save(S s);

    @Override
    Optional<CarColor> findById(Long aLong);

    Optional<CarColor> findByName(String name);

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(CarColor carColor);
}
