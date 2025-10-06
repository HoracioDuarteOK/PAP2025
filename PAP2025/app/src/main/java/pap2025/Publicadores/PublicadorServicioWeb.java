package pap2025.Publicadores;

import pap2025.persistencia.ConfiguracionBD;

/**
 * Clase principal para publicar el servicio web de la biblioteca comunitaria
 * Esta clase inicializa la conexión a la base de datos y publica el servicio web
 */
public class PublicadorServicioWeb {
    
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando publicación del servicio web de Biblioteca Comunitaria PAP2025...");
        
        // Inicializar conexión a la base de datos
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ Error: No se pudo conectar a la base de datos");
            System.err.println("   Verifica la configuración de la base de datos en ConfiguracionBD");
            System.exit(1);
        }
        
        System.out.println("✅ Conexión a la base de datos establecida");
        
        // Crear y publicar el servicio web
        ControladorPublish publicador = new ControladorPublish();
        
        try {
            publicador.publicar();
            System.out.println("✅ Servicio web publicado exitosamente");
            System.out.println("🌐 El servicio está disponible para ser consumido por el cliente web");
            System.out.println("");
            System.out.println("📋 Para probar el servicio:");
            System.out.println("   1. Abre el cliente web pap2025WebClientWS");
            System.out.println("   2. Registra usuarios y prueba las funcionalidades");
            System.out.println("   3. Verifica que las operaciones se ejecuten correctamente");
            System.out.println("");
            System.out.println("🛑 Para detener el servicio, presiona Ctrl+C");
            
            // Mantener el servicio ejecutándose
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("❌ Error al publicar el servicio web: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar conexión a la base de datos
            ConfiguracionBD.cerrarConexion();
            System.out.println("🔌 Conexión a la base de datos cerrada");
        }
    }
}
