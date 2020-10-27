package lab6.socket.server.controller;


import lab6.socket.common.domain.Movie;
import lab6.socket.common.domain.validators.ValidatorException;
import lab6.socket.server.DBRepo.Repo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieController {
    private Repo<Long, Movie> movieRepo;

    public MovieController(Repo<Long, Movie> repo) { movieRepo = repo; }
    public List<Movie> getAllMovies() {
        Iterable<Movie> movies = movieRepo.findAll();
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Movie> addMovie(Movie movie) throws ValidatorException { return movieRepo.add(movie); }

    public Optional<Movie> deleteMovie(Long id) { return movieRepo.delete(id); }

    public Optional<Movie> updateMovie(Movie movie) { return movieRepo.update(movie); }

    public List<Movie> filterMoviesByRanking(double ranking) {
        Iterable<Movie> movies = movieRepo.findAll();
        List<Movie> filteredMovies = StreamSupport.stream(movies.spliterator(), false).filter(movie -> movie.getRating() > ranking).collect(Collectors.toList());
        return filteredMovies;
    }
}
