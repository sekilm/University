package org.example.p9.core.domain.validators;

import org.example.p9.core.domain.Movie;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie entity) throws ValidatorException {
        Optional.of(entity.getTitle()).filter(n -> n.length() > 0).orElseThrow(() -> new ValidatorException("Title cannot be null"));
        Optional.of(entity.getRating()).filter(n -> n >= 0 && n <= 10).orElseThrow(() -> new ValidatorException("Rating has to be between 0 and 10"));
        Optional.of(entity.getNrOfAvailableDVDs()).filter(n -> n >= 0).orElseThrow(() -> new ValidatorException("Number of available DVDs cannot be negative"));
    }
}
