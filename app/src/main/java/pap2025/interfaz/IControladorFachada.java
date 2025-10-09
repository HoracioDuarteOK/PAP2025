package pap2025.interfaz;

import pap2025.logica.ManejadorUsuario;
import pap2025.logica.ManejadorMaterial;
import pap2025.logica.ManejadorPrestamo;
import pap2025.logica.Lector;
import pap2025.logica.Zona;
import pap2025.logica.Usuario;
import pap2025.logica.Bibliotecario;
import pap2025.logica.Material;
import pap2025.logica.Libro;
import pap2025.logica.ArtEspeciales;
import pap2025.logica.Prestamo;
import pap2025.logica.EstadoL;
import pap2025.logica.EstadoP;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;
import java.util.List;

/**
 * Interfaz del Controlador Fachada que define los métodos para acceder a los manejadores
 * y toda la lógica de negocio del sistema
 */
public interface IControladorFachada {
    
    /**
     * Obtiene el manejador de usuarios
     * @return ManejadorUsuario para gestionar usuarios
     */
    public ManejadorUsuario getManejadorUsuario();
    
    /**
     * Obtiene el manejador de materiales
     * @return ManejadorMaterial para gestionar materiales
     */
    public ManejadorMaterial getManejadorMaterial();
    
    /**
     * Obtiene el manejador de préstamos
     * @return ManejadorPrestamo para gestionar préstamos
     */
    public ManejadorPrestamo getManejadorPrestamo();
    
    // ===== MÉTODOS DE GESTIÓN DE USUARIOS =====
    
    /**
     * Crea un nuevo usuario
     * @param nombre Nombre del usuario
     * @param email Email del usuario
     * @return true si se creó exitosamente, false en caso contrario
     */
    public boolean crearUsuario(String nombre, String email);
    
    /**
     * Obtiene un usuario por su email
     * @param email Email del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerUsuario(String email);
    
    /**
     * Verifica si existe un usuario con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeUsuario(String email);
    
    /**
     * Edita un usuario existente
     * @param email Email del usuario a editar
     * @param nuevoNombre Nuevo nombre del usuario
     * @return true si se editó exitosamente, false en caso contrario
     */
    public boolean editarUsuario(String email, String nuevoNombre);
    
    /**
     * Elimina un usuario del sistema
     * @param email Email del usuario a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminarUsuario(String email);
    
    /**
     * Obtiene la cantidad total de usuarios
     * @return Número total de usuarios
     */
    public int obtenerCantidadUsuarios();
    
    /**
     * Obtiene la lista de usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> getListaUsuarios();
    
    // ===== MÉTODOS DE GESTIÓN DE LECTORES =====
    
    /**
     * Registra un nuevo lector en el sistema
     * @param nombre Nombre del lector
     * @param email Email del lector
     * @param direccion Dirección del lector
     * @param fechaRegistro Fecha de registro
     * @param zona Zona de la biblioteca
     * @return true si se registró exitosamente, false en caso contrario
     */
    public boolean registrarLector(String nombre, String email, String direccion, DTFecha fechaRegistro, Zona zona);
    
    /**
     * Obtiene un lector por su email
     * @param email Email del lector
     * @return Lector encontrado o null si no existe
     */
    public Lector obtenerLector(String email);
    
    /**
     * Verifica si existe un lector con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeLector(String email);
    
    /**
     * Modifica el estado de un lector
     * @param email Email del lector
     * @param nuevoEstado Nuevo estado del lector
     * @return true si se modificó exitosamente, false en caso contrario
     */
    public boolean modificarEstadoLector(String email, EstadoL nuevoEstado);
    
    /**
     * Suspende un lector
     * @param email Email del lector a suspender
     * @return true si se suspendió exitosamente, false en caso contrario
     */
    public boolean suspenderLector(String email);
    
    /**
     * Activa un lector suspendido
     * @param email Email del lector a activar
     * @return true si se activó exitosamente, false en caso contrario
     */
    public boolean activarLector(String email);
    
    /**
     * Verifica si un lector puede realizar préstamos
     * @param email Email del lector
     * @return true si puede prestar, false en caso contrario
     */
    public boolean lectorPuedePrestar(String email);
    
