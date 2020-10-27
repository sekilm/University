package lab6.socket.server;

import javafx.util.Pair;
import lab6.socket.common.Message;
import lab6.socket.common.SerializableHelper;
import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.Rent;
import lab6.socket.common.domain.validators.ClientValidator;
import lab6.socket.common.domain.validators.MovieValidator;
import lab6.socket.common.domain.validators.RentValidator;
import lab6.socket.common.service.SocketService;
import lab6.socket.server.DBRepo.ClientDBRepo;
import lab6.socket.server.DBRepo.MovieDBRepo;
import lab6.socket.server.DBRepo.RentDBRepo;
import lab6.socket.server.DBRepo.Repo;
import lab6.socket.server.controller.ClientController;
import lab6.socket.server.controller.MovieController;
import lab6.socket.server.controller.RentController;
import lab6.socket.server.controller.SocketServiceImpl;
import lab6.socket.server.tcp.TcpServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        try {
            System.out.println("server started");
            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );
            ClientValidator clientValidator = new ClientValidator();
            MovieValidator movieValidator = new MovieValidator();
            RentValidator rentValidator = new RentValidator();

            Repo<Long, Client> clientRepo = new ClientDBRepo(clientValidator);
            Repo<Long, Movie> movieRepo = new MovieDBRepo(movieValidator);
            Repo<Pair<Long, Long>, Rent> rentRepo = new RentDBRepo(rentValidator);

            ClientController clientController = new ClientController(clientRepo);
            MovieController movieController = new MovieController(movieRepo);
            RentController rentController = new RentController(rentRepo, clientRepo, movieRepo);

            SocketService socketService = new SocketServiceImpl(executorService, clientController, movieController, rentController);

            TcpServer tcpServer = new TcpServer(executorService);

            tcpServer.addHandler(SocketService.ADD_CLIENT, (request) -> {
                Optional<Serializable> clientObj = SerializableHelper.convertFrom(request.getBody());
                Client client = (Client) clientObj.get();
                try {
                    Future<Optional<Client>> future = socketService.addClient(client);
                    Client result = future.get().get();
                    return new Message("added ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.ADD_MOVIE, (request) -> {
                Optional<Serializable> movieObj = SerializableHelper.convertFrom(request.getBody());
                Movie movie = (Movie) movieObj.get();
                try {
                    Future<Optional<Movie>> future = socketService.addMovie(movie);
                    Movie result = future.get().get();
                    return new Message("added ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.ADD_RENT, (request) -> {
                Optional<Serializable> rentObj = SerializableHelper.convertFrom(request.getBody());
                Rent rent = (Rent) rentObj.get();
                try {
                    Future<Optional<Rent>> future = socketService.rentMovie(rent);
                    Rent result = future.get().get();
                    return new Message("added ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.DELETE_CLIENT, (request) -> {
                Long id = Long.parseLong(request.getBody());
                try {
                    Future<Optional<Client>> future = socketService.deleteClient(id);
                    Client result = future.get().get();
                    return new Message("deleted ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.DELETE_MOVIE, (request) -> {
                Long id = Long.parseLong(request.getBody());
                try {
                    Future<Optional<Movie>> future = socketService.deleteMovie(id);
                    Movie result = future.get().get();
                    return new Message("deleted ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.DELETE_RENT, (request) -> {
                Pair<Long, Long> id = (Pair<Long, Long>) SerializableHelper.convertFrom(request.getBody()).get();
                try {
                    Future<Optional<Rent>> future = socketService.deleteRent(id);
                    Rent result = future.get().get();
                    return new Message("deleted ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.UPDATE_CLIENT, (request) -> {
                Optional<Serializable> clientObj = SerializableHelper.convertFrom(request.getBody());
                Client client = (Client) clientObj.get();
                try {
                    Future<Optional<Client>> future = socketService.updateClient(client);
                    Client result = future.get().get();
                    return new Message("updated ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.UPDATE_MOVIE, (request) -> {
                Optional<Serializable> movieObj = SerializableHelper.convertFrom(request.getBody());
                Movie movie = (Movie) movieObj.get();
                try {
                    Future<Optional<Movie>> future = socketService.updateMovie(movie);
                    Movie result = future.get().get();
                    return new Message("updated  ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.UPDATE_RENT, (request) -> {
                Optional<Serializable> rentObj = SerializableHelper.convertFrom(request.getBody());
                Rent rent = (Rent) rentObj.get();
                try {
                    Future<Optional<Rent>> future = socketService.updateRent(rent);
                    Rent result = future.get().get();
                    return new Message("updated ", SerializableHelper.convertToString(result).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.PRINT_CLIENTS, (request) -> {
                try {
                    Future<List<Client>> future = socketService.getAllClients();
                    ArrayList<Client> clients = (ArrayList<Client>) future.get();

                    Optional<String> c = SerializableHelper.convertToString(clients);

                    return new Message("clients: ", c.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.PRINT_MOVIES, (request) -> {
                try {
                    Future<List<Movie>> future = socketService.getAllMovies();
                    ArrayList<Movie> movies = (ArrayList<Movie>) future.get();

                    Optional<String> m = SerializableHelper.convertToString(movies);

                    return new Message("movies: ", m.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.addHandler(SocketService.PRINT_RENTS, (request) -> {
                try {
                    Future<List<Rent>> future = socketService.getAllRents();
                    ArrayList<Rent> rents = (ArrayList<Rent>) future.get();

                    Optional<String> r = SerializableHelper.convertToString(rents);

                    return new Message("rents: ", r.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }
            });

            tcpServer.startServer();

            executorService.shutdown();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }
}
