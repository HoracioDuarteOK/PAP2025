package pap2025.web;

import pap2025.persistencia.ConfiguracionBD;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * Servidor web híbrido que combina lo mejor de ambos enfoques
 */
public class HybridWebServer {
    
    public static void main(String[] args) {
        String port = args.length > 0 ? args[0] : "8080";
        
        System.out.println("🚀 Iniciando Servidor Web Híbrido...");
        System.out.println("📁 Directorio actual: " + new File(".").getAbsolutePath());
        
        // Inicializar conexión a la base de datos
        System.out.println("🔌 Conectando a la base de datos...");
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos. Verifica la configuración.");
            System.exit(1);
        }
        System.out.println("✅ Conexión a la base de datos establecida");
        
        try {
            // Crear instancia de Tomcat con configuración mínima
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.parseInt(port));
            
            // Configurar host para evitar problemas
            tomcat.getHost().setAutoDeploy(false);
            tomcat.getHost().setDeployOnStartup(false);
            
            // Configurar directorio webapp
            String webappDirLocation = "src/main/webapp/";
            File webappDir = new File(webappDirLocation);
            
            System.out.println("📂 Buscando webapp en: " + webappDir.getAbsolutePath());
            System.out.println("📂 Webapp existe: " + webappDir.exists());
            
            if (!webappDir.exists()) {
                System.err.println("❌ Directorio webapp no encontrado: " + webappDir.getAbsolutePath());
                System.exit(1);
            }
            
            // Agregar la aplicación web
            Context ctx = tomcat.addWebapp("", webappDir.getAbsolutePath());
            
            // Configurar directorio de clases
            File classesDir = new File("build/classes/java/main");
            System.out.println("📂 Buscando clases en: " + classesDir.getAbsolutePath());
            System.out.println("📂 Clases existen: " + classesDir.exists());
            
            if (classesDir.exists()) {
                System.out.println("📂 Configurando recursos estáticos");
                StandardRoot resources = new StandardRoot(ctx);
                resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                        classesDir.getAbsolutePath(), "/"));
                ctx.setResources(resources);
            }
            
            // Configuración mínima para evitar problemas
            ctx.setReloadable(false);
            ctx.setCrossContext(false);
            
            // Iniciar el servidor
            System.out.println("🚀 Iniciando Tomcat en puerto " + port + "...");
            tomcat.start();
            
            System.out.println("✅ Servidor web híbrido iniciado exitosamente!");
            System.out.println("🌐 Aplicación disponible en: http://localhost:" + port);
            System.out.println("📚 Sistema de Biblioteca Comunitaria - Interfaz Web");
            System.out.println("📖 Para acceder al sistema, abre tu navegador y ve a la URL mostrada");
            System.out.println("⏹️  Presiona Ctrl+C para detener el servidor");
            
            // Agregar shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n🛑 Deteniendo servidor web...");
                try {
                    tomcat.stop();
                    ConfiguracionBD.cerrarConexion();
                    System.out.println("✅ Servidor detenido correctamente");
                } catch (LifecycleException e) {
                    System.err.println("❌ Error al detener el servidor: " + e.getMessage());
                }
            }));
            
            // Mantener el servidor ejecutándose
            tomcat.getServer().await();
            
        } catch (LifecycleException e) {
            System.err.println("❌ Error al iniciar el servidor web: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
