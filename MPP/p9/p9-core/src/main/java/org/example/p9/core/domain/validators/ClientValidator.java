package org.example.p9.core.domain.validators;

import org.example.p9.core.domain.Client;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        Optional.of(entity.getAge()).filter(n -> n > 14);
        Optional.of(entity.getName()).filter(n -> n.length() > 0).orElseThrow(() -> new ValidatorException("Name cannot be empty"));
        Optional.of(entity.getEmail()).filter(n -> n.length() > 0 && n.contains("@")).orElseThrow(() -> new ValidatorException("Email cannot be empty and it must include @"));
    }
}
