package pap2025.logica;

import pap2025.persistencia.PersistenciaHibernate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de Usuarios que gestiona el almacenamiento usando persistencia
 * Implementa el patrón Singleton
 * Toda la lógica de negocio está en ControladorFachada
 */
public class ManejadorUsuario {
    
    private static ManejadorUsuario instancia;
    
    // Constructor privado para Singleton
    private ManejadorUsuario() {
        // Ya no necesitamos ArrayList, usamos persistencia
    }
    
    // Método para obtener la instancia única (Singleton)
    public static ManejadorUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorUsuario();
        }
        return instancia;
    }
    
    /**
     * Obtiene la lista de usuarios desde la base de datos
     * @return Lista de usuarios
     */
    public List<Usuario> getListaUsuarios() {
        return PersistenciaHibernate.obtenerTodosLosUsuarios();
    }
    
    /**
     * Guarda un usuario en la base de datos
     * @param usuario Usuario a guardar
     */
    public void guardarUsuario(Usuario usuario) {
        PersistenciaHibernate.guardarUsuario(usuario);
    }
    
    /**
     * Actualiza un usuario en la base de datos
     * @param usuario Usuario a actualizar
     */
    public void actualizarUsuario(Usuario usuario) {
        PersistenciaHibernate.actualizarUsuario(usuario);
    }
    
    /**
     * Elimina un usuario de la base de datos
     * @param usuario Usuario a eliminar
     */
    public void eliminarUsuario(Usuario usuario) {
        PersistenciaHibernate.eliminarUsuario(usuario);
    }
    
    /**
     * Obtiene un usuario por email desde la base de datos
     * @param email Email del usuario
     * @return Usuario encontrado o null
     */
    public Usuario obtenerUsuarioPorEmail(String email) {
        return PersistenciaHibernate.obtenerUsuarioPorEmail(email);
    }
    
    /**
     * Obtiene todos los lectores desde la base de datos
     * @return Lista de lectores
     */
    public List<Lector> obtenerTodosLosLectores() {
        return PersistenciaHibernate.obtenerTodosLosLectores();
    }
    
    /**
     * Obtiene todos los bibliotecarios desde la base de datos
     * @return Lista de bibliotecarios
     */
    public List<Bibliotecario> obtenerTodosLosBibliotecarios() {
        return PersistenciaHibernate.obtenerTodosLosBibliotecarios();
    }
}
