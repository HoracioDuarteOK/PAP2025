package pap2025.logica;

import pap2025.persistencia.PersistenciaHibernate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de Materiales que gestiona el almacenamiento usando persistencia
 * Implementa el patrón Singleton
 * Toda la lógica de negocio está en ControladorFachada
 */
public class ManejadorMaterial {
    
    private static ManejadorMaterial instancia;
    
    // Constructor privado para Singleton
    private ManejadorMaterial() {
        // Ya no necesitamos ArrayList, usamos persistencia
    }
    
    // Método para obtener la instancia única (Singleton)
    public static ManejadorMaterial getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorMaterial();
        }
        return instancia;
    }
    
    /**
     * Obtiene la lista de materiales desde la base de datos
     * @return Lista de materiales
     */
    public List<Material> getListaMateriales() {
        return PersistenciaHibernate.obtenerTodosLosMateriales();
    }
    
    /**
     * Guarda un material en la base de datos
     * @param material Material a guardar
     */
    public void guardarMaterial(Material material) {
        PersistenciaHibernate.guardarMaterial(material);
    }
    
    /**
     * Actualiza un material en la base de datos
     * @param material Material a actualizar
     */
    public void actualizarMaterial(Material material) {
        PersistenciaHibernate.actualizarMaterial(material);
    }
    
    /**
     * Elimina un material de la base de datos
     * @param material Material a eliminar
     */
    public void eliminarMaterial(Material material) {
        PersistenciaHibernate.eliminarMaterial(material);
    }
    
    /**
     * Obtiene un material por ID desde la base de datos
     * @param id ID del material
     * @return Material encontrado o null
     */
    public Material obtenerMaterialPorId(int id) {
        return PersistenciaHibernate.obtenerMaterialPorId(id);
    }
    
    /**
     * Obtiene todos los libros desde la base de datos
     * @return Lista de libros
     */
    public List<Libro> obtenerTodosLosLibros() {
        return PersistenciaHibernate.obtenerTodosLosLibros();
    }
    
    /**
     * Obtiene todos los artículos especiales desde la base de datos
     * @return Lista de artículos especiales
     */
    public List<ArtEspeciales> obtenerTodosLosArtEspeciales() {
        return PersistenciaHibernate.obtenerTodosLosArtEspeciales();
    }
}
