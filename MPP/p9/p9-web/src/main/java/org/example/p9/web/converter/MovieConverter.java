package org.example.p9.web.converter;

import org.example.p9.core.domain.Movie;
import org.springframework.stereotype.Component;
import org.example.p9.web.dto.MovieDto;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .rating(dto.getRating())
                .nrOfAvailableDVDs(dto.getNrOfAvailableDVDs())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .title(movie.getTitle())
                .rating(movie.getRating())
                .nrOfAvailableDVDs(movie.getNrOfAvailableDVDs())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
