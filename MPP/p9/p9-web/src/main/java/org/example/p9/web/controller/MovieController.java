package org.example.p9.web.controller;

import org.example.p9.core.controller.MovieService;
import org.example.p9.web.converter.MovieConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.p9.web.dto.MovieDto;
import org.example.p9.web.dto.MoviesDto;

@RestController
public class MovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MoviesDto getMovies() {
        log.trace("getMovies --- method entered");
        return new MoviesDto(movieConverter
                .convertModelsToDtos(movieService.getAllMovies()));

    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    MovieDto saveMovie(@RequestBody MovieDto movieDto) {
        log.trace("addMovie --- method entered: movie: movie={}", movieDto);
        return movieConverter.convertModelToDto(movieService.addMovie(
                movieConverter.convertDtoToModel(movieDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    MovieDto updateMovie(@PathVariable Long id,
                            @RequestBody MovieDto movieDto) {
        log.trace("updateMovie --- method entered: movie={}", movieDto);
        return movieConverter.convertModelToDto( movieService.updateMovie(
                movieConverter.convertDtoToModel(movieDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id){
        log.trace("deleteMovie --- method entered: id={}", id);
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
