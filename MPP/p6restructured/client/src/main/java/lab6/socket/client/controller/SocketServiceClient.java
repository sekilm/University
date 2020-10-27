package lab6.socket.client.controller;

import javafx.util.Pair;
import lab6.socket.client.tcp.TcpClient;
import lab6.socket.common.Message;
import lab6.socket.common.SerializableHelper;
import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.Rent;
import lab6.socket.common.service.SocketService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SocketServiceClient implements SocketService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public SocketServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<Optional<Client>> addClient(Client client) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.ADD_CLIENT, SerializableHelper.convertToString(client).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Movie>> addMovie(Movie movie) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.ADD_MOVIE, SerializableHelper.convertToString(movie).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Rent>> rentMovie(Rent rent) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.ADD_RENT, SerializableHelper.convertToString(rent).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Client>> deleteClient(Long id) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.DELETE_CLIENT, Long.toString(id));
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Movie>> deleteMovie(Long id) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.DELETE_MOVIE, Long.toString(id));
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Rent>> deleteRent(Pair<Long, Long> id) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.DELETE_RENT, SerializableHelper.convertToString(id).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Client>> updateClient(Client client) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.UPDATE_CLIENT, SerializableHelper.convertToString(client).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Movie>> updateMovie(Movie movie) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.UPDATE_MOVIE, SerializableHelper.convertToString(movie).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<Optional<Rent>> updateRent(Rent rent) {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.UPDATE_CLIENT, SerializableHelper.convertToString(rent).get());
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return SerializableHelper.convertFrom(response.getBody());
        });
    }

    @Override
    public Future<List<Client>> getAllClients() {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.PRINT_CLIENTS, "");
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return (List<Client>)SerializableHelper.convertFrom(response.getBody()).get();
        });
    }

    @Override
    public Future<List<Movie>> getAllMovies() {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.PRINT_MOVIES, "");
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return (List<Movie>)SerializableHelper.convertFrom(response.getBody()).get();
        });
    }

    @Override
    public Future<List<Rent>> getAllRents() {
        return executorService.submit(() -> {
            Message request = new Message(SocketService.PRINT_RENTS, "");
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return (List<Rent>)SerializableHelper.convertFrom(response.getBody()).get();
        });
    }
}
