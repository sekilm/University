package ro.ubb.catalog.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