    /**
     * Cambia la zona de un lector
     * @param email Email del lector
     * @param nuevaZona Nueva zona para el lector
     * @return true si se cambió exitosamente, false en caso contrario
     */
    public boolean cambiarZonaLector(String email, Zona nuevaZona);
    
    /**
     * Obtiene todos los lectores del sistema
     * @return Lista de todos los lectores
     */
    public List<Lector> obtenerTodosLosLectores();
    
    /**
     * Obtiene lectores por estado
     * @param estado Estado a filtrar
     * @return Lista de lectores con el estado especificado
     */
    public List<Lector> obtenerLectoresPorEstado(EstadoL estado);
    
    /**
     * Obtiene lectores suspendidos
     * @return Lista de lectores suspendidos
     */
    public List<Lector> obtenerLectoresSuspendidos();
    
    /**
     * Obtiene lectores activos
     * @return Lista de lectores activos
     */
    public List<Lector> obtenerLectoresActivos();
    
    /**
     * Obtiene la cantidad total de lectores
     * @return Número total de lectores
     */
    public int obtenerCantidadLectores();
    
    /**
     * Obtiene la cantidad de lectores por estado
     * @param estado Estado a contar
     * @return Cantidad de lectores con el estado especificado
     */
    public int obtenerCantidadLectoresPorEstado(EstadoL estado);
    
    /**
     * Obtiene la lista de lectores
     * @return Lista de lectores
     */
    public List<Lector> getListaLectores();
    
    // ===== MÉTODOS DE GESTIÓN DE BIBLIOTECARIOS =====
    
    /**
     * Registra un nuevo bibliotecario en el sistema
     * @param nombre Nombre del bibliotecario
     * @param email Email del bibliotecario
     * @param nroEmpleado Número de empleado del bibliotecario
     * @return true si se registró exitosamente, false en caso contrario
     */
    public boolean registrarBibliotecario(String nombre, String email, int nroEmpleado);
    
    /**
     * Obtiene un bibliotecario por su email
     * @param email Email del bibliotecario
     * @return Bibliotecario encontrado o null si no existe
     */
    public Bibliotecario obtenerBibliotecario(String email);
    
    /**
     * Verifica si existe un bibliotecario con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeBibliotecario(String email);
    
    /**
     * Verifica si existe un bibliotecario con el número de empleado dado
     * @param nroEmpleado Número de empleado a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeBibliotecarioPorNroEmpleado(int nroEmpleado);
    
    /**
     * Obtiene todos los bibliotecarios del sistema
     * @return Lista de todos los bibliotecarios
     */
    public List<Bibliotecario> obtenerTodosLosBibliotecarios();
    
    /**
     * Obtiene la cantidad total de bibliotecarios
     * @return Número total de bibliotecarios
     */
    public int obtenerCantidadBibliotecarios();
    
    /**
     * Obtiene la lista de bibliotecarios
     * @return Lista de bibliotecarios
     */
    public List<Bibliotecario> getListaBibliotecarios();
    
    // ===== MÉTODOS DE GESTIÓN DE MATERIALES =====
    
    /**
     * Crea un nuevo material
     * @param fechaIngreso Fecha de ingreso del material
     * @return ID del material creado o null si falló
     */
    public Integer crearMaterial(DTFecha fechaIngreso);
    
    /**
     * Crea un nuevo libro
     * @param fechaIngreso Fecha de ingreso del libro
     * @param titulo Título del libro
     * @param cantPaginas Cantidad de páginas del libro
     * @return ID del libro creado o null si falló
     */
    public Integer crearLibro(DTFecha fechaIngreso, String titulo, int cantPaginas);
    
    /**
     * Crea un nuevo artículo especial
     * @param fechaIngreso Fecha de ingreso del artículo
     * @param descripcion Descripción del artículo
     * @param peso Peso del artículo
     * @param dimensiones Dimensiones del artículo
     * @return ID del artículo creado o null si falló
     */
    public Integer crearArtEspecial(DTFecha fechaIngreso, String descripcion, double peso, DTDimensiones dimensiones);
    
    /**
     * Obtiene un material por su ID
     * @param id ID del material
     * @return Material encontrado o null si no existe
     */
    public Material obtenerMaterial(int id);
    
