package org.example.p9.core.controller;

import org.example.p9.core.domain.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    Client addClient(Client client);
    void deleteClient(Long id);
    Client updateClient(Client client);
}
