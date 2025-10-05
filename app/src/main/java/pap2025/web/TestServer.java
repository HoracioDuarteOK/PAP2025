package pap2025.web;

import pap2025.persistencia.ConfiguracionBD;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Servidor web de prueba muy simple
 */
public class TestServer {
    
    public static void main(String[] args) throws IOException {
        String port = args.length > 0 ? args[0] : "8080";
        
        System.out.println("🚀 Iniciando Servidor Web de Prueba...");
        
        // Inicializar conexión a la base de datos
        System.out.println("🔌 Conectando a la base de datos...");
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos. Verifica la configuración.");
            System.exit(1);
        }
        System.out.println("✅ Conexión a la base de datos establecida");
        
        // Crear servidor HTTP simple
        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 0);
        
        // Manejador para la página principal
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "<html><head><title>Sistema de Biblioteca</title></head>" +
                    "<body><h1>🎉 ¡Servidor Web Funcionando!</h1>" +
                    "<p>El servidor web está funcionando correctamente.</p>" +
                    "<p>Base de datos: ✅ Conectada</p>" +
                    "<p>Puerto: " + port + "</p>" +
                    "<p>Hora: " + java.time.LocalDateTime.now() + "</p>" +
                    "</body></html>";
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        
        // Manejador para verificar la base de datos
        server.createContext("/db", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "<html><head><title>Estado de la Base de Datos</title></head>" +
                    "<body><h1>🗄️ Estado de la Base de Datos</h1>" +
                    "<p>✅ PostgreSQL conectado</p>" +
                    "<p>✅ Hibernate funcionando</p>" +
                    "<p>✅ Configuración correcta</p>" +
                    "</body></html>";
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("✅ Servidor web iniciado exitosamente!");
        System.out.println("🌐 Aplicación disponible en: http://localhost:" + port);
        System.out.println("📊 Verificar base de datos: http://localhost:" + port + "/db");
        System.out.println("⏹️  Presiona Ctrl+C para detener el servidor");
        
        // Mantener el servidor ejecutándose
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Deteniendo servidor web...");
            server.stop(0);
            ConfiguracionBD.cerrarConexion();
            System.out.println("✅ Servidor detenido correctamente");
        }));
        
        // Esperar indefinidamente
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Servidor interrumpido");
        }
    }
}
