package lab6.socket.server.controller;

import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.validators.ValidatorException;
import lab6.socket.server.DBRepo.Repo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientController {
    private Repo<Long, Client> clientRepo;

    public ClientController(Repo<Long, Client> repository) { clientRepo = repository; }

    public List<Client> getAllClients() {
        Iterable<Client> clients = clientRepo.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Client> addClient(Client client) throws ValidatorException { return clientRepo.add(client); }

    public Optional<Client> deleteClient(Long id) { return clientRepo.delete(id); }

    public Optional<Client> updateClient(Client client) { return clientRepo.update(client); }

    public List<Client> filterClientsByName(String name) {
        Iterable<Client> clients = clientRepo.findAll();
        List<Client> filteredClients =
                StreamSupport.stream(clients.spliterator(), false).filter(client -> client.getName().equals(name)).collect(Collectors.toList());
        return filteredClients;
    }
}
