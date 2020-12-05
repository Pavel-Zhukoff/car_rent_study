package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.CarColor;
import ru.pavel_zhukoff.car_rent.model.CarStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
    @Override
    List<CarStatus> findAll();

    @Override
    List<CarStatus> findAll(Sort sort);

    List<CarStatus> findAllByActiveTrue();

    @Override
    <S extends CarStatus> S save(S s);

    @Override
    Optional<CarStatus> findById(Long aLong);

    Optional<CarStatus> findByName(String name);

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(CarStatus carStatus);
}
