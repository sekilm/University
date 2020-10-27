package org.example.p9.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoviesDto {
    private Set<MovieDto> movies;
}
