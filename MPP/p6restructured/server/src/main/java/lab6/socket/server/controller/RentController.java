package lab6.socket.server.controller;

import javafx.util.Pair;
import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.Rent;
import lab6.socket.common.domain.validators.MyException;
import lab6.socket.server.DBRepo.Repo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentController {
    private Repo<Pair<Long, Long>, Rent> rentRepo;
    private Repo<Long, Client> clientRepo;
    private Repo<Long, Movie> movieRepo;

    public RentController(Repo<Pair<Long, Long>, Rent> repo, Repo<Long, Client> _clientRepo, Repo<Long, Movie> _movieRepo) {
        rentRepo = repo;
        clientRepo = _clientRepo;
        movieRepo = _movieRepo;
    }

    public Optional<Rent> rentMovie(Rent rent) throws MyException {
        clientRepo.find(rent.getId().getKey()).orElseThrow(() -> new MyException("Client ID doesn't exist"));
        movieRepo.find(rent.getId().getValue()).orElseThrow(() -> new MyException("Movie ID doesn't exist"));
        return rentRepo.add(rent);
    }

    public Optional<Rent> deleteRent(Pair<Long, Long> id) {
        return rentRepo.delete(id);
    }

    public Optional<Rent> updateRent(Rent rent) throws MyException {
        return rentRepo.update(rent);
    }

    public List<Rent> getAllRents() {
        Iterable<Rent> rents = rentRepo.findAll();
        return StreamSupport.stream(rents.spliterator(), false).collect(Collectors.toList());
    }

    private long getRentsForOneMovie(Long id) {
        Iterable<Rent> rents = rentRepo.findAll();
        return StreamSupport.stream(rents.spliterator(), false).filter(m -> m.getId().getValue().equals(id)).count();
    }

    public Optional<Movie> getMostRentedMovie() {
        Iterable<Movie> movies = movieRepo.findAll();
        return StreamSupport.stream(movies.spliterator(), false).max(Comparator.comparing(m -> getRentsForOneMovie(m.getId())));
    }

    public Set<Client> getClientsThatBoughtAMovie(Long id) {
        Iterable<Rent> rents = rentRepo.findAll();
        Iterable<Client> clients = clientRepo.findAll();
        List<Rent> filteredRents = StreamSupport.stream(rents.spliterator(), false).filter(rent -> rent.getId().getValue().equals(id)).collect(Collectors.toList());

        Set<Client> filteredClients = new HashSet<>(Collections.emptySet());
        filteredRents.forEach(rent -> { clients.forEach(client -> { if(client.getId().equals(rent.getId().getKey())) filteredClients.add(client); }); });
        return filteredClients;
    }
}
