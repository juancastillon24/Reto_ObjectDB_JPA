package org.example.retoobjectdb.services;

import org.example.retoobjectdb.models.Copy;
import org.example.retoobjectdb.models.User;
import org.example.retoobjectdb.utils.DataProvider;
import org.hibernate.Session;

public class UserService {
    public User deleteCopyFromUser(User user, Copy copy) {
        try(Session s = DataProvider.getSessionFactory().openSession()) {
            s.beginTransaction();

            // Recargar datos desde la BD
            User currentUser = s.find(User.class, user.getId());
            Copy copyToDelete = s.find(Copy.class, copy.getId());

            // Buscar y eliminar el juego de la colección y la bbdd
            currentUser.getCopies().remove(copyToDelete);
            currentUser.getCopies().removeIf(g -> g.getId().equals(copy.getId()));
            s.remove(copyToDelete);

            s.getTransaction().commit();

            return currentUser;
        }
    }

    public User createNewCopy(Copy newCopy, User actualUser) {
        try(Session s = DataProvider.getSessionFactory().openSession()) {
            actualUser.addCopy(newCopy);
            s.beginTransaction();
            s.merge(actualUser);
            s.getTransaction().commit();
            return s.find(User.class, actualUser.getId());
        }

    }
}
