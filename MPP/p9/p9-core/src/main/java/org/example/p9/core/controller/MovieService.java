package org.example.p9.core.controller;

import org.example.p9.core.domain.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie addMovie(Movie movie);
    void deleteMovie(Long id);
    Movie updateMovie(Movie movie);
}
