package org.example.p9.core.controller;

import org.example.p9.core.domain.Movie;
import org.example.p9.core.domain.validators.MovieValidator;
import org.example.p9.core.domain.validators.ValidatorException;
import org.example.p9.core.repository.MovieRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieServiceImpl implements MovieService {
    public static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private MovieRepo movieRepo;
    @Autowired
    private MovieValidator movieValidator;

    @Override
    public List<Movie> getAllMovies() {
        log.trace("getAllMovies --- method entered");
        return movieRepo.findAll();

    }

    @Override
    public Movie addMovie(Movie movie) throws ValidatorException {
        log.trace("addMovie --- method entered: movie: movie={}", movie);
        movieValidator.validate(movie);
        movieRepo.save(movie);
        log.trace("addMovie --- method finished");
        return movie;
    }

    @Override
    public void deleteMovie(Long id) {
        log.trace("deleteMovie --- method entered: id={}", id);
        movieRepo.deleteById(id);
        log.trace("deleteMovie --- method finished");
    }

    @Override
    @Transactional
    public Movie updateMovie(Movie movie) {
        log.trace("updateMovie --- method entered: movie={}", movie);
        movieValidator.validate(movie);
        movieRepo.findById(movie.getId()).ifPresent(m -> {
            m.setTitle(movie.getTitle());
            m.setNrOfAvailableDVDs(movie.getNrOfAvailableDVDs());
            m.setRating(movie.getRating());
            log.debug("updateMovie --- updated: movie={}", m);
        });
        return movie;
    }

    public Set<Movie> filterMoviesByRanking(double ranking) {
        Iterable<Movie> movies = movieRepo.findAll();
        Set<Movie> filteredMovies = StreamSupport.stream(movies.spliterator(), false).filter(movie -> movie.getRating() > ranking).collect(Collectors.toSet());
        return filteredMovies;
    }
}
