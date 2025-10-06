package pap2025.Publicadores;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

/**
 * Servicio Web Simple para Biblioteca Comunitaria PAP2025
 * Versión funcional sin dependencias complejas
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ServicioWebSimple {
    
    private Endpoint endpoint;
    
    /**
     * Publica el servicio web en el puerto 8080
     */
    public void publicar() {
        try {
            endpoint = Endpoint.publish("http://localhost:8080/bibliotecaWS", this);
            System.out.println("=== SERVICIO WEB BIBLIOTECA COMUNITARIA ===");
            System.out.println("Servicio publicado exitosamente!");
            System.out.println("URL: http://localhost:8080/bibliotecaWS");
            System.out.println("WSDL: http://localhost:8080/bibliotecaWS?wsdl");
            System.out.println("==========================================");
        } catch (Exception e) {
            System.err.println("Error al publicar el servicio: " + e.getMessage());
        }
    }
    
    /**
     * Método de prueba para verificar que el servicio funciona
     */
    @WebMethod
    public String saludar(String nombre) {
        return "Hola " + nombre + "! Bienvenido al servicio web de Biblioteca Comunitaria PAP2025";
    }
    
    /**
     * Método de prueba que retorna información del servicio
     */
    @WebMethod
    public String obtenerInfoServicio() {
        return "Servicio Web Biblioteca Comunitaria - PAP2025 - Funcionando correctamente";
    }
    
    /**
     * Método de prueba que retorna true
     */
    @WebMethod
    public boolean testServicio() {
        return true;
    }
    
    /**
     * Método para obtener la versión del servicio
     */
    @WebMethod
    public String obtenerVersion() {
        return "1.0.0 - Servicio Web Biblioteca Comunitaria PAP2025";
    }
    
    /**
     * Método para simular autenticación de lector
     */
    @WebMethod
    public String autenticarLector(String email, String password) {
        // Simulación simple - en producción esto conectaría con la base de datos
        if ("lector@test.com".equals(email) && "123456".equals(password)) {
            return "Lector autenticado: " + email;
        }
        return "Error: Credenciales inválidas";
    }
    
    /**
     * Método para simular autenticación de bibliotecario
     */
    @WebMethod
    public String autenticarBibliotecario(String email, String password) {
        // Simulación simple - en producción esto conectaría con la base de datos
        if ("bibliotecario@test.com".equals(email) && "admin123".equals(password)) {
            return "Bibliotecario autenticado: " + email;
        }
        return "Error: Credenciales inválidas";
    }
    
    /**
     * Método para simular registro de libro
     */
    @WebMethod
    public String registrarLibro(String titulo, int cantidadPaginas) {
        return "Libro registrado: " + titulo + " (" + cantidadPaginas + " páginas)";
    }
    
    /**
     * Método para simular registro de artículo especial
     */
    @WebMethod
    public String registrarArticuloEspecial(String descripcion, double peso) {
        return "Artículo especial registrado: " + descripcion + " (peso: " + peso + " kg)";
    }
    
    /**
     * Método para simular creación de préstamo
     */
    @WebMethod
    public String crearPrestamo(String emailLector, int idMaterial, String emailBibliotecario) {
        return "Préstamo creado - Lector: " + emailLector + ", Material: " + idMaterial + ", Bibliotecario: " + emailBibliotecario;
    }
    
    /**
     * Método para obtener estadísticas del servicio
     */
    @WebMethod
    public String obtenerEstadisticas() {
        return "Estadísticas del servicio:\n" +
               "- Servicio activo: Sí\n" +
               "- Métodos disponibles: 12\n" +
               "- Estado: Funcionando correctamente\n" +
               "- Versión: 1.0.0";
    }
    
    /**
     * Obtiene el endpoint del servicio
     */
    public Endpoint getEndpoint() {
        return endpoint;
    }
}
