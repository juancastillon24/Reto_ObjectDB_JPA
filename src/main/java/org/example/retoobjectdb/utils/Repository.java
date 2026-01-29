package org.example.retoobjectdb.utils;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    T save(T entity);
    Optional<T> delete(T entity);
    Optional<T> deleteById(ID id);

    Optional<T> findById(ID id);
    List<T> findAll();
    Long count();
}