    /**
     * Verifica si existe un material con el ID dado
     * @param id ID a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeMaterial(int id);
    
    /**
     * Edita un material existente
     * @param id ID del material a editar
     * @param nuevaFechaIngreso Nueva fecha de ingreso
     * @return true si se editó exitosamente, false en caso contrario
     */
    public boolean editarMaterial(int id, DTFecha nuevaFechaIngreso);
    
    /**
     * Elimina un material del sistema
     * @param id ID del material a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminarMaterial(int id);
    
    /**
     * Obtiene el siguiente ID disponible para materiales
     * @return Siguiente ID disponible
     */
    public int obtenerSiguienteIdMaterial();
    
    /**
     * Obtiene la cantidad total de materiales
     * @return Número total de materiales
     */
    public int obtenerCantidadMateriales();
    
    /**
     * Obtiene la lista de materiales
     * @return Lista de materiales
     */
    public List<Material> getListaMateriales();
    
    /**
     * Obtiene la lista de materiales disponibles para préstamo
     * @return Lista de materiales disponibles (no están en préstamos activos)
     */
    public List<Material> obtenerMaterialesDisponibles();
    
    /**
     * Verifica si un material está disponible para préstamo
     * @param material Material a verificar
     * @return true si está disponible, false si está en préstamo activo
     */
    public boolean estaMaterialDisponible(Material material);
    
    /**
     * Actualiza completamente un préstamo con todos sus campos
     * @param idPrestamo ID del préstamo a actualizar
     * @param nuevoMaterial Nuevo material
     * @param nuevoLector Nuevo lector
     * @param nuevoBibliotecario Nuevo bibliotecario
     * @param nuevoEstado Nuevo estado
     * @param nuevaFechaSolicitud Nueva fecha de solicitud
     * @param nuevaFechaDevolucion Nueva fecha de devolución
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarPrestamoCompleto(int idPrestamo, Material nuevoMaterial, Lector nuevoLector, 
                                            Bibliotecario nuevoBibliotecario, EstadoP nuevoEstado, 
                                            DTFecha nuevaFechaSolicitud, DTFecha nuevaFechaDevolucion);
    
    // ===== MÉTODOS DE GESTIÓN DE DONACIONES =====
    
    /**
     * Registra una nueva donación de libro
     * @param titulo Título del libro
     * @param cantPaginas Cantidad de páginas del libro
     * @return ID del material creado o null si falló
     */
    public Integer registrarDonacionLibro(String titulo, int cantPaginas);
    
    /**
     * Registra una nueva donación de artículo especial
     * @param descripcion Descripción del artículo especial
     * @param peso Peso del artículo en kg
     * @param dimensiones Dimensiones del artículo
     * @return ID del material creado o null si falló
     */
    public Integer registrarDonacionArtEspecial(String descripcion, double peso, DTDimensiones dimensiones);
    
    /**
     * Obtiene todos los libros del sistema
     * @return Lista de todos los libros
     */
    public List<Libro> obtenerTodosLosLibros();
    
    /**
     * Obtiene todos los artículos especiales del sistema
     * @return Lista de todos los artículos especiales
     */
    public List<ArtEspeciales> obtenerTodosLosArtEspeciales();
    
    /**
     * Obtiene la cantidad total de libros
     * @return Número total de libros
     */
    public int obtenerCantidadLibros();
    
    /**
     * Obtiene la cantidad total de artículos especiales
     * @return Número total de artículos especiales
     */
    public int obtenerCantidadArtEspeciales();
    
    // ===== MÉTODOS DE CONSULTA DE DONACIONES =====
    
    /**
     * Obtiene todas las donaciones en un rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de materiales donados en el rango de fechas
     */
    public List<Material> obtenerDonacionesEnRango(DTFecha fechaInicio, DTFecha fechaFin);
    
    /**
     * Obtiene todas las donaciones de libros en un rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de libros donados en el rango de fechas
     */
    public List<Libro> obtenerDonacionesLibrosEnRango(DTFecha fechaInicio, DTFecha fechaFin);
    
    /**
     * Obtiene todas las donaciones de artículos especiales en un rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de artículos especiales donados en el rango de fechas
     */
    public List<ArtEspeciales> obtenerDonacionesArtEspecialesEnRango(DTFecha fechaInicio, DTFecha fechaFin);
    
    /**
     * Obtiene todas las donaciones (sin filtro de fechas)
     * @return Lista de todos los materiales donados
     */
    public List<Material> obtenerTodasLasDonaciones();
    
