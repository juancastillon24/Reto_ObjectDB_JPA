package org.example.retoobjectdb.utils;

import org.example.retoobjectdb.models.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class DataSeeder {

    public static void seedUsers(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // 1. Comprobamos si ya existen usuarios para no duplicar
            Long count = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();

            if (count == 0) {
                System.out.println("🌱 Base de datos de Usuarios vacía. Iniciando siembra de datos...");

                // --- Crear ADMIN ---
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin"); // Recuerda: en producción esto debería ir hasheado
                admin.setIsAdmin(true);
                em.persist(admin);

                // --- Crear Usuario Normal 1 ---
                User user1 = new User();
                user1.setUsername("juan");
                user1.setPassword("1234");
                user1.setIsAdmin(false);
                em.persist(user1);

                // --- Crear Usuario Normal 2 ---
                User user2 = new User();
                user2.setUsername("maria");
                user2.setPassword("abcd");
                user2.setIsAdmin(false);
                em.persist(user2);

                System.out.println("✅ Se han insertado 3 usuarios de prueba correctamente.");
            } else {
                System.out.println("ℹ️ Ya existen usuarios en la base de datos. Se omite el seed.");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }
}