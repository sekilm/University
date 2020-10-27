package org.example.p9.core.controller;

import org.example.p9.core.domain.Client;
import org.example.p9.core.domain.Movie;
import org.example.p9.core.domain.Rent;
import org.example.p9.core.domain.validators.RentValidator;
import org.example.p9.core.domain.validators.ValidatorException;
import org.example.p9.core.repository.ClientRepo;
import org.example.p9.core.repository.MovieRepo;
import org.example.p9.core.repository.RentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentServiceImpl implements RentService {
    public static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private RentRepo rentRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private RentValidator rentValidator;

    @Override
    public List<Rent> getAllRents() {
        log.trace("getAllRents --- method entered");
        return rentRepo.findAll();
    }

    @Override
    public Rent rentMovie(Rent rent) throws ValidatorException {
        log.trace("addRent --- method entered: rent: rent={}", rent);
        rentValidator.validate(rent);
        rentRepo.save(rent);
        log.trace("addRent --- method finished");
        return rent;
    }

    @Override
    public void deleteRent(Long id) {
        log.trace("deleteRent --- method entered: id={}", id);
        rentRepo.deleteById(id);
        log.trace("deleteRent --- method finished");
    }

    @Override
    @Transactional
    public Rent updateRent(Rent rent) {
        log.trace("updateRent --- method entered: rent={}", rent);
        rentValidator.validate(rent);
        rentRepo.findById(rent.getId()).ifPresent(r -> {
            r.setReturnDate(rent.getReturnDate());
            log.debug("updateRent --- updated: rent={}", r);
        });
        return rent;
    }

    private long getRentsForOneMovie(Long id) {
        Iterable<Rent> rents = rentRepo.findAll();
        return StreamSupport.stream(rents.spliterator(), false).filter(m -> m.getMovieId().equals(id)).count();
    }

    public Optional<Movie> getMostRentedMovie() {
        Iterable<Movie> movies = movieRepo.findAll();
        return StreamSupport.stream(movies.spliterator(), false).max(Comparator.comparing(m -> getRentsForOneMovie(m.getId())));
    }

    public Set<Client> getClientsThatBoughtAMovie(Long id) {
        Iterable<Rent> rents = rentRepo.findAll();
        Iterable<Client> clients = clientRepo.findAll();
        List<Rent> filteredRents = StreamSupport.stream(rents.spliterator(), false).filter(rent -> rent.getMovieId().equals(id)).collect(Collectors.toList());

        Set<Client> filteredClients = new HashSet<>(Collections.emptySet());
        filteredRents.forEach(rent -> { clients.forEach(client -> { if(client.getId().equals(rent.getClientId())) filteredClients.add(client); }); });
        return filteredClients;
    }
}