    /**
     * Obtiene todas las donaciones de libros (sin filtro de fechas)
     * @return Lista de todos los libros donados
     */
    public List<Libro> obtenerTodasLasDonacionesLibros();
    
    /**
     * Obtiene todas las donaciones de artículos especiales (sin filtro de fechas)
     * @return Lista de todos los artículos especiales donados
     */
    public List<ArtEspeciales> obtenerTodasLasDonacionesArtEspeciales();
    
    // ===== MÉTODOS DE GESTIÓN DE PRÉSTAMOS =====
    
    /**
     * Crea un nuevo préstamo asociando material, lector y bibliotecario
     * @param material Material a prestar
     * @param lector Lector que solicita el préstamo
     * @param bibliotecario Bibliotecario que gestiona el préstamo
     * @param fechaDevolucion Fecha de devolución del préstamo
     * @return ID del préstamo creado o null si falló
     */
    public Integer crearPrestamo(Material material, Lector lector, Bibliotecario bibliotecario, DTFecha fechaDevolucion);
    
    /**
     * Obtiene un préstamo por su ID
     * @param idPrestamo ID del préstamo
     * @return Préstamo encontrado o null si no existe
     */
    public Prestamo obtenerPrestamo(int idPrestamo);
    
    /**
     * Verifica si existe un préstamo con el ID dado
     * @param idPrestamo ID a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existePrestamo(int idPrestamo);
    
    /**
     * Edita un préstamo existente
     * @param idPrestamo ID del préstamo a editar
     * @param nuevaFechaDevolucion Nueva fecha de devolución
     * @return true si se editó exitosamente, false en caso contrario
     */
    public boolean editarPrestamo(int idPrestamo, DTFecha nuevaFechaDevolucion);
    
    /**
     * Actualiza el estado de un préstamo
     * @param idPrestamo ID del préstamo
     * @param nuevoEstado Nuevo estado del préstamo (EN CURSO, DEVUELTO, etc.)
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizarEstadoPrestamo(int idPrestamo, EstadoP nuevoEstado);
    
    /**
     * Elimina un préstamo del sistema
     * @param idPrestamo ID del préstamo a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminarPrestamo(int idPrestamo);
    
    /**
     * Obtiene el siguiente ID disponible para préstamos
     * @return Siguiente ID disponible
     */
    public int obtenerSiguienteIdPrestamo();
    
    /**
     * Obtiene la cantidad total de préstamos
     * @return Número total de préstamos
     */
    public int obtenerCantidadPrestamos();
    
    /**
     * Obtiene la lista de préstamos
     * @return Lista de préstamos
     */
    public List<Prestamo> getListaPrestamos();
    
    /**
     * Obtiene préstamos por lector
     * @param emailLector Email del lector
     * @return Lista de préstamos del lector
     */
    public List<Prestamo> obtenerPrestamosPorLector(String emailLector);
    
    /**
     * Obtiene préstamos por material
     * @param idMaterial ID del material
     * @return Lista de préstamos del material
     */
    public List<Prestamo> obtenerPrestamosPorMaterial(int idMaterial);
    
    /**
     * Obtiene préstamos por estado
     * @param estado Estado del préstamo
     * @return Lista de préstamos con el estado especificado
     */
    public List<Prestamo> obtenerPrestamosPorEstado(EstadoP estado);
    
    // ===== MÉTODOS DE ADMINISTRADOR PARA REPORTES =====
    
    /**
     * Obtiene el historial de préstamos gestionados por un bibliotecario específico
     * @param emailBibliotecario Email del bibliotecario
     * @return Lista de préstamos gestionados por el bibliotecario
     */
    public List<Prestamo> obtenerHistorialPrestamosPorBibliotecario(String emailBibliotecario);
    
    /**
     * Obtiene un reporte de préstamos por zona para análisis de uso
     * @return Lista de préstamos agrupados por zona
     */
    public List<Object[]> obtenerReportePrestamosPorZona();
    
    /**
     * Identifica materiales con muchos préstamos pendientes para priorizar devolución
     * @return Lista de materiales con cantidad de préstamos pendientes
     */
    public List<Object[]> obtenerMaterialesConPrestamosPendientes();
}
