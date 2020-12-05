package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel_zhukoff.car_rent.model.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Client client);

    @Override
    <S extends Client> S save(S s);

    @Override
    Optional<Client> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<Client> findAll();

    @Override
    List<Client> findAll(Sort sort);
}
