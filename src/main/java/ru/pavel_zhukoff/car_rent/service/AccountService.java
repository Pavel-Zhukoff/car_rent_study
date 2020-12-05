package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.pavel_zhukoff.car_rent.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService extends UserDetailsService {

    List<Account> findAll();

    List<Account> findAll(Sort sort);

    <S extends Account> S save(S s);

    Optional<Account> findById(Long aLong);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    boolean existsById(Long aLong);

    void deleteById(Long aLong);

    void delete(Account account);

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
