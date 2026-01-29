package org.example.retoobjectdb.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.example.retoobjectdb.models.Film;
import org.example.retoobjectdb.utils.Repository;

import java.util.List;
import java.util.Optional;

public class FilmRepository implements Repository<Film, Integer> {

    private final EntityManagerFactory entityManagerFactory;

    public FilmRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Film save(Film film) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(film);
            tx.commit();
            return film;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Film> delete(Film entity) {
        return deleteById(entity.getId());
    }

    @Override
    public Optional<Film> deleteById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Optional<Film> film = Optional.ofNullable(em.find(Film.class, id));
            film.ifPresent(em::remove);
            tx.commit();
            return film;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Film> findById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Film.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Film> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Film f", Film.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Long count() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(f) FROM Film f", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
