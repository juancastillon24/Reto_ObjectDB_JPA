package org.example.retoobjectdb.repositories;

import org.example.retoobjectdb.models.Copy;
import org.example.retoobjectdb.utils.Repository;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CopyRespository implements Repository<Copy> {

    private SessionFactory sessionFactory;

    public CopyRespository(SessionFactory sessionFactory) {
        this.sessionFactory  = sessionFactory;
    }
    @Override
    public Copy save(Copy entity) {
        return null;
    }

    @Override
    public Optional<Copy> delete(Copy entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Copy> deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Copy> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Copy> findAll() {
        return List.of();
    }

    @Override
    public Long count() {
        return 0L;
    }
}
