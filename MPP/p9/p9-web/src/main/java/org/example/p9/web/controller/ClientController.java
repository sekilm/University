package org.example.p9.web.controller;

import org.example.p9.core.controller.ClientService;
import org.example.p9.web.dto.ClientDto;
import org.example.p9.web.dto.ClientsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.p9.web.converter.ClientConverter;

@RestController
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDto getClients() {
        log.trace("getClients --- method entered");
        return new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.getAllClients()));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) {
        log.trace("addClient --- method entered: client: client={}", clientDto);
        return clientConverter.convertModelToDto(clientService.addClient(
                clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,
                             @RequestBody ClientDto clientDto) {
        log.trace("updateClient --- method entered: client={}", clientDto);
        return clientConverter.convertModelToDto(clientService.updateClient(
                clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("deleteClient --- method entered: id={}", id);
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
