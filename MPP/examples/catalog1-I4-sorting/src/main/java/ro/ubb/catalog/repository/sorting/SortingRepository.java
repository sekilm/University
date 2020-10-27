package ro.ubb.catalog.repository.sorting;

import ro.ubb.catalog.domain.BaseEntity;
import ro.ubb.catalog.repository.CrudRepository;
import ro.ubb.catalog.repository.sorting.impl.Sort;

import java.io.Serializable;

/**
 * Created by radu.
 */
public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends CrudRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}

