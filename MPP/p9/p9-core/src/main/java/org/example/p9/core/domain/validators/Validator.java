package org.example.p9.core.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
