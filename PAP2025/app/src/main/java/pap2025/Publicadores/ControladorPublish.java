package pap2025.Publicadores;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;
import pap2025.datatypes.DTLector;
import pap2025.datatypes.DTBibliotecario;
import pap2025.datatypes.DTMaterial;
import pap2025.datatypes.DTPrestamo;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;
import pap2025.logica.Zona;
import pap2025.logica.EstadoL;
import pap2025.logica.EstadoP;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ControladorPublish {
    private Fabrica fabrica;
    private IControladorFachada controladorFachada;
    private WebServiceConfiguracion configuracion;
    private Endpoint endpoint;

    public ControladorPublish() {
        fabrica = Fabrica.getInstancia();
        controladorFachada = fabrica.getIControladorFachada();
        try {
            configuracion = new WebServiceConfiguracion();
        } catch (Exception ex) {
            System.err.println("Error al cargar configuración: " + ex.getMessage());
        }
    }

    @WebMethod(exclude = true)
    public void publicar() {
        String ip = configuracion.getConfigOf("WS_IP", "localhost");
        String puerto = configuracion.getConfigOf("WS_PORT", "8080");
        String contexto = configuracion.getConfigOf("WS_CONTEXT", "bibliotecaWS");
        
        endpoint = Endpoint.publish("http://" + ip + ":" + puerto + "/" + contexto, this);
        System.out.println("🌐 Servicio web publicado en: http://" + ip + ":" + puerto + "/" + contexto);
        configuracion.imprimirConfiguracion();
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }
    
    // ===== MÉTODOS DE AUTENTICACIÓN =====
    
    @WebMethod
    public DTLector autenticarLector(String email, String password) {
        // Implementar autenticación - por ahora retornar null
        // TODO: Implementar autenticación real
        return null;
    }
    
    @WebMethod
    public DTBibliotecario autenticarBibliotecario(String email, String password) {
        // Implementar autenticación - por ahora retornar null
        // TODO: Implementar autenticación real
        return null;
    }
    
    // ===== MÉTODOS DE REGISTRO DE USUARIOS =====
    
    @WebMethod
    public boolean registrarLector(String nombre, String email, String password, 
                                  String direccion, DTFecha fechaRegistro, Zona zona) {
        return controladorFachada.registrarLector(nombre, email, direccion, fechaRegistro, zona);
    }
    
    @WebMethod
    public boolean registrarBibliotecario(String nombre, String email, String password, int nroEmpleado) {
        return controladorFachada.registrarBibliotecario(nombre, email, nroEmpleado);
    }
    
    // ===== MÉTODOS DE GESTIÓN DE USUARIOS =====
    
    @WebMethod
    public void modificarEstadoLector(String email, String nuevoEstado) {
        EstadoL estado = EstadoL.valueOf(nuevoEstado);
        controladorFachada.modificarEstadoLector(email, estado);
    }
    
    @WebMethod
    public void cambiarZonaLector(String email, Zona nuevaZona) {
        controladorFachada.cambiarZonaLector(email, nuevaZona);
    }
    
    @WebMethod
    public boolean cambiarPassword(String email, String passwordActual, String passwordNueva) {
        // TODO: Implementar cambio de password
        return false;
    }
    
    // ===== MÉTODOS DE GESTIÓN DE MATERIALES =====
    
    @WebMethod
    public boolean registrarLibro(String titulo, int cantidadPaginas) {
        DTFecha fechaActual = new DTFecha(); // Fecha actual
        Integer id = controladorFachada.crearLibro(fechaActual, titulo, cantidadPaginas);
        return id != null;
    }
    
    @WebMethod
    public boolean registrarArticuloEspecial(String descripcion, double peso, DTDimensiones dimensiones) {
        DTFecha fechaActual = new DTFecha(); // Fecha actual
        Integer id = controladorFachada.crearArtEspecial(fechaActual, descripcion, peso, dimensiones);
        return id != null;
    }
    
    @WebMethod
    public DTMaterial[] consultarDonaciones() {
        List<Material> materiales = controladorFachada.obtenerTodosLosMateriales();
        DTMaterial[] dtMateriales = new DTMaterial[materiales.size()];
        for (int i = 0; i < materiales.size(); i++) {
            Material m = materiales.get(i);
            if (m instanceof Libro) {
                Libro libro = (Libro) m;
                dtMateriales[i] = new DTMaterial(m.getId(), libro.getTitulo(), 
                    new DTFecha(m.getFechaIngreso()), libro.getCantPaginas());
            } else if (m instanceof ArtEspeciales) {
                ArtEspeciales art = (ArtEspeciales) m;
                dtMateriales[i] = new DTMaterial(m.getId(), art.getDescripcion(), 
                    new DTFecha(m.getFechaIngreso()), art.getPeso(), art.getDimensiones());
            }
        }
        return dtMateriales;
    }
    
    @WebMethod
    public DTMaterial[] consultarDonacionesPorFecha(DTFecha fechaInicio, DTFecha fechaFin) {
        // TODO: Implementar consulta por fecha
        return new DTMaterial[0];
    }
    
    // ===== MÉTODOS DE GESTIÓN DE PRÉSTAMOS =====
    
    @WebMethod
    public boolean crearPrestamo(String emailLector, int idMaterial, String emailBibliotecario, DTFecha fechaPrestamo) {
        Material material = controladorFachada.obtenerMaterial(idMaterial);
        if (material == null) return false;
        
        Integer id = controladorFachada.crearPrestamo(emailLector, material, emailBibliotecario, fechaPrestamo);
        return id != null;
    }
    
    @WebMethod
    public void actualizarEstadoPrestamo(int idPrestamo, String nuevoEstado) {
        EstadoP estado = EstadoP.valueOf(nuevoEstado);
        controladorFachada.actualizarEstadoPrestamo(idPrestamo, estado);
    }
    
    @WebMethod
    public DTPrestamo[] obtenerPrestamosPorLector(String emailLector) {
        List<Prestamo> prestamos = controladorFachada.obtenerPrestamosPorLector(emailLector);
        DTPrestamo[] dtPrestamos = new DTPrestamo[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            dtPrestamos[i] = new DTPrestamo(p.getId(), p.getLector().getEmail(), 
                p.getLector().getNombre(), p.getMaterial().getId(), 
                p.getMaterial().getTitulo(), p.getBibliotecario().getEmail(),
                p.getBibliotecario().getNombre(), new DTFecha(p.getFechaPrestamo()),
                new DTFecha(p.getFechaVencimiento()), p.getEstadoP());
        }
        return dtPrestamos;
    }
    
    @WebMethod
    public DTPrestamo[] obtenerPrestamosActivosPorLector(String emailLector) {
        List<Prestamo> prestamos = controladorFachada.obtenerPrestamosActivosPorLector(emailLector);
        DTPrestamo[] dtPrestamos = new DTPrestamo[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            dtPrestamos[i] = new DTPrestamo(p.getId(), p.getLector().getEmail(), 
                p.getLector().getNombre(), p.getMaterial().getId(), 
                p.getMaterial().getTitulo(), p.getBibliotecario().getEmail(),
                p.getBibliotecario().getNombre(), new DTFecha(p.getFechaPrestamo()),
                new DTFecha(p.getFechaVencimiento()), p.getEstadoP());
        }
        return dtPrestamos;
    }
    
    @WebMethod
    public DTPrestamo[] obtenerPrestamosPorEstado(String estado) {
        EstadoP estadoEnum = EstadoP.valueOf(estado);
        List<Prestamo> prestamos = controladorFachada.obtenerPrestamosPorEstado(estadoEnum);
        DTPrestamo[] dtPrestamos = new DTPrestamo[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            dtPrestamos[i] = new DTPrestamo(p.getId(), p.getLector().getEmail(), 
                p.getLector().getNombre(), p.getMaterial().getId(), 
                p.getMaterial().getTitulo(), p.getBibliotecario().getEmail(),
                p.getBibliotecario().getNombre(), new DTFecha(p.getFechaPrestamo()),
                new DTFecha(p.getFechaVencimiento()), p.getEstadoP());
        }
        return dtPrestamos;
    }
    
    @WebMethod
    public boolean editarInformacionPrestamo(int idPrestamo, DTFecha fechaPrestamo, DTFecha fechaVencimiento) {
        return controladorFachada.editarInformacionPrestamo(idPrestamo, fechaPrestamo, fechaVencimiento);
    }
    
    // ===== MÉTODOS DE CONSULTAS Y REPORTES =====
    
    @WebMethod
    public DTPrestamo[] obtenerHistorialPrestamosBibliotecario(String emailBibliotecario) {
        List<Prestamo> prestamos = controladorFachada.obtenerHistorialPrestamosBibliotecario(emailBibliotecario);
        DTPrestamo[] dtPrestamos = new DTPrestamo[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            dtPrestamos[i] = new DTPrestamo(p.getId(), p.getLector().getEmail(), 
                p.getLector().getNombre(), p.getMaterial().getId(), 
                p.getMaterial().getTitulo(), p.getBibliotecario().getEmail(),
                p.getBibliotecario().getNombre(), new DTFecha(p.getFechaPrestamo()),
                new DTFecha(p.getFechaVencimiento()), p.getEstadoP());
        }
        return dtPrestamos;
    }
    
    @WebMethod
    public DTPrestamo[] obtenerReportePrestamosPorZona(Zona zona) {
        List<Prestamo> prestamos = controladorFachada.obtenerReportePrestamosPorZona();
        DTPrestamo[] dtPrestamos = new DTPrestamo[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo p = prestamos.get(i);
            dtPrestamos[i] = new DTPrestamo(p.getId(), p.getLector().getEmail(), 
                p.getLector().getNombre(), p.getMaterial().getId(), 
                p.getMaterial().getTitulo(), p.getBibliotecario().getEmail(),
                p.getBibliotecario().getNombre(), new DTFecha(p.getFechaPrestamo()),
                new DTFecha(p.getFechaVencimiento()), p.getEstadoP());
        }
        return dtPrestamos;
    }
    
    @WebMethod
    public DTMaterial[] obtenerMaterialesConPrestamosPendientes() {
        List<Material> materiales = controladorFachada.obtenerMaterialesConPrestamosPendientes();
        DTMaterial[] dtMateriales = new DTMaterial[materiales.size()];
        for (int i = 0; i < materiales.size(); i++) {
            Material m = materiales.get(i);
            if (m instanceof Libro) {
                Libro libro = (Libro) m;
                dtMateriales[i] = new DTMaterial(m.getId(), libro.getTitulo(), 
                    new DTFecha(m.getFechaIngreso()), libro.getCantPaginas());
            } else if (m instanceof ArtEspeciales) {
                ArtEspeciales art = (ArtEspeciales) m;
                dtMateriales[i] = new DTMaterial(m.getId(), art.getDescripcion(), 
                    new DTFecha(m.getFechaIngreso()), art.getPeso(), art.getDimensiones());
            }
        }
        return dtMateriales;
    }
    
    // ===== MÉTODOS DE UTILIDAD =====
    
    @WebMethod
    public boolean existeUsuario(String email) {
        return controladorFachada.existeUsuario(email);
    }
    
    @WebMethod
    public DTLector obtenerLector(String email) {
        Lector lector = controladorFachada.obtenerLector(email);
        if (lector == null) return null;
        
        return new DTLector(lector.getId(), lector.getNombre(), lector.getEmail(),
            lector.getDireccion(), new DTFecha(lector.getFechaRegistro()),
            lector.getZona(), lector.getEstadoL());
    }
    
    @WebMethod
    public DTBibliotecario obtenerBibliotecario(String email) {
        Bibliotecario bibliotecario = controladorFachada.obtenerBibliotecario(email);
        if (bibliotecario == null) return null;
        
        return new DTBibliotecario(bibliotecario.getId(), bibliotecario.getNombre(),
            bibliotecario.getEmail(), bibliotecario.getNroEmpleado());
    }
    
    @WebMethod
    public DTMaterial obtenerMaterial(int idMaterial) {
        Material material = controladorFachada.obtenerMaterial(idMaterial);
        if (material == null) return null;
        
        if (material instanceof Libro) {
            Libro libro = (Libro) material;
            return new DTMaterial(material.getId(), libro.getTitulo(), 
                new DTFecha(material.getFechaIngreso()), libro.getCantPaginas());
        } else if (material instanceof ArtEspeciales) {
            ArtEspeciales art = (ArtEspeciales) material;
            return new DTMaterial(material.getId(), art.getDescripcion(), 
                new DTFecha(material.getFechaIngreso()), art.getPeso(), art.getDimensiones());
        }
        return null;
    }
    
    @WebMethod
    public DTPrestamo obtenerPrestamo(int idPrestamo) {
        Prestamo prestamo = controladorFachada.obtenerPrestamo(idPrestamo);
        if (prestamo == null) return null;
        
        return new DTPrestamo(prestamo.getId(), prestamo.getLector().getEmail(),
            prestamo.getLector().getNombre(), prestamo.getMaterial().getId(),
            prestamo.getMaterial().getTitulo(), prestamo.getBibliotecario().getEmail(),
            prestamo.getBibliotecario().getNombre(), new DTFecha(prestamo.getFechaPrestamo()),
            new DTFecha(prestamo.getFechaVencimiento()), prestamo.getEstadoP());
    }
}
