package lab6.socket.server.controller;

import javafx.util.Pair;
import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.Rent;
import lab6.socket.common.service.SocketService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SocketServiceImpl implements SocketService {
    private ExecutorService executorService;
    private ClientController clientController;
    private MovieController movieController;
    private RentController rentController;

    public SocketServiceImpl(ExecutorService executorService, ClientController clientController,
                             MovieController movieController, RentController rentController) {
        this.executorService = executorService;
        this.clientController = clientController;
        this.movieController = movieController;
        this.rentController = rentController;
    }

    @Override
    public Future<Optional<Client>> addClient(Client client) {
        return executorService.submit(() -> clientController.addClient(client));
    }

    @Override
    public Future<Optional<Movie>> addMovie(Movie movie) {
        return executorService.submit(() -> movieController.addMovie(movie));
    }

    @Override
    public Future<Optional<Rent>> rentMovie(Rent rent) {
        return executorService.submit(() -> rentController.rentMovie(rent));
    }

    @Override
    public Future<Optional<Client>> deleteClient(Long id) {
        return executorService.submit(() -> clientController.deleteClient(id));
    }

    @Override
    public Future<Optional<Movie>> deleteMovie(Long id) {
        return executorService.submit(() -> movieController.deleteMovie(id));
    }

    @Override
    public Future<Optional<Rent>> deleteRent(Pair<Long, Long> id) {
        return executorService.submit(() -> rentController.deleteRent(id));
    }

    @Override
    public Future<Optional<Client>> updateClient(Client client) {
        return executorService.submit(() -> clientController.updateClient(client));
    }

    @Override
    public Future<Optional<Movie>> updateMovie(Movie movie) {
        return executorService.submit(() -> movieController.updateMovie(movie));
    }

    @Override
    public Future<Optional<Rent>> updateRent(Rent rent) {
        return executorService.submit(() -> rentController.updateRent(rent));
    }

    @Override
    public Future<List<Client>> getAllClients() {
        return executorService.submit(() -> clientController.getAllClients());
    }

    @Override
    public Future<List<Movie>> getAllMovies() {
        return executorService.submit(() -> movieController.getAllMovies());
    }

    @Override
    public Future<List<Rent>> getAllRents() {
        return executorService.submit(() -> rentController.getAllRents());
    }
}
