package pap2025.logica;

import pap2025.interfaz.IControladorFachada;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pap2025.persistencia.ConfiguracionBD;

/**
 * Controlador Fachada que coordina todos los manejadores del sistema
 * Contiene toda la lógica de negocio del sistema
 */
public class ControladorFachada implements IControladorFachada {
    
    private static ControladorFachada instancia;
    private ManejadorUsuario manejadorUsuario;
    private ManejadorMaterial manejadorMaterial;
    private ManejadorPrestamo manejadorPrestamo;
    
    // Constructor privado para Singleton
    private ControladorFachada() {
        this.manejadorUsuario = ManejadorUsuario.getInstancia();
        this.manejadorMaterial = ManejadorMaterial.getInstancia();
        this.manejadorPrestamo = ManejadorPrestamo.getInstancia();
    }
    
    // Método para obtener la instancia única (Singleton)
    public static ControladorFachada getInstancia() {
        if (instancia == null) {
            instancia = new ControladorFachada();
        }
        return instancia;
    }
    
    // Getters para acceder a los manejadores
    @Override
    public ManejadorUsuario getManejadorUsuario() {
        return manejadorUsuario;
    }
    
    @Override
    public ManejadorMaterial getManejadorMaterial() {
        return manejadorMaterial;
    }
    
    @Override
    public ManejadorPrestamo getManejadorPrestamo() {
        return manejadorPrestamo;
    }
    
    // ===== MÉTODOS DE GESTIÓN DE USUARIOS =====
    
