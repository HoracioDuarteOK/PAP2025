package pap2025.web;

import pap2025.persistencia.ConfiguracionBD;
import pap2025.web.soap.BibliotecaWebService;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;

import javax.xml.ws.Endpoint;
import java.io.File;

/**
 * Servidor web con Tomcat Embebido para la aplicación de biblioteca
 * Este servidor usa Servlets y JSP correctamente según los requisitos de la tarea
 */
public class TomcatEmbeddedServer {
    
    private Tomcat tomcat;
    private int port;
    private Endpoint soapEndpoint;
    
    public TomcatEmbeddedServer(int port) {
        this.port = port;
    }
    
    public void start() throws Exception {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   Sistema de Biblioteca Comunitaria                   ║");
        System.out.println("║   Servidor Web con Tomcat Embebido + Servlets + JSP   ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Inicializar conexión a base de datos
        System.out.println("🔌 Conectando a la base de datos...");
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos");
            System.err.println("   Verifica que PostgreSQL esté ejecutándose");
            System.err.println("   y que la base de datos 'pap2025' exista");
            return;
        }
        System.out.println("✅ Conexión a base de datos establecida");
        System.out.println();
        
        // Crear instancia de Tomcat
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector(); // Crear conector por defecto
        
        // Configurar directorio base
        String webappDirLocation = "src/main/webapp/";
        File webappDir = new File(webappDirLocation);
        if (!webappDir.exists()) {
            // Intentar con ruta absoluta si no se encuentra
            webappDirLocation = System.getProperty("user.dir") + "/app/src/main/webapp/";
            webappDir = new File(webappDirLocation);
        }
        
        if (!webappDir.exists()) {
            System.err.println("❌ No se encontró el directorio webapp: " + webappDirLocation);
            return;
        }
        
        System.out.println("📁 Directorio webapp: " + webappDir.getAbsolutePath());
        
        // Agregar webapp
        Context ctx = tomcat.addWebapp("", webappDir.getAbsolutePath());
        
        // Habilitar recargar recursos
        ctx.setReloadable(true);
        
        System.out.println();
        System.out.println("🚀 Iniciando Tomcat en puerto " + port + "...");
        
        // Iniciar Tomcat
        tomcat.start();
        
        // Publicar servicio SOAP
        System.out.println("📡 Publicando servicio SOAP...");
        try {
            String soapAddress = "http://localhost:9090/soap/biblioteca";
            soapEndpoint = Endpoint.publish(soapAddress, new BibliotecaWebService());
            System.out.println("✅ Servicio SOAP publicado en: " + soapAddress);
            System.out.println("📄 WSDL disponible en: " + soapAddress + "?wsdl");
        } catch (Exception e) {
            System.err.println("⚠️  No se pudo publicar servicio SOAP: " + e.getMessage());
        }
        
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║  ✅ Servidor iniciado exitosamente                     ║");
        System.out.println("║                                                        ║");
        System.out.println("║  🌐 Aplicación Web: http://localhost:" + port + "              ║");
        System.out.println("║  📡 Servicio SOAP: http://localhost:9090/soap/biblioteca ║");
        System.out.println("║  📄 WSDL: http://localhost:9090/soap/biblioteca?wsdl    ║");
        System.out.println("║                                                        ║");
        System.out.println("║  📝 Usuarios de prueba:                                ║");
        System.out.println("║     Bibliotecario: admin@biblioteca.com                ║");
        System.out.println("║     Lector: juan@email.com                             ║");
        System.out.println("║                                                        ║");
        System.out.println("║  Presiona Ctrl+C para detener el servidor             ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Mantener el servidor corriendo
        tomcat.getServer().await();
    }
    
    public void stop() throws LifecycleException {
        if (soapEndpoint != null && soapEndpoint.isPublished()) {
            soapEndpoint.stop();
            System.out.println("🛑 Servicio SOAP detenido");
        }
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
        }
    }
    
    public static void main(String[] args) {
        int port = 8080;
        
        // Permitir especificar puerto como argumento
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Puerto inválido, usando puerto por defecto: 8080");
            }
        }
        
        TomcatEmbeddedServer server = new TomcatEmbeddedServer(port);
        
        // Agregar hook para detener el servidor correctamente
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("\n🛑 Deteniendo servidor...");
                server.stop();
                System.out.println("✅ Servidor detenido correctamente");
            } catch (Exception e) {
                System.err.println("❌ Error al detener servidor: " + e.getMessage());
            }
        }));
        
        try {
            server.start();
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar servidor: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

