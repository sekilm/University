package lab6.socket.common.domain.validators;

import lab6.socket.common.domain.Rent;

import java.util.Optional;


public class RentValidator implements Validator<Rent> {
    @Override
    public void validate(Rent entity) {
        Optional.ofNullable(entity.getId()).orElseThrow(() -> new ValidatorException("ID cannot be null"));
        Optional.ofNullable(entity.getId().getKey()).orElseThrow(() -> new ValidatorException("Client ID cannot be null"));
        Optional.ofNullable(entity.getId().getValue()).orElseThrow(() -> new ValidatorException("Movie ID cannot be null"));
    }
}
