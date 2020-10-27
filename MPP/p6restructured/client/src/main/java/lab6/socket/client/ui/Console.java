package lab6.socket.client.ui;

import javafx.util.Pair;
import lab6.socket.common.domain.Client;
import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.Rent;
import lab6.socket.common.service.SocketService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {
    private SocketService socketService;

    public Console(SocketService socketService) {
        this.socketService = socketService;
    }

    private void printMenu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Add client");
        System.out.println("2 - Add movie");
        System.out.println("3 - Rent movie");
        System.out.println("4 - Delete client");
        System.out.println("5 - Delete movie");
        System.out.println("6 - Return movie");
        System.out.println("7 - Update client");
        System.out.println("8 - Update movie");
        System.out.println("9 - Update rent");
        System.out.println("10 - See all clients");
        System.out.println("11 - See all movies");
        System.out.println("12 - See all rents");
    }

    public void runConsole() {
        label:
        while(true) {
            printMenu();
            System.out.println("\nGive command: \n");
            Scanner in = new Scanner(System.in);
            String option = in.nextLine();
            switch (option) {
                case "0":
                    break label;
                case "1": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    System.out.println("Give name: ");
                    String name = in.nextLine();

                    System.out.println("Give email: ");
                    String email = in.nextLine();

                    System.out.println("Give age: ");
                    int age = in.nextInt();

                    Client client = new Client(id, name, age, email);
                    Future<Optional<Client>> addFuture = socketService.addClient(client);
                    break;
                }
                case "2": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    System.out.println("Give title: ");
                    String title = in.nextLine();

                    System.out.println("Give rating: ");
                    double rating = in.nextDouble();

                    System.out.println("Give nrOfAvailableDVDs: ");
                    int dvds = in.nextInt();

                    Movie movie = new Movie(id, title, rating, dvds);
                    Future<Optional<Movie>> addFuture = socketService.addMovie(movie);
                    break;
                }
                case "3": {
                    System.out.println("Give client id: ");
                    String cidString = in.nextLine();
                    Long cid = Long.parseLong(cidString);

                    System.out.println("Give movie id: ");
                    String midString = in.nextLine();
                    Long mid = Long.parseLong(midString);

                    System.out.println("Give return date: ");
                    LocalDate date = LocalDate.parse(in.nextLine());

                    Rent rent = new Rent(cid, mid, date);
                    Future<Optional<Rent>> addFuture = socketService.rentMovie(rent);
                    break;
                }
                case "4": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    Future<Optional<Client>> deleteFuture = socketService.deleteClient(id);
                    break;
                }
                case "5": {
                    System.out.println("Give id: ");
                    Long id = in.nextLong();

                    Future<Optional<Movie>> deleteFuture = socketService.deleteMovie(id);
                    break;
                }
                case "6": {
                    System.out.println("Give client id: ");
                    String cidString = in.nextLine();
                    Long cid = Long.parseLong(cidString);

                    System.out.println("Give movie id: ");
                    String midString = in.nextLine();
                    Long mid = Long.parseLong(midString);

                    Pair<Long, Long> id = new Pair<>(cid, mid);
                    Future<Optional<Rent>> deleteFuture = socketService.deleteRent(id);
                    break;
                }
                case "7": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    System.out.println("Give name: ");
                    String name = in.nextLine();

                    System.out.println("Give email: ");
                    String email = in.nextLine();

                    System.out.println("Give age: ");
                    int age = in.nextInt();

                    Client client = new Client(id, name, age, email);
                    Future<Optional<Client>> addFuture = socketService.updateClient(client);
                    break;
                }
                case "8": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    System.out.println("Give title: ");
                    String title = in.nextLine();

                    System.out.println("Give rating: ");
                    double rating = in.nextDouble();

                    System.out.println("Give nrOfAvailableDVDs: ");
                    int dvds = in.nextInt();

                    Movie movie = new Movie(id, title, rating, dvds);
                    Future<Optional<Movie>> addFuture = socketService.updateMovie(movie);
                    break;
                }
                case "9": {
                    System.out.println("Give client id: ");
                    String cidString = in.nextLine();
                    Long cid = Long.parseLong(cidString);

                    System.out.println("Give movie id: ");
                    String midString = in.nextLine();
                    Long mid = Long.parseLong(midString);

                    System.out.println("Give return date: ");
                    LocalDate date = LocalDate.parse(in.nextLine());

                    Rent rent = new Rent(cid, mid, date);
                    Future<Optional<Rent>> addFuture = socketService.updateRent(rent);
                    break;
                }
                case "10":
                    Future<List<Client>> clients = socketService.getAllClients();
                    System.out.println("\n");
                    try {
                        clients.get().forEach(System.out::println);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
                case "11":
                    Future<List<Movie>> movies = socketService.getAllMovies();
                    System.out.println("\n");
                    try {
                        movies.get().forEach(System.out::println);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
                case "12":
                    Future<List<Rent>> rents = socketService.getAllRents();
                    System.out.println("\n");
                    try {
                        rents.get().forEach(System.out::println);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
