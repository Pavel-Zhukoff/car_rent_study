package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    
    void deleteById(Long aLong);

    
    void delete(Client client);

    
    <S extends Client> S save(S s);

    
    Optional<Client> findById(Long aLong);

    
    boolean existsById(Long aLong);

    
    List<Client> findAll();

    
    List<Client> findAll(Sort sort);
}
