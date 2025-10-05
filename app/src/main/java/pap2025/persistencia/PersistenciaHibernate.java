package pap2025.persistencia;

import pap2025.logica.*;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase para manejar todas las operaciones de persistencia con Hibernate
 */
public class PersistenciaHibernate {
    
    // ===== MÉTODOS PARA USUARIOS =====
    
    public static void guardarUsuario(Usuario usuario) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(usuario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void actualizarUsuario(Usuario usuario) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(usuario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void eliminarUsuario(Usuario usuario) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(usuario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static List<Usuario> obtenerTodosLosUsuarios() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Usuario> query = session.createQuery("FROM Usuario", Usuario.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static Usuario obtenerUsuarioPorEmail(String email) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
    
    public static List<Lector> obtenerTodosLosLectores() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Lector> query = session.createQuery("FROM Lector", Lector.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static List<Bibliotecario> obtenerTodosLosBibliotecarios() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Bibliotecario> query = session.createQuery("FROM Bibliotecario", Bibliotecario.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    // ===== MÉTODOS PARA MATERIALES =====
    
    public static void guardarMaterial(Material material) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(material);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void actualizarMaterial(Material material) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(material);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void eliminarMaterial(Material material) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(material);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static List<Material> obtenerTodosLosMateriales() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Material> query = session.createQuery("FROM Material", Material.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static Material obtenerMaterialPorId(int id) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Material> query = session.createQuery("FROM Material WHERE id = :id", Material.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
    
    public static List<Libro> obtenerTodosLosLibros() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Libro> query = session.createQuery("FROM Libro", Libro.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static List<ArtEspeciales> obtenerTodosLosArtEspeciales() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<ArtEspeciales> query = session.createQuery("FROM ArtEspeciales", ArtEspeciales.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    // ===== MÉTODOS PARA PRÉSTAMOS =====
    
    public static void guardarPrestamo(Prestamo prestamo) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void actualizarPrestamo(Prestamo prestamo) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void eliminarPrestamo(Prestamo prestamo) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static List<Prestamo> obtenerTodosLosPrestamos() {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Prestamo> query = session.createQuery("FROM Prestamo", Prestamo.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static Prestamo obtenerPrestamoPorId(int id) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Prestamo> query = session.createQuery("FROM Prestamo WHERE id = :id", Prestamo.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
    
    public static List<Prestamo> obtenerPrestamosPorLector(String emailLector) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p JOIN FETCH p.lector l WHERE l.email = :email", Prestamo.class);
            query.setParameter("email", emailLector);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static List<Prestamo> obtenerPrestamosPorBibliotecario(String emailBibliotecario) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p JOIN FETCH p.bibliotecario b WHERE b.email = :email", Prestamo.class);
            query.setParameter("email", emailBibliotecario);
            return query.list();
        } finally {
            session.close();
        }
    }
    
    public static List<Prestamo> obtenerPrestamosPorEstado(EstadoP estado) {
        Session session = ConfiguracionBD.getSessionFactory().openSession();
        try {
            Query<Prestamo> query = session.createQuery("FROM Prestamo WHERE estadoP = :estado", Prestamo.class);
            query.setParameter("estado", estado);
            return query.list();
        } finally {
            session.close();
        }
    }
}
