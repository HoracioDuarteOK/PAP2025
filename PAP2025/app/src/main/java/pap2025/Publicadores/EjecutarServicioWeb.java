package pap2025.Publicadores;

/**
 * Clase principal para ejecutar el servicio web simple
 */
public class EjecutarServicioWeb {
    
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SERVICIO WEB BIBLIOTECA COMUNITARIA ===");
        System.out.println("Versión: 1.0.0");
        System.out.println("Desarrollado para PAP2025");
        System.out.println();
        
        try {
            // Crear y publicar el servicio web
            ServicioWebSimple servicio = new ServicioWebSimple();
            servicio.publicar();
            
            System.out.println();
            System.out.println("=== SERVICIO WEB ACTIVO ===");
            System.out.println("El servicio está funcionando correctamente.");
            System.out.println("Para probar el servicio:");
            System.out.println("1. Abre: http://localhost:8080/bibliotecaWS?wsdl");
            System.out.println("2. O usa un cliente SOAP para consumir los métodos");
            System.out.println();
            System.out.println("Métodos disponibles:");
            System.out.println("- saludar(String nombre)");
            System.out.println("- obtenerInfoServicio()");
            System.out.println("- testServicio()");
            System.out.println("- obtenerVersion()");
            System.out.println("- autenticarLector(String email, String password)");
            System.out.println("- autenticarBibliotecario(String email, String password)");
            System.out.println("- registrarLibro(String titulo, int cantidadPaginas)");
            System.out.println("- registrarArticuloEspecial(String descripcion, double peso)");
            System.out.println("- crearPrestamo(String emailLector, int idMaterial, String emailBibliotecario)");
            System.out.println("- obtenerEstadisticas()");
            System.out.println();
            System.out.println("Para detener el servicio, presiona Ctrl+C");
            System.out.println("================================================");
            
            // Mantener el servicio ejecutándose
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("Error al ejecutar el servicio web: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
