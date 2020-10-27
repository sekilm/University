package lab6.socket.common.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
