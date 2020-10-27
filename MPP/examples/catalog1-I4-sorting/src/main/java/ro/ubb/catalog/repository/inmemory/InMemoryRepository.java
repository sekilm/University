package ro.ubb.catalog.repository.inmemory;

import ro.ubb.catalog.domain.BaseEntity;
import ro.ubb.catalog.domain.validators.Validator;
import ro.ubb.catalog.domain.validators.ValidatorException;
import ro.ubb.catalog.repository.sorting.SortingRepository;
import ro.ubb.catalog.repository.sorting.impl.Sort;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author radu.
 */
public class InMemoryRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        implements
        SortingRepository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(Optional<ID> id) {
        ID myId = id.orElseThrow(() -> {
            throw new IllegalArgumentException("id must not " +
                    "be null");
        });
        return Optional.ofNullable(entities.get(myId));
    }

    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<T> save(Optional<T> entity) throws ValidatorException {
        T myEntity = entity.orElseThrow(() -> {
            throw new IllegalArgumentException("entity must not be null");
        });
        validator.validate(myEntity);
        return Optional.ofNullable(entities.putIfAbsent(myEntity.getId(),
                myEntity));
    }

    @Override
    public Optional<T> delete(Optional<ID> id) {
        ID myId = id.orElseThrow(() -> {
            throw new IllegalArgumentException("id must not be null");
        });
        return Optional.ofNullable(entities.remove(myId));
    }

    @Override
    public Optional<T> update(Optional<T> entity) throws ValidatorException {
        T myEntity = entity.orElseThrow(() -> {
            throw new IllegalArgumentException("entity must not be null");
        });
        validator.validate(myEntity);
        return Optional.ofNullable(entities.computeIfPresent(myEntity.getId(),
                (k, v) -> myEntity));
    }


    @Override
    public Iterable<T> findAll(Sort sort) {
        throw new RuntimeException("not yet implemented");
    }
}
