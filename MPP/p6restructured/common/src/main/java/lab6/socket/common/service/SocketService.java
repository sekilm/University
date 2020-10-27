package lab6.socket.common.service;

import javafx.util.Pair;
import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.Rent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public interface SocketService {
    String ADD_CLIENT = "addClient";
    String ADD_MOVIE = "addMovie";
    String ADD_RENT = "rentMovie";
    String DELETE_CLIENT = "deleteClient";
    String DELETE_MOVIE = "deleteMovie";
    String DELETE_RENT = "deleteRent";
    String UPDATE_CLIENT = "updateClient";
    String UPDATE_MOVIE = "updateMovie";
    String UPDATE_RENT = "updateRent";
    String PRINT_CLIENTS = "getAllClients";
    String PRINT_MOVIES = "getAllMovies";
    String PRINT_RENTS = "getAllRents";

    Future<Optional<Client>> addClient(Client client);
    Future<Optional<Movie>> addMovie(Movie movie);
    Future<Optional<Rent>> rentMovie(Rent rent);
    Future<Optional<Client>> deleteClient(Long id);
    Future<Optional<Movie>> deleteMovie(Long id);
    Future<Optional<Rent>> deleteRent(Pair<Long, Long> id);
    Future<Optional<Client>> updateClient(Client client);
    Future<Optional<Movie>> updateMovie(Movie movie);
    Future<Optional<Rent>> updateRent(Rent rent);
    Future<List<Client>> getAllClients();
    Future<List<Movie>> getAllMovies();
    Future<List<Rent>> getAllRents();
}
