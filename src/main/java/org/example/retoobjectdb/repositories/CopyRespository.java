package org.example.retoobjectdb.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.example.retoobjectdb.models.Copy;
import org.example.retoobjectdb.utils.Repository;

import java.util.List;
import java.util.Optional;

public class CopyRespository implements Repository<Copy, Integer> {

    private final EntityManagerFactory entityManagerFactory;

    public CopyRespository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public Copy save(Copy entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
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
    public Optional<Copy> delete(Copy entity) {
        return deleteById(entity.getId());
    }

    @Override
    public Optional<Copy> deleteById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Optional<Copy> copy = Optional.ofNullable(em.find(Copy.class, id));
            copy.ifPresent(em::remove);
            tx.commit();
            return copy;
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
    public Optional<Copy> findById(Integer id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Copy.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Copy> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Copy c", Copy.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Long count() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM Copy c", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
