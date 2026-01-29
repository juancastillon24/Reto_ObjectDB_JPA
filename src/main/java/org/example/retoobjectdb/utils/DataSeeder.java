package org.example.retoobjectdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.example.retoobjectdb.models.User;
import org.example.retoobjectdb.models.Copy; // Asegúrate de importar tu entidad Copy

public class DataSeeder {

    public static void main(String[] args) {
        // 1. Configurar la conexión a ObjectDB
        // Puedes usar el nombre de tu persistence-unit en persistence.xml o la ruta directa al archivo .odb
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/biblioteca.odb");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // --- Creación de Usuario Administrador ---
            System.out.println("Creando administrador...");
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin"); // En producción, recuerda hashear la contraseña
            admin.setIsAdmin(true);

            // --- Creación de Usuario Normal ---
            System.out.println("Creando usuario estándar...");
            User user = new User();
            user.setUsername("juan");
            user.setPassword("1234");
            user.setIsAdmin(false);

            // --- Ejemplo de relación con Copy (Opcional) ---
            // Gracias a CascadeType.ALL en tu entidad User, si agregas copias,
            // se guardarán automáticamente al guardar el User.
            /* Copy copy1 = new Copy();
            // configurar propiedades de copy1...
            user.addCopy(copy1); // Usamos tu método helper addCopy
            */

            // 3. Persistir los objetos
            // Si la base de datos ya tiene datos, podrías querer comprobar antes si existen
            em.persist(admin);
            em.persist(user);

            em.getTransaction().commit();
            System.out.println("¡Datos sembrados correctamente!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}