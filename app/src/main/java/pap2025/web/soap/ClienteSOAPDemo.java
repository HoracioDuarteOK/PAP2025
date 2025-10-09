package pap2025.web.soap;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Cliente de demostración para consumir el servicio SOAP
 * 
 * NOTA: En producción, este cliente se generaría automáticamente con wsimport:
 * wsimport -keep -p pap2025.client http://localhost:9090/soap/biblioteca?wsdl
 * 
 * Para fines demostrativos, este cliente usa la interfaz manualmente.
 */
public class ClienteSOAPDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   Cliente SOAP - Biblioteca Comunitaria               ║");
        System.out.println("║   Demostración de consumo de servicios SOAP            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
        
        try {
            System.out.println("📡 Conectando al servicio SOAP...");
            
            // URL del WSDL
            URL wsdlURL = new URL("http://localhost:9090/soap/biblioteca?wsdl");
            
            // QName del servicio (debe coincidir con el @WebService)
            QName serviceName = new QName("http://soap.web.pap2025/", "BibliotecaService");
            
            // Crear el servicio
            Service service = Service.create(wsdlURL, serviceName);
            
            // Obtener el puerto (interfaz del servicio)
            IBibliotecaWebService biblioteca = service.getPort(IBibliotecaWebService.class);
            
            System.out.println("✅ Conexión establecida con el servicio SOAP");
            System.out.println();
            
            // Llamar a las operaciones
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("📊 ESTADÍSTICAS DE LA BIBLIOTECA");
            System.out.println("═══════════════════════════════════════════════════════");
            
            int totalMateriales = biblioteca.obtenerTotalMateriales();
            System.out.println("📚 Total de materiales: " + totalMateriales);
            
            int totalLibros = biblioteca.obtenerTotalLibros();
            System.out.println("📖 Total de libros: " + totalLibros);
            
            int totalPrestamos = biblioteca.obtenerTotalPrestamos();
            System.out.println("📋 Total de préstamos: " + totalPrestamos);
            
            int totalLectores = biblioteca.obtenerTotalLectores();
            System.out.println("👥 Total de lectores: " + totalLectores);
            
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("📚 TÍTULOS DE LIBROS DISPONIBLES");
            System.out.println("═══════════════════════════════════════════════════════");
            
            String[] titulosLibros = biblioteca.obtenerTitulosLibros();
            for (int i = 0; i < titulosLibros.length && i < 10; i++) {
                System.out.println("  " + (i + 1) + ". " + titulosLibros[i]);
            }
            if (titulosLibros.length > 10) {
                System.out.println("  ... y " + (titulosLibros.length - 10) + " libros más");
            }
            
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("🔍 VERIFICACIÓN DE USUARIOS");
            System.out.println("═══════════════════════════════════════════════════════");
            
            // Verificar usuarios
            String[] emailsProbar = {
                "admin@biblioteca.com",
                "juan@email.com",
                "usuario@noexiste.com"
            };
            
            for (String email : emailsProbar) {
                boolean existe = biblioteca.existeUsuario(email);
                if (existe) {
                    String nombre = biblioteca.obtenerNombreUsuario(email);
                    System.out.println("✅ " + email + " → " + nombre);
                } else {
                    System.out.println("❌ " + email + " → No existe");
                }
            }
            
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("📊 ESTADÍSTICAS EN TEXTO");
            System.out.println("═══════════════════════════════════════════════════════");
            
            String estadisticas = biblioteca.obtenerEstadisticasTexto();
            System.out.println(estadisticas);
            
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║  ✅ Cliente SOAP ejecutado exitosamente                ║");
            System.out.println("║  Todas las operaciones SOAP funcionaron correctamente ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con el servicio SOAP:");
            System.err.println("   " + e.getMessage());
            System.err.println();
            System.err.println("Verifica que el servidor esté corriendo:");
            System.err.println("   ./gradlew app:completeWebServer");
            e.printStackTrace();
        }
    }
}
