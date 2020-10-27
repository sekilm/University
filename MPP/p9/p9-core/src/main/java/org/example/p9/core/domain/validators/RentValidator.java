package org.example.p9.core.domain.validators;

import org.example.p9.core.domain.Rent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RentValidator implements Validator<Rent> {

    @Override
    public void validate(Rent entity) {
        Optional.ofNullable(entity.getClientId()).orElseThrow(() -> new ValidatorException("Client ID cannot be null"));
        Optional.ofNullable(entity.getMovieId()).orElseThrow(() -> new ValidatorException("Movie ID cannot be null"));
    }
}
