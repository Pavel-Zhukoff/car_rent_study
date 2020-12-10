package ru.pavel_zhukoff.car_rent.service;

import org.springframework.data.domain.Sort;
import ru.pavel_zhukoff.car_rent.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    
    void deleteById(Long aLong);

    
    void delete(Client client);


    Client save(Client s);

    Client update(Client u);

    Optional<Client> findById(Long aLong);

    Optional<Client> findByDriverId(String driverId);

    Optional<Client> findByPhone(String phone);

    Optional<Client> findByPassport(String passport);

    boolean existsById(Long aLong);

    
    List<Client> findAll();

    
    List<Client> findAllActive();
}
