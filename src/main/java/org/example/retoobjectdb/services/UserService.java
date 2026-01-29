package org.example.retoobjectdb.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.example.retoobjectdb.models.Copy;
import org.example.retoobjectdb.models.User;
import org.example.retoobjectdb.utils.JPAUtil;

public class UserService {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public User deleteCopyFromUser(User user, Copy copy) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Recargar datos desde la BD
            User currentUser = em.find(User.class, user.getId());
            Copy copyToDelete = em.find(Copy.class, copy.getId());

            if (currentUser != null && copyToDelete != null) {
                // Al eliminar la copia, JPA debería manejar la actualización de la colección del usuario.
                // Para estar seguros, eliminamos explícitamente de la colección gestionada.
                currentUser.getCopies().remove(copyToDelete);
                em.remove(copyToDelete);
            }

            tx.commit();
            return currentUser;
        } finally {
            em.close();
        }
    }

    public User createNewCopy(Copy newCopy, User actualUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            actualUser.addCopy(newCopy);
            User mergedUser = em.merge(actualUser);
            tx.commit();
            return mergedUser;
        } finally {
            em.close();
        }

    }
}
