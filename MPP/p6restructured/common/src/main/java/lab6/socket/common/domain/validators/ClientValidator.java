package lab6.socket.common.domain.validators;

import lab6.socket.common.domain.Client;

import java.util.Optional;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        Optional.ofNullable(entity.getId()).orElseThrow(() -> new ValidatorException("The ID cannot be null"));
        Optional.of(entity.getAge()).filter(n -> n > 14);
        Optional.of(entity.getName()).filter(n -> n.length() > 0).orElseThrow(() -> new ValidatorException("Name cannot be empty"));
        Optional.of(entity.getEmail()).filter(n -> n.length() > 0 && n.contains("@")).orElseThrow(() -> new ValidatorException("Email cannot be empty and it must include @"));
    }
}
