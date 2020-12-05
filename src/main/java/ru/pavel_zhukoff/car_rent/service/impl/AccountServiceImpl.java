package ru.pavel_zhukoff.car_rent.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.Account;
import ru.pavel_zhukoff.car_rent.repository.AccountRepository;
import ru.pavel_zhukoff.car_rent.service.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final PasswordEncoder passwordEncoder;


    public AccountServiceImpl(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Account> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public <S extends Account> S save(S s) {
        Optional<Account> account = repository.findByUsername(s.getUsername());
        if (account.isPresent()) return null;
        s.setPassword(passwordEncoder.encode(s.getPassword()));
        return repository.save(s);
    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<Account> findByUsernameAndPassword(String username, String password) {
        password = passwordEncoder.encode(password);
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.findById(aLong).ifPresent(e -> repository.deleteById(aLong));
    }

    @Override
    public void delete(Account account) {
        repository.delete(account);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

}
