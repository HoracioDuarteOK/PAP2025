package pap2025.logica;

import pap2025.persistencia.PersistenciaHibernate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de Préstamos que gestiona el almacenamiento usando persistencia
 * Implementa el patrón Singleton
 * Toda la lógica de negocio está en ControladorFachada
 */
public class ManejadorPrestamo {
    
    private static ManejadorPrestamo instancia;
    
    // Constructor privado para Singleton
    private ManejadorPrestamo() {
        // Ya no necesitamos ArrayList, usamos persistencia
    }
    
    // Método para obtener la instancia única (Singleton)
    public static ManejadorPrestamo getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorPrestamo();
        }
        return instancia;
    }
    
    /**
     * Obtiene la lista de préstamos desde la base de datos
     * @return Lista de préstamos
     */
    public List<Prestamo> getListaPrestamos() {
        return PersistenciaHibernate.obtenerTodosLosPrestamos();
    }
    
    /**
     * Guarda un préstamo en la base de datos
     * @param prestamo Préstamo a guardar
     */
    public void guardarPrestamo(Prestamo prestamo) {
        PersistenciaHibernate.guardarPrestamo(prestamo);
    }
    
    /**
     * Actualiza un préstamo en la base de datos
     * @param prestamo Préstamo a actualizar
     */
    public void actualizarPrestamo(Prestamo prestamo) {
        PersistenciaHibernate.actualizarPrestamo(prestamo);
    }
    
    /**
     * Elimina un préstamo de la base de datos
     * @param prestamo Préstamo a eliminar
     */
    public void eliminarPrestamo(Prestamo prestamo) {
        PersistenciaHibernate.eliminarPrestamo(prestamo);
    }
    
    /**
     * Obtiene un préstamo por ID desde la base de datos
     * @param id ID del préstamo
     * @return Préstamo encontrado o null
     */
    public Prestamo obtenerPrestamoPorId(int id) {
        return PersistenciaHibernate.obtenerPrestamoPorId(id);
    }
    
    /**
     * Obtiene préstamos por lector desde la base de datos
     * @param emailLector Email del lector
     * @return Lista de préstamos del lector
     */
    public List<Prestamo> obtenerPrestamosPorLector(String emailLector) {
        return PersistenciaHibernate.obtenerPrestamosPorLector(emailLector);
    }
    
    /**
     * Obtiene préstamos por bibliotecario desde la base de datos
     * @param emailBibliotecario Email del bibliotecario
     * @return Lista de préstamos del bibliotecario
     */
    public List<Prestamo> obtenerPrestamosPorBibliotecario(String emailBibliotecario) {
        return PersistenciaHibernate.obtenerPrestamosPorBibliotecario(emailBibliotecario);
    }
    
    /**
     * Obtiene préstamos por estado desde la base de datos
     * @param estado Estado de los préstamos
     * @return Lista de préstamos con ese estado
     */
    public List<Prestamo> obtenerPrestamosPorEstado(EstadoP estado) {
        return PersistenciaHibernate.obtenerPrestamosPorEstado(estado);
    }
}
