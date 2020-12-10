package ru.pavel_zhukoff.car_rent.service.impl;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pavel_zhukoff.car_rent.model.CarStatus;
import ru.pavel_zhukoff.car_rent.model.Client;
import ru.pavel_zhukoff.car_rent.repository.ClientRepository;
import ru.pavel_zhukoff.car_rent.service.ClientService;
import ru.pavel_zhukoff.car_rent.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client s) {
        return repository.save(s);
    }

    @Override
    public Optional<Client> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public Client update(Client u) {
        if (!this.repository.existsById(u.getId())) {
            throw new ObjectNotFoundException(u.getId(), "Client");
        }
        return this.save(u);
    }

    @Override
    public Optional<Client> findByDriverId(String driverId) {
        return repository.findByDriverId(driverId);
    }

    @Override
    public Optional<Client> findByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public Optional<Client> findByPassport(String passport) {
        return repository.findByPassport(passport);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    public List<Client> findAllActive() {
        return repository.findAllByActiveTrue();
    }


    @Override
    public void deleteById(Long aLong) {
        if (this.existsById(aLong)) {
            this._delete(this.findById(aLong).get());
        } else {
            throw new ObjectNotFoundException(aLong, "Client");
        }
    }

    @Override
    public void delete(Client client) {
        if (this.existsById(client.getId())) {
            this._delete(client);
        } else {
            throw new ObjectNotFoundException(client.getId(), "Client");
        }
    }

    private void _delete(Client client) {
        client.setActive(false);
        this.save(client);
    }
}
