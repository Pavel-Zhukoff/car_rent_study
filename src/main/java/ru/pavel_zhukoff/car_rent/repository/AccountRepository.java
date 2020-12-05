package ru.pavel_zhukoff.car_rent.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pavel_zhukoff.car_rent.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Override
    List<Account> findAll();

    @Override
    List<Account> findAll(Sort sort);

    @Override
    <S extends Account> S save(S s);

    @Override
    Optional<Account> findById(Long aLong);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Account account);

}
