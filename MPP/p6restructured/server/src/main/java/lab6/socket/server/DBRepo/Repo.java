package lab6.socket.server.DBRepo;

import lab6.socket.common.domain.BaseEntity;
import lab6.socket.common.domain.validators.ValidatorException;

import java.util.Optional;


public interface Repo<ID, T extends BaseEntity<ID>> {
    Optional<T> find(ID id) throws ValidatorException;
    Iterable<T> findAll();
    Optional<T> add(T entity) throws ValidatorException;
    Optional<T> delete(ID id) throws ValidatorException;
    Optional<T> update(T entity) throws ValidatorException;
}
