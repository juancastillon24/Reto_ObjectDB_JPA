package org.example.retoobjectdb.repositories;

import org.example.retoobjectdb.models.Film;
import org.example.retoobjectdb.utils.Repository;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class FilmRepository implements Repository<Film> {

    SessionFactory sessionFactory;

    public FilmRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Film save(Film film) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(film);
            session.getTransaction().commit();
            return film;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Film> delete(Film entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Film> findAll() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("from Film", Film.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Long count() {
        return 0L;
    }
}
