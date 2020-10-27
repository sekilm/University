package org.example.p9.client.ui;

import org.example.p9.core.domain.Client;
import org.example.p9.core.domain.Movie;
import org.example.p9.core.domain.Rent;
import org.example.p9.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.example.p9.web.converter.ClientConverter;
import org.example.p9.web.converter.MovieConverter;
import org.example.p9.web.converter.RentConverter;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Console {
    public static final String URLClients = "http://localhost:8080/api/clients";
    public static final String URLMovies = "http://localhost:8080/api/movies";
    public static final String URLRents = "http://localhost:8080/api/rents";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ClientConverter clientConverter;

    @Autowired
    MovieConverter movieConverter;

    @Autowired
    RentConverter rentConverter;

    private void printMenu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Add client");
        System.out.println("2 - Add rent");
        System.out.println("3 - Rent movie");
        System.out.println("4 - Delete client");
        System.out.println("5 - Delete rent");
        System.out.println("6 - Return rent");
        System.out.println("7 - Update client");
        System.out.println("8 - Update rent");
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
                    System.out.println("Give name: ");
                    String name = in.nextLine();

                    System.out.println("Give email: ");
                    String email = in.nextLine();

                    System.out.println("Give age: ");
                    int age = in.nextInt();

                    try {
                        ClientDto savedClient = restTemplate.postForObject(
                                URLClients,
                                new ClientDto(name, email, age),
                                ClientDto.class);
                        System.out.println("Saved client: " + savedClient);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not add client\n");
                    }
                    break;
                }
                case "2": {
                    System.out.println("Give title: ");
                    String title = in.nextLine();

                    System.out.println("Give rating: ");
                    double rating = in.nextDouble();

                    System.out.println("Give nrOfAvailableDVDs: ");
                    int dvds = in.nextInt();

                    try {
                        MovieDto savedMovie = restTemplate.postForObject(
                                URLMovies,
                                new MovieDto(title, rating ,dvds),
                                MovieDto.class);
                        System.out.println("Saved movie: " + savedMovie);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not add movie\n");
                    }
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
                    String date = in.nextLine();

                    try {
                        RentDto savedRent = restTemplate.postForObject(
                                URLRents,
                                new RentDto(cid, mid, date),
                                RentDto.class);
                        System.out.println("Saved rent: " + savedRent);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not rent movie\n");
                    }
                    break;
                }
                case "4": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    try {
                        restTemplate.delete(URLClients + "/{id}", id);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not delete client\n");
                    }
                    break;
                }
                case "5": {
                    System.out.println("Give id: ");
                    Long id = in.nextLong();

                    try {
                        restTemplate.delete(URLMovies + "/{id}", id);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not delete movie\n");
                    }
                    break;
                }
                case "6": {
                    System.out.println("Give id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    try {
                        restTemplate.delete(URLRents + "/{id}", id);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not delete rent\n");
                    }
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

                    Client client = new Client(name, email, age);
                    client.setId(id);
                    try {
                        restTemplate.put(URLClients + "/{id}", client, id);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not update client\n");
                    }
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

                    Movie movie = new Movie(title, rating, dvds);
                    movie.setId(id);
                    try {
                        restTemplate.put(URLRents + "/{id}", movie, id);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not update rent\n");
                    }
                    break;
                }
                case "9": {
                    System.out.println("Give rent id: ");
                    String idString = in.nextLine();
                    Long id = Long.parseLong(idString);

                    System.out.println("Give client id: ");
                    String cidString = in.nextLine();
                    Long cid = Long.parseLong(cidString);

                    System.out.println("Give rent id: ");
                    String midString = in.nextLine();
                    Long mid = Long.parseLong(midString);

                    System.out.println("Give return date: ");
                    String date = in.nextLine();

                    Rent rent = new Rent(cid, mid, date);
                    try {
                        restTemplate.put(URLRents + "/{id}", rent, id);
                    }
                    catch (Exception ignored) {
                        System.out.println("Could not update rent\n");
                    }
                    break;
                }
                case "10":
                    ClientsDto clientsDto = restTemplate.getForObject(URLClients, ClientsDto.class);
                    assert clientsDto != null;
                    List<Client> clients = clientsDto.getClients().stream().
                            map(clientConverter::convertDtoToModel).collect(Collectors.toList());
                    clients.forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case "11":
                    MoviesDto moviesDto = restTemplate.getForObject(URLMovies, MoviesDto.class);
                    assert moviesDto != null;
                    List<Movie> movies = moviesDto.getMovies().stream().
                            map(movieConverter::convertDtoToModel).collect(Collectors.toList());
                    movies.forEach(System.out::println);
                    System.out.println("\n");
                    break;
                case "12":
                    RentsDto rentsDto = restTemplate.getForObject(URLRents, RentsDto.class);
                    assert rentsDto != null;
                    List<Rent> rents = rentsDto.getRents().stream().
                            map(rentConverter::convertDtoToModel).collect(Collectors.toList());
                    rents.forEach(System.out::println);
                    System.out.println("\n");
            }
        }
    }
}
