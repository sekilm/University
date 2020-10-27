package org.example.p9.core.controller;

import org.example.p9.core.repository.ClientRepo;
import org.example.p9.core.domain.Client;
import org.example.p9.core.domain.validators.ClientValidator;
import org.example.p9.core.domain.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService {
    public static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ClientValidator clientValidator;

    @Override
    public List<Client> getAllClients() {
        log.trace("getAllClients --- method entered");
        return clientRepo.findAll();
    }

    @Override
    public Client addClient(Client client) throws ValidatorException {
        log.trace("addClient --- method entered: client: client={}", client);
        clientValidator.validate(client);
        clientRepo.save(client);
        log.trace("addClient --- method finished");
        return client;
    }

    public void deleteClient(Long id) {
        log.trace("deleteClient --- method entered: id={}", id);
        clientRepo.deleteById(id);
        log.trace("deleteClient --- method finished");
    }

    @Override
    @Transactional
    public Client updateClient(Client client) {
        log.trace("updateClient --- method entered: client={}", client);
        clientValidator.validate(client);
        clientRepo.findById(client.getId()).ifPresent(c -> {
            c.setName(client.getName());
            c.setAge(client.getAge());
            c.setEmail(client.getEmail());
            log.debug("updateClient --- updated: client={}", c);
        });
        return client;
    }

    public Set<Client> filterClientsByName(String name) {
        Iterable<Client> clients = clientRepo.findAll();
        Set<Client> filteredClients =
                StreamSupport.stream(clients.spliterator(), false).filter(client -> client.getName().equals(name)).collect(Collectors.toSet());
        return filteredClients;
    }
}
