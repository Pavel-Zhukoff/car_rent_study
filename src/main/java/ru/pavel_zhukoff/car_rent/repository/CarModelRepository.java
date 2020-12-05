package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.CarModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    @Override
    void deleteById(Long aLong);

    @Override
    void delete(CarModel carModel);

    @Override
    <S extends CarModel> S save(S s);

    @Override
    Optional<CarModel> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<CarModel> findAll();

    @Override
    List<CarModel> findAll(Sort sort);
}
