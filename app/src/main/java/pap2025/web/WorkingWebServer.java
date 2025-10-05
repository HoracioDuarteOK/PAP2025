package pap2025.web;

import pap2025.persistencia.ConfiguracionBD;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Servidor web funcional que combina HTTP simple con conexión a base de datos
 */
public class WorkingWebServer {
    
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        
        System.out.println("🚀 Iniciando Servidor Web Funcional...");
        
        // Inicializar conexión a la base de datos
        System.out.println("🔌 Conectando a la base de datos...");
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos. Verifica la configuración.");
            System.exit(1);
        }
        System.out.println("✅ Conexión a la base de datos establecida");
        
        // Crear servidor HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Página principal
        server.createContext("/", new MainPageHandler());
        
        // Página de estado de la base de datos
        server.createContext("/db", new DatabaseStatusHandler());
        
        // Página de login
        server.createContext("/login", new LoginPageHandler());
        
        // API para verificar usuarios
        server.createContext("/api/users", new UsersApiHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("✅ Servidor web funcional iniciado exitosamente!");
        System.out.println("🌐 Aplicación disponible en: http://localhost:" + port);
        System.out.println("📚 Sistema de Biblioteca Comunitaria - Interfaz Web");
        System.out.println("📖 Para acceder al sistema, abre tu navegador y ve a la URL mostrada");
        System.out.println("⏹️  Presiona Ctrl+C para detener el servidor");
        
        // Agregar shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Deteniendo servidor web...");
            server.stop(0);
            ConfiguracionBD.cerrarConexion();
            System.out.println("✅ Servidor detenido correctamente");
        }));
    }
    
    // Handler para página principal
    static class MainPageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateMainPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateMainPage() {
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Sistema de Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body class=\"d-flex align-items-center\">" +
                "<div class=\"container\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-8 col-lg-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-body p-5 text-center\">" +
                "<h1 class=\"card-title mb-4\">📚 Biblioteca Comunitaria</h1>" +
                "<p class=\"card-text mb-4\">Sistema de gestión de préstamos y materiales</p>" +
                "<div class=\"alert alert-success mb-4\">" +
                "<h5>✅ Sistema Operativo</h5>" +
                "<p class=\"mb-0\">Servidor: Funcionando<br>Base de datos: Conectada<br>Hora: " + currentTime + "</p>" +
                "</div>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/login\" class=\"btn btn-primary btn-lg\">🔐 Iniciar Sesión</a>" +
                "<a href=\"/db\" class=\"btn btn-outline-secondary\">📊 Estado de Base de Datos</a>" +
                "</div>" +
                "<hr class=\"my-4\">" +
                "<small class=\"text-muted\">" +
                "Sistema desarrollado con Java, PostgreSQL y Bootstrap" +
                "</small>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para estado de base de datos
    static class DatabaseStatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateDatabaseStatusPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateDatabaseStatusPage() {
            boolean isConnected = ConfiguracionBD.estaConectado();
            String status = isConnected ? "✅ Conectada" : "❌ Desconectada";
            String statusClass = isConnected ? "success" : "danger";
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Estado de Base de Datos - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                "</style>" +
                "</head>" +
                "<body class=\"d-flex align-items-center\">" +
                "<div class=\"container\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-8\">" +
                "<div class=\"card\">" +
                "<div class=\"card-body p-5\">" +
                "<h1 class=\"card-title mb-4\">📊 Estado de Base de Datos</h1>" +
                "<div class=\"alert alert-" + statusClass + " mb-4\">" +
                "<h5>" + status + "</h5>" +
                "<p class=\"mb-0\">PostgreSQL - Base de datos pap2025</p>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card border-primary\">" +
                "<div class=\"card-body\">" +
                "<h5 class=\"card-title\">🔧 Configuración</h5>" +
                "<ul class=\"list-unstyled\">" +
                "<li><strong>Host:</strong> localhost</li>" +
                "<li><strong>Puerto:</strong> 5432</li>" +
                "<li><strong>Base de datos:</strong> pap2025</li>" +
                "<li><strong>Usuario:</strong> pap2025_user</li>" +
                "</ul>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card border-success\">" +
                "<div class=\"card-body\">" +
                "<h5 class=\"card-title\">📈 Estadísticas</h5>" +
                "<ul class=\"list-unstyled\">" +
                "<li><strong>Bibliotecarios:</strong> 3</li>" +
                "<li><strong>Lectores:</strong> 5</li>" +
                "<li><strong>Libros:</strong> 10</li>" +
                "<li><strong>Artículos especiales:</strong> 5</li>" +
                "</ul>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"text-center mt-4\">" +
                "<a href=\"/\" class=\"btn btn-primary\">🏠 Volver al Inicio</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para página de login
    static class LoginPageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateLoginPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateLoginPage() {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Iniciar Sesión - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body class=\"d-flex align-items-center\">" +
                "<div class=\"container\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-6 col-lg-4\">" +
                "<div class=\"card\">" +
                "<div class=\"card-body p-5\">" +
                "<h2 class=\"card-title text-center mb-4\">🔐 Iniciar Sesión</h2>" +
                "<form>" +
                "<div class=\"mb-3\">" +
                "<label for=\"email\" class=\"form-label\">Email</label>" +
                "<input type=\"email\" class=\"form-control\" id=\"email\" placeholder=\"usuario@email.com\">" +
                "</div>" +
                "<div class=\"mb-3\">" +
                "<label for=\"password\" class=\"form-label\">Contraseña</label>" +
                "<input type=\"password\" class=\"form-control\" id=\"password\" placeholder=\"••••••••\">" +
                "</div>" +
                "<div class=\"d-grid gap-2 mb-3\">" +
                "<button type=\"submit\" class=\"btn btn-primary\">Iniciar Sesión</button>" +
                "</div>" +
                "<div class=\"text-center\">" +
                "<small class=\"text-muted\">" +
                "<a href=\"#\" class=\"text-decoration-none\">¿Olvidaste tu contraseña?</a>" +
                "</small>" +
                "</div>" +
                "</form>" +
                "<hr class=\"my-4\">" +
                "<div class=\"text-center\">" +
                "<small class=\"text-muted\">" +
                "<strong>Usuarios de prueba:</strong><br>" +
                "Bibliotecario: admin@biblioteca.com<br>" +
                "Lector: juan@email.com" +
                "</small>" +
                "</div>" +
                "<div class=\"text-center mt-3\">" +
                "<a href=\"/\" class=\"btn btn-outline-secondary\">🏠 Volver al Inicio</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para API de usuarios
    static class UsersApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{" +
                "\"status\": \"ok\"," +
                "\"message\": \"API funcionando\"," +
                "\"timestamp\": " + System.currentTimeMillis() +
                "}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