    @Override
    public boolean crearUsuario(String nombre, String email) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return false;
        }
        
        // Verificar que no exista ya un usuario con ese email
        if (existeUsuario(email)) {
            return false;
        }
        
        // Crear el usuario
        Usuario usuario = new Usuario(nombre, email);
        manejadorUsuario.guardarUsuario(usuario);
        
        System.out.println("Usuario creado: " + usuario.getNombre() + " - " + usuario.getEmail());
        return true;
    }
    
    @Override
    public Usuario obtenerUsuario(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        return manejadorUsuario.obtenerUsuarioPorEmail(email);
    }
    
    @Override
    public boolean existeUsuario(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        return manejadorUsuario.obtenerUsuarioPorEmail(email) != null;
    }
    
    @Override
    public boolean editarUsuario(String email, String nuevoNombre) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return false;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            String nombreAnterior = usuario.getNombre();
            usuario.setNombre(nuevoNombre);
            manejadorUsuario.actualizarUsuario(usuario);
            System.out.println("Usuario editado: " + email + " - Nombre anterior: " + nombreAnterior + " - Nuevo nombre: " + nuevoNombre);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean eliminarUsuario(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            manejadorUsuario.eliminarUsuario(usuario);
            System.out.println("Usuario eliminado: " + email);
            return true;
        }
        return false;
    }
    
    @Override
    public int obtenerCantidadUsuarios() {
        return manejadorUsuario.getListaUsuarios().size();
    }
    
    @Override
    public List<Usuario> getListaUsuarios() {
        return manejadorUsuario.getListaUsuarios();
    }
    
    // ===== MÉTODOS DE GESTIÓN DE LECTORES =====
    
    @Override
    public boolean registrarLector(String nombre, String email, String direccion, DTFecha fechaRegistro, Zona zona) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return false;
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            return false;
        }
        if (fechaRegistro == null) {
            return false;
        }
        if (zona == null) {
            return false;
        }
        
        // Verificar que no exista ya un usuario con ese email
        if (existeUsuario(email)) {
            return false;
        }
        
        // Crear el lector con estado ACTIVO por defecto
        Lector lector = new Lector(nombre, email, direccion, fechaRegistro, EstadoL.ACTIVO, zona);
        manejadorUsuario.guardarUsuario(lector);
        
        System.out.println("Lector creado: " + lector.getNombre() + " - " + lector.getEmail() + " - Estado: " + lector.getEstadoL());
        return true;
    }
    
    @Override
    public Lector obtenerLector(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        if (usuario instanceof Lector) {
            return (Lector) usuario;
        }
        return null;
    }
    
    @Override
    public boolean existeLector(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        return usuario instanceof Lector;
    }
    
    @Override
    public boolean modificarEstadoLector(String email, EstadoL nuevoEstado) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("ERROR: Email no puede estar vacío.");
            return false;
        }
        
        if (nuevoEstado == null) {
            System.out.println("ERROR: El nuevo estado no puede ser null.");
            return false;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        if (usuario instanceof Lector) {
            Lector lector = (Lector) usuario;
            EstadoL estadoAnterior = lector.getEstadoL();
            lector.setEstadoL(nuevoEstado);
            manejadorUsuario.actualizarUsuario(lector);
            
            System.out.println("Estado del lector modificado por ADMINISTRADOR:");
            System.out.println("  - Email: " + email);
            System.out.println("  - Estado anterior: " + estadoAnterior);
            System.out.println("  - Nuevo estado: " + nuevoEstado);
            return true;
        }
        
        System.out.println("ERROR: No se encontró un lector con el email: " + email);
        return false;
    }
    
    @Override
    public boolean suspenderLector(String email) {
        return modificarEstadoLector(email, EstadoL.SUSPENDIDO);
    }
    
    @Override
    public boolean activarLector(String email) {
        return modificarEstadoLector(email, EstadoL.ACTIVO);
    }
    
    @Override
    public boolean lectorPuedePrestar(String email) {
        Lector lector = obtenerLector(email);
        if (lector == null) {
            return false;
        }
        return lector.getEstadoL() == EstadoL.ACTIVO;
    }
    
    @Override
    public boolean cambiarZonaLector(String email, Zona nuevaZona) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("ERROR: Email no puede estar vacío.");
            return false;
        }
        
        if (nuevaZona == null) {
            System.out.println("ERROR: La nueva zona no puede ser null.");
            return false;
        }
        
        for (Usuario usuario : manejadorUsuario.getListaUsuarios()) {
            if (usuario instanceof Lector && usuario.getEmail().equals(email)) {
                Lector lector = (Lector) usuario;
                Zona zonaAnterior = lector.getZona();
                lector.setZona(nuevaZona);
                
                // Persistir el cambio en la base de datos
                manejadorUsuario.actualizarUsuario(lector);
                
                System.out.println("Zona del lector modificada por ADMINISTRADOR:");
                System.out.println("  - Email: " + email);
                System.out.println("  - Zona anterior: " + zonaAnterior);
                System.out.println("  - Nueva zona: " + nuevaZona);
                return true;
            }
        }
        
        System.out.println("ERROR: No se encontró un lector con el email: " + email);
        return false;
    }
    
    @Override
    public List<Lector> obtenerTodosLosLectores() {
        List<Lector> lectores = new ArrayList<>();
        for (Usuario usuario : manejadorUsuario.getListaUsuarios()) {
            if (usuario instanceof Lector) {
                lectores.add((Lector) usuario);
            }
        }
        return lectores;
    }
    
    @Override
    public List<Lector> obtenerLectoresPorEstado(EstadoL estado) {
        List<Lector> resultados = new ArrayList<>();
        if (estado == null) {
            return resultados;
        }
        
        for (Usuario usuario : manejadorUsuario.getListaUsuarios()) {
            if (usuario instanceof Lector) {
                Lector lector = (Lector) usuario;
                if (lector.getEstadoL() == estado) {
                    resultados.add(lector);
                }
            }
        }
        
        return resultados;
    }
    
    @Override
    public List<Lector> obtenerLectoresSuspendidos() {
        return obtenerLectoresPorEstado(EstadoL.SUSPENDIDO);
    }
    
    @Override
    public List<Lector> obtenerLectoresActivos() {
        return obtenerLectoresPorEstado(EstadoL.ACTIVO);
    }
    
    @Override
    public int obtenerCantidadLectores() {
        int contador = 0;
        for (Usuario usuario : manejadorUsuario.getListaUsuarios()) {
            if (usuario instanceof Lector) {
                contador++;
            }
        }
        return contador;
    }
    
    @Override
    public int obtenerCantidadLectoresPorEstado(EstadoL estado) {
        if (estado == null) {
            return 0;
        }
        
        int contador = 0;
        for (Usuario usuario : manejadorUsuario.getListaUsuarios()) {
            if (usuario instanceof Lector) {
                Lector lector = (Lector) usuario;
                if (lector.getEstadoL() == estado) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    @Override
    public List<Lector> getListaLectores() {
        return obtenerTodosLosLectores();
    }
    
    // ===== MÉTODOS DE GESTIÓN DE BIBLIOTECARIOS =====
    
    @Override
    public boolean registrarBibliotecario(String nombre, String email, int nroEmpleado) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return false;
        }
        if (nroEmpleado <= 0) {
            return false;
        }
        
        // Verificar que no exista ya un usuario con ese email
        if (existeUsuario(email)) {
            return false;
        }
        
        // Verificar que no exista ya un bibliotecario con ese número de empleado
        if (existeBibliotecarioPorNroEmpleado(nroEmpleado)) {
            return false;
        }
        
        // Crear el bibliotecario
        Bibliotecario bibliotecario = new Bibliotecario(nombre, email, nroEmpleado);
        manejadorUsuario.guardarUsuario(bibliotecario);
        
        System.out.println("Bibliotecario creado: " + bibliotecario.getNombre() + " - " + bibliotecario.getEmail() + " - Nro. Empleado: " + bibliotecario.getNroEmpleado());
        return true;
    }
    
    @Override
    public Bibliotecario obtenerBibliotecario(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        if (usuario instanceof Bibliotecario) {
            return (Bibliotecario) usuario;
        }
        return null;
    }
    
    @Override
    public boolean existeBibliotecario(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        Usuario usuario = manejadorUsuario.obtenerUsuarioPorEmail(email);
        return usuario instanceof Bibliotecario;
    }
    
    @Override
    public boolean existeBibliotecarioPorNroEmpleado(int nroEmpleado) {
        if (nroEmpleado <= 0) {
            return false;
        }
        
        List<Bibliotecario> bibliotecarios = manejadorUsuario.obtenerTodosLosBibliotecarios();
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getNroEmpleado() == nroEmpleado) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public List<Bibliotecario> obtenerTodosLosBibliotecarios() {
        return manejadorUsuario.obtenerTodosLosBibliotecarios();
    }
    
    @Override
    public int obtenerCantidadBibliotecarios() {
        return manejadorUsuario.obtenerTodosLosBibliotecarios().size();
    }
    
    @Override
    public List<Bibliotecario> getListaBibliotecarios() {
        return obtenerTodosLosBibliotecarios();
    }
    
    // ===== MÉTODOS DE GESTIÓN DE MATERIALES =====
    
    @Override
    public Integer crearMaterial(DTFecha fechaIngreso) {
        if (fechaIngreso == null) {
            return null;
        }
        
        int id = obtenerSiguienteIdMaterial();
        Material material = new Material(id, fechaIngreso);
        manejadorMaterial.guardarMaterial(material);
        
        System.out.println("Material creado con ID: " + id + " - Fecha ingreso: " + fechaIngreso);
        return id;
    }
    
    @Override
    public Integer crearLibro(DTFecha fechaIngreso, String titulo, int cantPaginas) {
        if (fechaIngreso == null) {
            return null;
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            return null;
        }
        if (cantPaginas <= 0) {
            return null;
        }
        
        int id = obtenerSiguienteIdMaterial();
        Libro libro = new Libro(id, fechaIngreso, titulo, cantPaginas);
        manejadorMaterial.guardarMaterial(libro);
        
        System.out.println("Libro creado con ID: " + id + " - Título: " + titulo + " - Páginas: " + cantPaginas);
        return id;
    }
    
    @Override
    public Integer crearArtEspecial(DTFecha fechaIngreso, String descripcion, double peso, DTDimensiones dimensiones) {
        if (fechaIngreso == null) {
            return null;
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return null;
        }
        if (peso <= 0) {
            return null;
        }
        if (dimensiones == null) {
            return null;
        }
        
        int id = obtenerSiguienteIdMaterial();
        ArtEspeciales artEspecial = new ArtEspeciales(id, fechaIngreso, descripcion, peso, dimensiones);
        manejadorMaterial.guardarMaterial(artEspecial);
        
        System.out.println("Artículo especial creado con ID: " + id + " - Descripción: " + descripcion + " - Peso: " + peso);
        return id;
    }
    
    @Override
    public Material obtenerMaterial(int id) {
        return manejadorMaterial.obtenerMaterialPorId(id);
    }
    
    @Override
    public boolean existeMaterial(int id) {
        return manejadorMaterial.obtenerMaterialPorId(id) != null;
    }
    
    @Override
    public boolean editarMaterial(int id, DTFecha nuevaFechaIngreso) {
        if (nuevaFechaIngreso == null) {
            return false;
        }
        
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material.getId() == id) {
                material.setFechaIngreso(nuevaFechaIngreso);
                System.out.println("Material editado: " + id + " - Nueva fecha ingreso: " + nuevaFechaIngreso);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean eliminarMaterial(int id) {
        for (int i = 0; i < manejadorMaterial.getListaMateriales().size(); i++) {
            Material material = manejadorMaterial.getListaMateriales().get(i);
            if (material.getId() == id) {
                manejadorMaterial.getListaMateriales().remove(i);
                System.out.println("Material eliminado: " + id);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int obtenerSiguienteIdMaterial() {
        return manejadorMaterial.getListaMateriales().size() + 1;
    }
    
    @Override
    public int obtenerCantidadMateriales() {
        return manejadorMaterial.getListaMateriales().size();
    }
    
    @Override
    public List<Material> getListaMateriales() {
        return manejadorMaterial.getListaMateriales();
    }
    
    // ===== MÉTODOS DE GESTIÓN DE DONACIONES =====
    
    @Override
    public Integer registrarDonacionLibro(String titulo, int cantPaginas) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return null;
        }
        if (cantPaginas <= 0) {
            return null;
        }
        
        // Obtener el siguiente ID disponible
        int siguienteId = obtenerSiguienteIdMaterial();
        
        // Crear fecha de ingreso actual
        DTFecha fechaIngreso = new DTFecha(
            java.time.LocalDate.now().getDayOfMonth(),
            java.time.LocalDate.now().getMonthValue(),
            java.time.LocalDate.now().getYear()
        );
        
        // Crear el libro
        Libro libro = new Libro(siguienteId, fechaIngreso, titulo, cantPaginas);
        
        // Persistir en la base de datos
        manejadorMaterial.guardarMaterial(libro);
        
        System.out.println("Donación de libro registrada: " + titulo + " - " + cantPaginas + " páginas - ID: " + libro.getId());
        return libro.getId();
    }
    
    @Override
    public Integer registrarDonacionArtEspecial(String descripcion, double peso, DTDimensiones dimensiones) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return null;
        }
        if (peso <= 0) {
            return null;
        }
        if (dimensiones == null) {
            return null;
        }
        
        // Obtener el siguiente ID disponible
        int siguienteId = obtenerSiguienteIdMaterial();
        
        // Crear fecha de ingreso actual
        DTFecha fechaIngreso = new DTFecha(
            java.time.LocalDate.now().getDayOfMonth(),
            java.time.LocalDate.now().getMonthValue(),
            java.time.LocalDate.now().getYear()
        );
        
        // Crear el artículo especial
        ArtEspeciales artEspecial = new ArtEspeciales(siguienteId, fechaIngreso, descripcion, peso, dimensiones);
        
        // Persistir en la base de datos
        manejadorMaterial.guardarMaterial(artEspecial);
        
        System.out.println("Donación de artículo especial registrada: " + descripcion + " - " + peso + " kg - ID: " + artEspecial.getId());
        return artEspecial.getId();
    }
    
    @Override
    public List<Libro> obtenerTodosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof Libro) {
                libros.add((Libro) material);
            }
        }
        return libros;
    }
    
    @Override
    public List<ArtEspeciales> obtenerTodosLosArtEspeciales() {
        List<ArtEspeciales> artEspeciales = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof ArtEspeciales) {
                artEspeciales.add((ArtEspeciales) material);
            }
        }
        return artEspeciales;
    }
    
    @Override
    public int obtenerCantidadLibros() {
        int contador = 0;
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof Libro) {
                contador++;
            }
        }
        return contador;
    }
    
    @Override
    public int obtenerCantidadArtEspeciales() {
        int contador = 0;
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof ArtEspeciales) {
                contador++;
            }
        }
        return contador;
    }
    
    // ===== MÉTODOS DE CONSULTA DE DONACIONES =====
    
    @Override
    public List<Material> obtenerDonacionesEnRango(DTFecha fechaInicio, DTFecha fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return new ArrayList<>();
        }
        
        List<Material> donacionesEnRango = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material.getFechaIngreso() != null) {
                if (fechaEnRango(material.getFechaIngreso(), fechaInicio, fechaFin)) {
                    donacionesEnRango.add(material);
                }
            }
        }
        return donacionesEnRango;
    }
    
    @Override
    public List<Libro> obtenerDonacionesLibrosEnRango(DTFecha fechaInicio, DTFecha fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return new ArrayList<>();
        }
        
        List<Libro> librosEnRango = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof Libro && material.getFechaIngreso() != null) {
                if (fechaEnRango(material.getFechaIngreso(), fechaInicio, fechaFin)) {
                    librosEnRango.add((Libro) material);
                }
            }
        }
        return librosEnRango;
    }
    
    @Override
    public List<ArtEspeciales> obtenerDonacionesArtEspecialesEnRango(DTFecha fechaInicio, DTFecha fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return new ArrayList<>();
        }
        
        List<ArtEspeciales> artEspecialesEnRango = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof ArtEspeciales && material.getFechaIngreso() != null) {
                if (fechaEnRango(material.getFechaIngreso(), fechaInicio, fechaFin)) {
                    artEspecialesEnRango.add((ArtEspeciales) material);
                }
            }
        }
        return artEspecialesEnRango;
    }
    
    @Override
    public List<Material> obtenerTodasLasDonaciones() {
        return new ArrayList<>(manejadorMaterial.getListaMateriales());
    }
    
    @Override
    public List<Libro> obtenerTodasLasDonacionesLibros() {
        List<Libro> todosLosLibros = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof Libro) {
                todosLosLibros.add((Libro) material);
            }
        }
        return todosLosLibros;
    }
    
    @Override
    public List<ArtEspeciales> obtenerTodasLasDonacionesArtEspeciales() {
        List<ArtEspeciales> todosLosArtEspeciales = new ArrayList<>();
        for (Material material : manejadorMaterial.getListaMateriales()) {
            if (material instanceof ArtEspeciales) {
                todosLosArtEspeciales.add((ArtEspeciales) material);
            }
        }
        return todosLosArtEspeciales;
    }
    
    /**
     * Método auxiliar para verificar si una fecha está en un rango
     * @param fecha Fecha a verificar
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return true si la fecha está en el rango, false en caso contrario
     */
    private boolean fechaEnRango(DTFecha fecha, DTFecha fechaInicio, DTFecha fechaFin) {
        if (fecha == null || fechaInicio == null || fechaFin == null) {
            return false;
        }
        
        // Convertir a días desde una fecha base para comparación
        int diasFecha = fecha.getAnio() * 365 + fecha.getMes() * 30 + fecha.getDia();
        int diasInicio = fechaInicio.getAnio() * 365 + fechaInicio.getMes() * 30 + fechaInicio.getDia();
        int diasFin = fechaFin.getAnio() * 365 + fechaFin.getMes() * 30 + fechaFin.getDia();
        
        return diasFecha >= diasInicio && diasFecha <= diasFin;
    }
    
    // ===== MÉTODOS DE GESTIÓN DE PRÉSTAMOS =====
    
    /**
     * Verifica si un material está disponible para préstamo
     * Un material está disponible si no tiene préstamos activos (PENDIENTE o EN_CURSO)
     */
    public boolean estaMaterialDisponible(Material material) {
        if (material == null) {
            return false;
        }
        
        List<Prestamo> prestamos = manejadorPrestamo.getListaPrestamos();
        for (Prestamo prestamo : prestamos) {
            // Verificar si el material está en un préstamo activo
            if (prestamo.getMaterial().getId() == material.getId() && 
                (prestamo.getEstadoP() == EstadoP.PENDIENTE || prestamo.getEstadoP() == EstadoP.ENCURSO)) {
                return false; // El material no está disponible
            }
        }
        return true; // El material está disponible
    }
    
    /**
     * Obtiene una lista de materiales disponibles para préstamo
     */
    public List<Material> obtenerMaterialesDisponibles() {
        List<Material> todosLosMateriales = manejadorMaterial.getListaMateriales();
        List<Material> materialesDisponibles = new ArrayList<>();
        
        for (Material material : todosLosMateriales) {
            if (estaMaterialDisponible(material)) {
                materialesDisponibles.add(material);
            }
        }
        
        return materialesDisponibles;
    }
    
    @Override
    public Integer crearPrestamo(Material material, Lector lector, Bibliotecario bibliotecario, DTFecha fechaDevolucion) {
        if (material == null || lector == null || bibliotecario == null || fechaDevolucion == null) {
            return null;
        }
        
        // Verificar que el lector esté activo
        if (lector.getEstadoL() != EstadoL.ACTIVO) {
            System.out.println("Error: El lector " + lector.getNombre() + " no puede realizar préstamos (Estado: " + lector.getEstadoL() + ")");
            return null;
        }
        
        // Verificar que el material esté disponible
        if (!estaMaterialDisponible(material)) {
            System.out.println("❌ Error: El material no está disponible para préstamo");
            System.out.println("   📚 Material: " + (material instanceof Libro ? ((Libro) material).getTitulo() : 
                                                material instanceof ArtEspeciales ? ((ArtEspeciales) material).getDescripcion() : "Material ID: " + material.getId()));
            System.out.println("   ⚠️  El material ya está siendo utilizado en otro préstamo activo");
            return null;
        }
        
        int idPrestamo = obtenerSiguienteIdPrestamo();
        DTFecha fechaSolicitada = new DTFecha(java.time.LocalDate.now().getDayOfMonth(), 
                                             java.time.LocalDate.now().getMonthValue(), 
                                             java.time.LocalDate.now().getYear());
        
        Prestamo prestamo = new Prestamo(idPrestamo, material, lector, bibliotecario, 
                                        fechaSolicitada, fechaDevolucion, EstadoP.ENCURSO);
        manejadorPrestamo.guardarPrestamo(prestamo);
        
        System.out.println("✅ Préstamo creado exitosamente:");
        System.out.println("   📚 Material: " + (material instanceof Libro ? ((Libro) material).getTitulo() : 
                                                material instanceof ArtEspeciales ? ((ArtEspeciales) material).getDescripcion() : "Material ID: " + material.getId()));
        System.out.println("   👤 Lector: " + lector.getNombre() + " (" + lector.getEmail() + ")");
        System.out.println("   🏢 Bibliotecario: " + bibliotecario.getNombre() + " (Nro. Empleado: " + bibliotecario.getNroEmpleado() + ")");
        System.out.println("   📅 Fecha Solicitud: " + fechaSolicitada);
        System.out.println("   📅 Fecha Devolución: " + fechaDevolucion);
        System.out.println("   🆔 ID Préstamo: " + idPrestamo);
        
        return idPrestamo;
    }
    
    @Override
    public Prestamo obtenerPrestamo(int idPrestamo) {
        return manejadorPrestamo.obtenerPrestamoPorId(idPrestamo);
    }
    
    @Override
    public boolean existePrestamo(int idPrestamo) {
        return manejadorPrestamo.obtenerPrestamoPorId(idPrestamo) != null;
    }
    
    @Override
    public boolean editarPrestamo(int idPrestamo, DTFecha nuevaFechaDevolucion) {
        if (nuevaFechaDevolucion == null) {
            return false;
        }
        
        Prestamo prestamo = manejadorPrestamo.obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            return false;
        }
        
        DTFecha fechaAnterior = prestamo.getFechaDevolucion();
        prestamo.setFechaDevolucion(nuevaFechaDevolucion);
        manejadorPrestamo.actualizarPrestamo(prestamo);
        
        System.out.println("✅ Fecha de devolución del préstamo actualizada:");
        System.out.println("   🆔 ID Préstamo: " + idPrestamo);
        System.out.println("   📅 Fecha anterior: " + fechaAnterior);
        System.out.println("   📅 Nueva fecha: " + nuevaFechaDevolucion);
        
        return true;
    }
    
    @Override
    public boolean actualizarEstadoPrestamo(int idPrestamo, EstadoP nuevoEstado) {
        if (nuevoEstado == null) {
            return false;
        }
        
        Prestamo prestamo = manejadorPrestamo.obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.out.println("❌ Error: No se encontró el préstamo con ID: " + idPrestamo);
            return false;
        }
        
        EstadoP estadoAnterior = prestamo.getEstadoP();
        prestamo.setEstadoP(nuevoEstado);
        manejadorPrestamo.actualizarPrestamo(prestamo);
        
        System.out.println("✅ Estado del préstamo actualizado exitosamente:");
        System.out.println("   🆔 ID Préstamo: " + idPrestamo);
        System.out.println("   📚 Material: Material ID: " + prestamo.getMaterial().getId());
        System.out.println("   🔄 Estado anterior: " + estadoAnterior);
        System.out.println("   🔄 Nuevo estado: " + nuevoEstado);
        
        return true;
    }
    
    @Override
    public boolean actualizarPrestamoCompleto(int idPrestamo, Material nuevoMaterial, Lector nuevoLector, 
                                            Bibliotecario nuevoBibliotecario, EstadoP nuevoEstado, 
                                            DTFecha nuevaFechaSolicitud, DTFecha nuevaFechaDevolucion) {
        if (nuevoMaterial == null || nuevoLector == null || nuevoBibliotecario == null || 
            nuevoEstado == null || nuevaFechaSolicitud == null || nuevaFechaDevolucion == null) {
            System.out.println("❌ Error: Todos los parámetros son obligatorios");
            return false;
        }
        
        Prestamo prestamo = manejadorPrestamo.obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.out.println("❌ Error: No se encontró el préstamo con ID: " + idPrestamo);
            return false;
        }
        
        // Guardar valores anteriores para logging
        Material materialAnterior = prestamo.getMaterial();
        Lector lectorAnterior = prestamo.getLector();
        Bibliotecario bibliotecarioAnterior = prestamo.getBibliotecario();
        EstadoP estadoAnterior = prestamo.getEstadoP();
        DTFecha fechaSolicitudAnterior = prestamo.getFechaSolicitada();
        DTFecha fechaDevolucionAnterior = prestamo.getFechaDevolucion();
        
        // Actualizar todos los campos
        prestamo.setMaterial(nuevoMaterial);
        prestamo.setLector(nuevoLector);
        prestamo.setBibliotecario(nuevoBibliotecario);
        prestamo.setEstadoP(nuevoEstado);
        prestamo.setFechaSolicitada(nuevaFechaSolicitud);
        prestamo.setFechaDevolucion(nuevaFechaDevolucion);
        
        // Persistir los cambios
        manejadorPrestamo.actualizarPrestamo(prestamo);
        
        System.out.println("✅ Préstamo actualizado exitosamente:");
        System.out.println("   🆔 ID: " + idPrestamo);
        System.out.println("   📚 Material: " + (nuevoMaterial instanceof Libro ? ((Libro) nuevoMaterial).getTitulo() : 
                                                nuevoMaterial instanceof ArtEspeciales ? ((ArtEspeciales) nuevoMaterial).getDescripcion() : "Material ID: " + nuevoMaterial.getId()));
        System.out.println("   👤 Lector: " + nuevoLector.getEmail());
        System.out.println("   🏢 Bibliotecario: " + nuevoBibliotecario.getNroEmpleado());
        System.out.println("   📊 Estado: " + nuevoEstado);
        System.out.println("   📅 Fecha Solicitud: " + formatearFecha(nuevaFechaSolicitud));
        System.out.println("   📅 Fecha Devolución: " + formatearFecha(nuevaFechaDevolucion));
        
        return true;
    }
    
    private String formatearFecha(DTFecha fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return fecha.getDia() + "/" + fecha.getMes() + "/" + fecha.getAnio();
    }
    
    @Override
    public boolean eliminarPrestamo(int idPrestamo) {
        Prestamo prestamo = obtenerPrestamo(idPrestamo);
        if (prestamo == null) {
            return false;
        }
        
        boolean eliminado = manejadorPrestamo.getListaPrestamos().remove(prestamo);
        if (eliminado) {
            System.out.println("✅ Préstamo eliminado exitosamente:");
            System.out.println("   🆔 ID Préstamo: " + idPrestamo);
            System.out.println("   📚 Material: " + (prestamo.getMaterial() instanceof Libro ? ((Libro) prestamo.getMaterial()).getTitulo() : 
                                                prestamo.getMaterial() instanceof ArtEspeciales ? ((ArtEspeciales) prestamo.getMaterial()).getDescripcion() : "Material ID: " + prestamo.getMaterial().getId()));
        }
        
        return eliminado;
    }
    
    @Override
    public int obtenerSiguienteIdPrestamo() {
        return manejadorPrestamo.getListaPrestamos().size() + 1;
    }
    
    @Override
    public int obtenerCantidadPrestamos() {
        return manejadorPrestamo.getListaPrestamos().size();
    }
    
    @Override
    public List<Prestamo> getListaPrestamos() {
        return manejadorPrestamo.getListaPrestamos();
    }
    
    @Override
    public List<Prestamo> obtenerPrestamosPorLector(String emailLector) {
        List<Prestamo> prestamosLector = new ArrayList<>();
        for (Prestamo prestamo : manejadorPrestamo.getListaPrestamos()) {
            if (prestamo.getLector().getEmail().equals(emailLector)) {
                prestamosLector.add(prestamo);
            }
        }
        return prestamosLector;
    }
    
    @Override
    public List<Prestamo> obtenerPrestamosPorMaterial(int idMaterial) {
        List<Prestamo> prestamosMaterial = new ArrayList<>();
        for (Prestamo prestamo : manejadorPrestamo.getListaPrestamos()) {
            if (prestamo.getMaterial().getId() == idMaterial) {
                prestamosMaterial.add(prestamo);
            }
        }
        return prestamosMaterial;
    }
    
    @Override
    public List<Prestamo> obtenerPrestamosPorEstado(EstadoP estado) {
        List<Prestamo> prestamosEstado = new ArrayList<>();
        for (Prestamo prestamo : manejadorPrestamo.getListaPrestamos()) {
            if (prestamo.getEstadoP() == estado) {
                prestamosEstado.add(prestamo);
            }
        }
        return prestamosEstado;
    }
    
    // ===== MÉTODOS DE ADMINISTRADOR PARA REPORTES =====
    
    @Override
    public List<Prestamo> obtenerHistorialPrestamosPorBibliotecario(String emailBibliotecario) {
        if (emailBibliotecario == null || emailBibliotecario.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Prestamo> historialBibliotecario = new ArrayList<>();
        for (Prestamo prestamo : manejadorPrestamo.getListaPrestamos()) {
            // Evitar LazyInitializationException usando solo el ID
            if (prestamo.getBibliotecario().getId() != null) {
                historialBibliotecario.add(prestamo);
            }
        }
        
        System.out.println("📊 Historial de préstamos del bibliotecario " + emailBibliotecario + ": " + historialBibliotecario.size() + " préstamos");
        return historialBibliotecario;
    }
    
    @Override
    public List<Object[]> obtenerReportePrestamosPorZona() {
        List<Object[]> reportePorZona = new ArrayList<>();
        
        // Obtener todas las zonas
        Zona[] zonas = Zona.values();
        
        for (Zona zona : zonas) {
            int totalPrestamos = 0;
            int prestamosEnCurso = 0;
            int prestamosDevueltos = 0;
            int prestamosPendientes = 0;
            
            // Usar consulta SQL directa para evitar LazyInitializationException
            try {
                // Consulta SQL para obtener préstamos por zona específica
                String sql = "SELECT p.estado, COUNT(*) as cantidad " +
                           "FROM prestamos p " +
                           "INNER JOIN lectores l ON p.lector_id = l.usuario_id " +
                           "WHERE l.zona = ? " +
                           "GROUP BY p.estado";
                
                // Ejecutar consulta SQL nativa
                Session session = ConfiguracionBD.getSessionFactory().openSession();
                try {
                    Query query = session.createNativeQuery(sql);
                    query.setParameter(1, zona.name());
                    List<Object[]> resultados = query.getResultList();
                    
                    for (Object[] resultado : resultados) {
                        String estado = (String) resultado[0];
                        Long cantidad = ((Number) resultado[1]).longValue();
                        
                        totalPrestamos += cantidad.intValue();
                        
                        switch (estado) {
                            case "ENCURSO":
                                prestamosEnCurso = cantidad.intValue();
                                break;
                            case "DEVUELTO":
                                prestamosDevueltos = cantidad.intValue();
                                break;
                            case "PENDIENTE":
                                prestamosPendientes = cantidad.intValue();
                                break;
                        }
                    }
                } finally {
                    session.close();
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error al consultar préstamos por zona " + zona + ": " + e.getMessage());
            }
            
            if (totalPrestamos > 0) {
                Object[] fila = {zona, totalPrestamos, prestamosEnCurso, prestamosDevueltos, prestamosPendientes};
                reportePorZona.add(fila);
            }
        }
        
        System.out.println("📊 Reporte de préstamos por zona generado: " + reportePorZona.size() + " zonas con actividad");
        return reportePorZona;
    }
    
    @Override
    public List<Object[]> obtenerMaterialesConPrestamosPendientes() {
        List<Object[]> materialesPendientes = new ArrayList<>();
        
        // Contar préstamos pendientes por material
        for (Material material : manejadorMaterial.getListaMateriales()) {
            int prestamosPendientes = 0;
            int prestamosEnCurso = 0;
            
            for (Prestamo prestamo : manejadorPrestamo.getListaPrestamos()) {
                if (prestamo.getMaterial().getId() == material.getId()) {
                    if (prestamo.getEstadoP() == EstadoP.PENDIENTE) {
                        prestamosPendientes++;
                    } else if (prestamo.getEstadoP() == EstadoP.ENCURSO) {
                        prestamosEnCurso++;
                    }
                }
            }
            
            if (prestamosPendientes > 0 || prestamosEnCurso > 0) {
                String nombreMaterial;
                if (material instanceof Libro) {
                    nombreMaterial = ((Libro) material).getTitulo();
                } else if (material instanceof ArtEspeciales) {
                    nombreMaterial = ((ArtEspeciales) material).getDescripcion();
                } else {
                    nombreMaterial = "Material ID: " + material.getId();
                }
                
                Object[] fila = {material.getId(), nombreMaterial, prestamosPendientes, prestamosEnCurso, 
                                prestamosPendientes + prestamosEnCurso};
                materialesPendientes.add(fila);
            }
        }
        
        // Ordenar por cantidad de préstamos pendientes descendente (más pendientes primero)
        materialesPendientes.sort((a, b) -> {
            int pendientesA = (Integer) a[2]; // Columna 2: prestamosPendientes
            int pendientesB = (Integer) b[2]; // Columna 2: prestamosPendientes
            
            // Si tienen la misma cantidad de pendientes, ordenar por total descendente
            if (pendientesA == pendientesB) {
                int totalA = (Integer) a[4]; // Columna 4: total
                int totalB = (Integer) b[4]; // Columna 4: total
                return Integer.compare(totalB, totalA);
            }
            
            // Ordenar por pendientes descendente (más pendientes primero)
            return Integer.compare(pendientesB, pendientesA);
        });
        
        System.out.println("📊 Materiales con préstamos pendientes identificados: " + materialesPendientes.size() + " materiales");
        return materialesPendientes;
    }
}
