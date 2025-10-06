package pap2025.web;

import pap2025.persistencia.ConfiguracionBD;
//  // Eliminado
import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Usuario;
import pap2025.logica.Bibliotecario;
import pap2025.logica.Lector;
import pap2025.logica.Material;
import pap2025.logica.Libro;
import pap2025.logica.ArtEspeciales;
import pap2025.logica.Prestamo;
import pap2025.logica.EstadoL;
import pap2025.logica.EstadoP;
import pap2025.logica.Zona;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Servidor web completo del sistema de biblioteca comunitaria
 */
public class CompleteWebServer {
    
    private static IControladorFachada controlador;
    
    // Mapa para almacenar sesiones de usuarios autenticados
    private static Map<String, Usuario> sesiones = new HashMap<>();
    
    // Método para obtener usuario de la sesión
    private static Usuario getUsuarioFromSession(HttpExchange t) {
        String cookieHeader = t.getRequestHeaders().getFirst("Cookie");
        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split(";");
            for (String cookie : cookies) {
                String[] parts = cookie.trim().split("=");
                if (parts.length == 2 && parts[0].equals("session")) {
                    String sessionToken = parts[1];
                    return sesiones.get(sessionToken);
                }
            }
        }
        return null;
    }
    
    // Método para generar página base con Bootstrap
    private static String generateBasePage(String title, String content) {
        return "<!DOCTYPE html>" +
            "<html lang=\"es\">" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
            "<title>" + title + " - Biblioteca Comunitaria</title>" +
            "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
            "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css\" rel=\"stylesheet\">" +
            "<style>" +
            "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; font-size: 14px; }" +
            ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; margin-bottom: 1rem; }" +
            ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
            ".navbar-brand { font-weight: bold; font-size: 1.1rem; }" +
            ".btn-action { margin: 1px; padding: 0.25rem 0.5rem; font-size: 0.8rem; }" +
            ".status-badge { font-size: 0.75rem; padding: 0.25rem 0.5rem; }" +
            ".table-responsive { border-radius: 0.375rem; overflow-x: auto; }" +
            ".table { font-size: 0.875rem; margin-bottom: 0; }" +
            ".table th, .table td { padding: 0.5rem 0.75rem; vertical-align: middle; }" +
            ".form-control, .form-select { font-size: 0.875rem; }" +
            ".btn { font-size: 0.875rem; padding: 0.5rem 1rem; }" +
            ".btn-sm { padding: 0.25rem 0.5rem; font-size: 0.75rem; }" +
            ".modal-dialog { margin: 0.5rem; }" +
            ".modal-content { border-radius: 0.5rem; }" +
            ".stats-card { text-align: center; padding: 1rem; }" +
            ".stats-number { font-size: 2rem; font-weight: bold; }" +
            ".stats-label { font-size: 0.875rem; color: #6c757d; }" +
            ".action-buttons { display: flex; flex-wrap: wrap; gap: 0.25rem; }" +
            ".navbar-toggler { border: none; padding: 0.25rem 0.5rem; }" +
            ".navbar-toggler:focus { box-shadow: none; }" +
            "@media (max-width: 768px) {" +
            ".container { padding-left: 0.75rem; padding-right: 0.75rem; }" +
            ".navbar-brand { font-size: 1rem; }" +
            ".btn { width: 100%; margin-bottom: 0.5rem; }" +
            ".btn-action { width: auto; margin: 1px; }" +
            ".table-responsive { font-size: 0.75rem; }" +
            ".modal-dialog { margin: 0.25rem; max-width: calc(100% - 0.5rem); }" +
            ".stats-number { font-size: 1.5rem; }" +
            ".card-body { padding: 0.75rem; }" +
            "}" +
            "@media (max-width: 576px) {" +
            ".container { padding-left: 0.5rem; padding-right: 0.5rem; }" +
            ".navbar-brand { font-size: 0.9rem; }" +
            ".table th, .table td { padding: 0.25rem 0.5rem; }" +
            ".btn-sm { padding: 0.2rem 0.4rem; font-size: 0.7rem; }" +
            ".modal-dialog { margin: 0.1rem; max-width: calc(100% - 0.2rem); }" +
            ".stats-card { padding: 0.75rem; }" +
            "}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
            "<div class=\"container-fluid\">" +
            "<a class=\"navbar-brand\" href=\"/\">" +
            "<i class=\"fas fa-book\"></i> <span class=\"d-none d-sm-inline\">Biblioteca Comunitaria</span>" +
            "</a>" +
            "<button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\">" +
            "<span class=\"navbar-toggler-icon\"></span>" +
            "</button>" +
            "<div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
            "<div class=\"navbar-nav ms-auto\">" +
            "<a class=\"nav-link\" href=\"/bibliotecario\">" +
            "<i class=\"fas fa-home\"></i> <span class=\"d-none d-sm-inline\">Dashboard</span>" +
            "</a>" +
            "<a class=\"nav-link\" href=\"/logout\">" +
            "<i class=\"fas fa-sign-out-alt\"></i> <span class=\"d-none d-sm-inline\">Cerrar Sesión</span>" +
            "</a>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</nav>" +
            content +
            "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>" +
            "</body>" +
            "</html>";
    }
    
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        
        System.out.println("🚀 Iniciando Sistema Completo de Biblioteca Comunitaria...");
        
        // Inicializar conexión a la base de datos
        System.out.println("🔌 Conectando a la base de datos...");
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos. Verifica la configuración.");
            System.exit(1);
        }
        System.out.println("✅ Conexión a la base de datos establecida");
        
        // Obtener controlador
        controlador = pap2025.logica.ControladorFachada.getInstancia();
        
        // Crear servidor HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Rutas principales
        server.createContext("/", new MainPageHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/auth", new AuthHandler());
        
        // Rutas para bibliotecarios
        server.createContext("/bibliotecario", new BibliotecarioHandler());
        server.createContext("/bibliotecario/usuarios", new GestionUsuariosHandler());
        server.createContext("/bibliotecario/usuarios/nuevo", new NuevoUsuarioHandler());
        server.createContext("/bibliotecario/materiales", new GestionMaterialesHandler());
        server.createContext("/bibliotecario/materiales/nuevo", new GestionMaterialesHandler());
        server.createContext("/bibliotecario/prestamos", new GestionPrestamosHandler());
        server.createContext("/bibliotecario/prestamos/nuevo", new GestionPrestamosHandler());
        server.createContext("/bibliotecario/admin", new PanelAdminHandler());
        
        // Rutas para formularios de registro
        server.createContext("/bibliotecario/usuarios/registrar-lector", new RegistrarLectorHandler());
        server.createContext("/bibliotecario/usuarios/registrar-bibliotecario", new RegistrarBibliotecarioHandler());
        server.createContext("/bibliotecario/materiales/registrar-donacion", new RegistrarDonacionHandler());
        
        // Rutas para gestión de usuarios
        server.createContext("/bibliotecario/usuarios/cambiar-estado", new CambiarEstadoLectorHandler());
        server.createContext("/bibliotecario/usuarios/cambiar-zona", new CambiarZonaLectorHandler());
        
        // Rutas para gestión de préstamos
        server.createContext("/bibliotecario/prestamos/cambiar-estado", new CambiarEstadoPrestamoHandler());
        server.createContext("/lector/prestamos/nuevo", new NuevoPrestamoHandler());
        
        // Rutas para funcionalidades opcionales
        server.createContext("/bibliotecario/prestamos/por-lector", new PrestamosPorLectorHandler());
        server.createContext("/bibliotecario/prestamos/activos", new PrestamosActivosHandler());
        server.createContext("/bibliotecario/prestamos/editar", new EditarPrestamoHandler());
        server.createContext("/bibliotecario/prestamos/activos-lector", new PrestamosActivosLectorHandler());
        server.createContext("/bibliotecario/materiales/editar", new EditarMaterialHandler());
        server.createContext("/bibliotecario/usuarios/editar", new EditarUsuarioHandler());
        server.createContext("/bibliotecario/reportes/por-zona", new ReportePorZonaHandler());
        server.createContext("/bibliotecario/reportes/materiales-populares", new MaterialesPopularesHandler());
        server.createContext("/bibliotecario/historial", new HistorialBibliotecarioHandler());
        server.createContext("/bibliotecario/materiales/por-fecha", new MaterialesPorFechaHandler());
        
        // Rutas para lectores
        server.createContext("/lector", new LectorHandler());
        server.createContext("/lector/materiales", new ConsultarMaterialesHandler());
        server.createContext("/lector/prestamos", new MisPrestamosHandler());
        
        // API endpoints
        server.createContext("/api/usuarios", new ApiUsuariosHandler());
        server.createContext("/api/materiales", new ApiMaterialesHandler());
        server.createContext("/api/prestamos", new ApiPrestamosHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("✅ Sistema completo iniciado exitosamente!");
        System.out.println("🌐 Aplicación disponible en: http://localhost:" + port);
        System.out.println("📚 Sistema de Biblioteca Comunitaria - Funcionalidad Completa");
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
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                ".feature-card { transition: transform 0.3s ease; }" +
                ".feature-card:hover { transform: translateY(-5px); }" +
                "</style>" +
                "</head>" +
                "<body class=\"d-flex align-items-center\">" +
                "<div class=\"container\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-10\">" +
                "<div class=\"card\">" +
                "<div class=\"card-body p-5\">" +
                "<div class=\"text-center mb-5\">" +
                "<h1 class=\"display-4 mb-3\">📚 Biblioteca Comunitaria</h1>" +
                "<p class=\"lead\">Sistema completo de gestión de préstamos y materiales</p>" +
                "</div>" +
                
                "<div class=\"alert alert-success mb-4\">" +
                "<h5><i class=\"bi bi-check-circle\"></i> Sistema Operativo</h5>" +
                "<p class=\"mb-0\">Servidor: Funcionando | Base de datos: Conectada | Hora: " + currentTime + "</p>" +
                "</div>" +
                
                "<div class=\"row mb-4\">" +
                "<div class=\"col-md-6 mb-3\">" +
                "<div class=\"card feature-card h-100\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-person-badge text-primary\" style=\"font-size: 3rem;\"></i>" +
                "<h5 class=\"card-title mt-3\">Bibliotecarios</h5>" +
                "<p class=\"card-text\">Gestión completa de usuarios, materiales y préstamos</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6 mb-3\">" +
                "<div class=\"card feature-card h-100\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-book text-success\" style=\"font-size: 3rem;\"></i>" +
                "<h5 class=\"card-title mt-3\">Lectores</h5>" +
                "<p class=\"card-text\">Consulta de materiales y gestión de préstamos personales</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/login\" class=\"btn btn-primary btn-lg\">" +
                "<i class=\"bi bi-box-arrow-in-right\"></i> Iniciar Sesión" +
                "</a>" +
                "</div>" +
                
                "<hr class=\"my-4\">" +
                "<div class=\"row text-center\">" +
                "<div class=\"col-md-4\">" +
                "<h6><i class=\"bi bi-people\"></i> Gestión de Usuarios</h6>" +
                "<small class=\"text-muted\">Registro y control de bibliotecarios y lectores</small>" +
                "</div>" +
                "<div class=\"col-md-4\">" +
                "<h6><i class=\"bi bi-book\"></i> Gestión de Materiales</h6>" +
                "<small class=\"text-muted\">Libros y artículos especiales</small>" +
                "</div>" +
                "<div class=\"col-md-4\">" +
                "<h6><i class=\"bi bi-arrow-repeat\"></i> Gestión de Préstamos</h6>" +
                "<small class=\"text-muted\">Ciclo completo de préstamos</small>" +
                "</div>" +
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
    
    // Handler para login
    static class LoginHandler implements HttpHandler {
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
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
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
                "<div class=\"text-center mb-4\">" +
                "<h2><i class=\"bi bi-box-arrow-in-right\"></i> Iniciar Sesión</h2>" +
                "</div>" +
                
                "<form action=\"/auth\" method=\"POST\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"email\" class=\"form-label\">Email</label>" +
                "<input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\" placeholder=\"usuario@email.com\" required>" +
                "</div>" +
                "<div class=\"mb-3\">" +
                "<label for=\"password\" class=\"form-label\">Contraseña</label>" +
                "<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"••••••••\" required>" +
                "</div>" +
                
                "<div class=\"d-grid gap-2 mb-3\">" +
                "<button type=\"submit\" class=\"btn btn-primary\">" +
                "<i class=\"bi bi-box-arrow-in-right\"></i> Iniciar Sesión" +
                "</button>" +
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
                "<a href=\"/\" class=\"btn btn-outline-secondary\">" +
                "<i class=\"bi bi-house\"></i> Volver al Inicio" +
                "</a>" +
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
    
    // Método para parsear datos de formulario
    private static Map<String, String> parseFormData(String formData) {
        Map<String, String> params = new HashMap<>();
        if (formData != null && !formData.isEmpty()) {
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    try {
                        String key = URLDecoder.decode(keyValue[0], "UTF-8");
                        String value = URLDecoder.decode(keyValue[1], "UTF-8");
                        params.put(key, value);
                    } catch (Exception e) {
                        // Ignorar errores de decodificación
                    }
                }
            }
        }
        return params;
    }

    // Handler para autenticación
    static class AuthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("🔐 Procesando autenticación...");
            
            if ("POST".equals(t.getRequestMethod())) {
                // Procesar login
                String body = new String(t.getRequestBody().readAllBytes());
                System.out.println("📝 Datos recibidos: " + body);
                
                Map<String, String> params = parseFormData(body);
                
                String email = params.get("email");
                String password = params.get("password");
                
                // Decodificar el email si está URL-encoded
                if (email != null) {
                    try {
                        email = URLDecoder.decode(email, "UTF-8");
                    } catch (Exception e) {
                        System.err.println("Error decodificando email: " + e.getMessage());
                    }
                }
                
                System.out.println("👤 Email: " + email);
                System.out.println("🔑 Password: " + password);
                
                try {
                    Usuario usuario = controlador.obtenerUsuario(email);
                    System.out.println("🔍 Usuario encontrado: " + (usuario != null ? usuario.getNombre() : "null"));
                    
                    if (usuario != null) {
                        // Validar contraseña (por ahora aceptamos cualquier contraseña para usuarios existentes)
                        // En un sistema real, aquí se validaría el hash de la contraseña
                        System.out.println("✅ Usuario autenticado correctamente");
                        
                        // Generar token de sesión
                        String sessionToken = UUID.randomUUID().toString();
                        sesiones.put(sessionToken, usuario);
                        System.out.println("🔑 Token de sesión generado: " + sessionToken);
                        
                        // Redirigir según tipo de usuario
                        String redirectUrl;
                        if (usuario instanceof Bibliotecario) {
                            redirectUrl = "/bibliotecario";
                            System.out.println("👨‍💼 Redirigiendo a dashboard de bibliotecario");
                        } else {
                            redirectUrl = "/lector";
                            System.out.println("👤 Redirigiendo a dashboard de lector");
                        }
                        
                        // Enviar respuesta de redirección con cookie de sesión
                        t.getResponseHeaders().set("Location", redirectUrl);
                        t.getResponseHeaders().set("Set-Cookie", "session=" + sessionToken + "; Path=/; HttpOnly");
                        t.sendResponseHeaders(302, 0);
                        t.getResponseBody().close();
                        return;
                    } else {
                        System.out.println("❌ Usuario no encontrado");
                        // Mostrar página de error de login
                        String response = generateLoginErrorPage("Usuario no encontrado. Verifica tu email.");
                        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                        t.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error en autenticación: " + e.getMessage());
                    e.printStackTrace();
                    
                    // En caso de error, mostrar página de error
                    String response = generateLoginErrorPage("Error en el sistema. Intenta nuevamente.");
                    t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                    t.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }
            } else {
                System.out.println("❌ Método no permitido: " + t.getRequestMethod());
            }
            
            // Si llega aquí, permitir acceso por defecto
            System.out.println("🔄 Permitindo acceso por defecto...");
            String redirectUrl = "/lector";
            t.getResponseHeaders().set("Location", redirectUrl);
            t.sendResponseHeaders(302, 0);
            t.getResponseBody().close();
        }
        
        private Map<String, String> parseFormData(String formData) {
            Map<String, String> params = new HashMap<>();
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
            return params;
        }
        
        private String generateErrorPage(String message) {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Error de Autenticación - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                "</style>" +
                "</head>" +
                "<body class=\"d-flex align-items-center\">" +
                "<div class=\"container\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-6 col-lg-4\">" +
                "<div class=\"card\">" +
                "<div class=\"card-body p-5 text-center\">" +
                "<i class=\"bi bi-exclamation-triangle text-danger\" style=\"font-size: 4rem;\"></i>" +
                "<h3 class=\"mt-3 mb-3\">Error de Autenticación</h3>" +
                "<p class=\"text-muted mb-4\">" + message + "</p>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/login\" class=\"btn btn-primary\">" +
                "<i class=\"bi bi-arrow-left\"></i> Volver al Login" +
                "</a>" +
                "<a href=\"/\" class=\"btn btn-outline-secondary\">" +
                "<i class=\"bi bi-house\"></i> Ir al Inicio" +
                "</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        }
        
        
        private String generateLoginErrorPage(String errorMessage) {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Error de Login - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
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
                "<div class=\"text-center mb-4\">" +
                "<i class=\"bi bi-exclamation-triangle text-danger\" style=\"font-size: 3rem;\"></i>" +
                "<h2 class=\"mt-3\">Error de Autenticación</h2>" +
                "</div>" +
                "<div class=\"alert alert-danger\" role=\"alert\">" +
                "<i class=\"bi bi-exclamation-circle\"></i> " + errorMessage +
                "</div>" +
                "<div class=\"d-grid gap-2 mb-3\">" +
                "<a href=\"/login\" class=\"btn btn-primary\"><i class=\"bi bi-arrow-left\"></i> Intentar Nuevamente</a>" +
                "</div>" +
                "<hr class=\"my-4\">" +
                "<div class=\"text-center\">" +
                "<small class=\"text-muted\"><strong>Usuarios de prueba:</strong><br>" +
                "Bibliotecario: admin@biblioteca.com<br>" +
                "Lector: juan@email.com</small>" +
                "</div>" +
                "<div class=\"text-center mt-3\">" +
                "<a href=\"/\" class=\"btn btn-outline-secondary\"><i class=\"bi bi-house\"></i> Volver al Inicio</a>" +
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
    
    // Handler para cambiar estado de lector
    static class CambiarEstadoLectorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                String email = params.get("email");
                
                try {
                    Usuario usuario = controlador.obtenerUsuario(email);
                    if (usuario instanceof Lector) {
                        Lector lector = (Lector) usuario;
                        // Cambiar estado: ACTIVO -> SUSPENDIDO, SUSPENDIDO -> ACTIVO
                        EstadoL nuevoEstado = lector.getEstadoL() == EstadoL.ACTIVO ? EstadoL.SUSPENDIDO : EstadoL.ACTIVO;
                        


                        // Persistir el cambio en la base de datos
                        
                        boolean exito = controlador.modificarEstadoLector(email, nuevoEstado);
                        
                        String response = "Estado del lector " + lector.getNombre() + " cambiado a " + nuevoEstado;
                        t.getResponseHeaders().set("Content-Type", "text/plain");
                        t.sendResponseHeaders(200, response.length());
                        t.getResponseBody().write(response.getBytes());
                    } else {
                        String response = "Usuario no encontrado o no es un lector";
                        t.getResponseHeaders().set("Content-Type", "text/plain");
                        t.sendResponseHeaders(400, response.length());
                        t.getResponseBody().write(response.getBytes());
                    }
                } catch (Exception e) {
                    String response = "Error al cambiar estado: " + e.getMessage();
                    t.getResponseHeaders().set("Content-Type", "text/plain");
                    t.sendResponseHeaders(500, response.length());
                    t.getResponseBody().write(response.getBytes());
                }
            } else {
                String response = "Método no permitido";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(405, responseBytes.length);
                t.getResponseBody().write(responseBytes);
            }
            t.getResponseBody().close();
        }
    }
    
    // Handler para cambiar zona de lector
    static class CambiarZonaLectorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                String email = params.get("email");
                String nuevaZona = params.get("zona");
                
                try {
                    Usuario usuario = controlador.obtenerUsuario(email);
                    if (usuario instanceof Lector) {
                        Lector lector = (Lector) usuario;
                        
                        // Persistir el cambio en la base de datos
                        boolean exito = controlador.cambiarZonaLector(email, Zona.valueOf(nuevaZona));
                        
                        String response = "Zona del lector " + lector.getNombre() + " cambiada a " + nuevaZona;
                        t.getResponseHeaders().set("Content-Type", "text/plain");
                        t.sendResponseHeaders(200, response.length());
                        t.getResponseBody().write(response.getBytes());
                    } else {
                        String response = "Usuario no encontrado o no es un lector";
                        t.getResponseHeaders().set("Content-Type", "text/plain");
                        t.sendResponseHeaders(400, response.length());
                        t.getResponseBody().write(response.getBytes());
                    }
                } catch (Exception e) {
                    String response = "Error al cambiar zona: " + e.getMessage();
                    t.getResponseHeaders().set("Content-Type", "text/plain");
                    t.sendResponseHeaders(500, response.length());
                    t.getResponseBody().write(response.getBytes());
                }
            } else {
                String response = "Método no permitido";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(405, responseBytes.length);
                t.getResponseBody().write(responseBytes);
            }
            t.getResponseBody().close();
        }
    }
    
    // Handler para cambiar estado de préstamo
    static class CambiarEstadoPrestamoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                String idStr = params.get("id");
                
                try {
                    int id = Integer.parseInt(idStr);
                    List<Prestamo> prestamos = controlador.getListaPrestamos();
                    Prestamo prestamo = null;
                    
                    for (Prestamo p : prestamos) {
                        if (p.getId() == id) {
                            prestamo = p;
                            break;
                        }
                    }
                    
                    if (prestamo != null) {
                        // Cambiar estado: ENCURSO -> DEVUELTO, DEVUELTO -> ENCURSO
                        EstadoP nuevoEstado = prestamo.getEstadoP() == EstadoP.ENCURSO ? EstadoP.DEVUELTO : EstadoP.ENCURSO;
                        prestamo.setEstadoP(nuevoEstado);
                        
                        // Persistir el cambio en la base de datos
                        controlador.actualizarEstadoPrestamo(id, nuevoEstado);
                        
                        String response = "Estado del préstamo " + id + " cambiado a " + nuevoEstado;
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(200, responseBytes.length);
                        OutputStream os = t.getResponseBody();
                        os.write(responseBytes);
                        os.close();
                    } else {
                        String response = "Préstamo no encontrado";
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(400, responseBytes.length);
                        OutputStream os = t.getResponseBody();
                        os.write(responseBytes);
                        os.close();
                    }
                } catch (Exception e) {
                    String response = "Error al cambiar estado del préstamo: " + e.getMessage();
                    byte[] responseBytes = response.getBytes("UTF-8");
                    t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                    t.sendResponseHeaders(500, responseBytes.length);
                    OutputStream os = t.getResponseBody();
                    os.write(responseBytes);
                    os.close();
                }
            } else {
                String response = "Método no permitido";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(405, responseBytes.length);
                t.getResponseBody().write(responseBytes);
            }
            t.getResponseBody().close();
        }
    }
    
    // Handler para nuevo préstamo por lector
    static class NuevoPrestamoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("GET".equals(t.getRequestMethod())) {
                // Mostrar formulario para crear préstamo
                List<Material> materiales = controlador.obtenerMaterialesDisponibles();
                List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
                
                StringBuilder materialesOptions = new StringBuilder();
                for (Material material : materiales) {
                    String materialInfo = "";
                    if (material instanceof Libro) {
                        materialInfo = ((Libro) material).getTitulo() + " (Libro)";
                    } else if (material instanceof ArtEspeciales) {
                        materialInfo = ((ArtEspeciales) material).getDescripcion() + " (Artículo Especial)";
                    }
                    materialesOptions.append("<option value=\"").append(material.getId()).append("\">")
                                   .append(materialInfo).append("</option>");
                }
                
                StringBuilder bibliotecariosOptions = new StringBuilder();
                for (Bibliotecario bibliotecario : bibliotecarios) {
                    bibliotecariosOptions.append("<option value=\"").append(bibliotecario.getId()).append("\">")
                                        .append(bibliotecario.getNombre()).append("</option>");
                }
                
                String html = "<!DOCTYPE html>" +
                    "<html lang=\"es\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "<title>Nuevo Préstamo - Biblioteca Comunitaria</title>" +
                    "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                    "<style>body { background: #f8f9fa; }</style>" +
                    "</head>" +
                    "<body>" +
                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-success\">" +
                    "<div class=\"container\">" +
                    "<a class=\"navbar-brand\" href=\"/lector\"><i class=\"bi bi-book\"></i> Dashboard Lector</a>" +
                    "<div class=\"navbar-nav ms-auto\">" +
                    "<a class=\"nav-link\" href=\"/lector\">🏠 Dashboard</a>" +
                    "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                    "</div>" +
                    "</div>" +
                    "</nav>" +
                    "<div class=\"container mt-4\">" +
                    "<div class=\"row justify-content-center\">" +
                    "<div class=\"col-md-8\">" +
                    "<div class=\"card\">" +
                    "<div class=\"card-header bg-success text-white\">" +
                    "<h5 class=\"mb-0\">📚 Solicitar Nuevo Préstamo</h5>" +
                    "</div>" +
                    "<div class=\"card-body\">" +
                    "<form method=\"POST\" action=\"/lector/prestamos/nuevo\">" +
                    "<div class=\"mb-3\">" +
                    "<label for=\"material\" class=\"form-label\">Material a solicitar:</label>" +
                    "<select class=\"form-select\" id=\"material\" name=\"material\" required>" +
                    "<option value=\"\">Seleccione un material...</option>" +
                    materialesOptions.toString() +
                    "</select>" +
                    "</div>" +
                    "<div class=\"mb-3\">" +
                    "<label for=\"bibliotecario\" class=\"form-label\">Bibliotecario responsable:</label>" +
                    "<select class=\"form-select\" id=\"bibliotecario\" name=\"bibliotecario\" required>" +
                    "<option value=\"\">Seleccione un bibliotecario...</option>" +
                    bibliotecariosOptions.toString() +
                    "</select>" +
                    "</div>" +
                    "<div class=\"mb-3\">" +
                    "<label for=\"fechaDevolucion\" class=\"form-label\">Fecha de devolución:</label>" +
                    "<input type=\"date\" class=\"form-control\" id=\"fechaDevolucion\" name=\"fechaDevolucion\" required min=\"" + java.time.LocalDate.now().plusDays(1).toString() + "\">" +
                    "<div class=\"form-text\">La fecha debe ser al menos mañana.</div>" +
                    "</div>" +
                    "<div class=\"d-grid gap-2\">" +
                    "<button type=\"submit\" class=\"btn btn-success\">Solicitar Préstamo</button>" +
                    "<a href=\"/lector\" class=\"btn btn-secondary\">Cancelar</a>" +
                    "</div>" +
                    "</form>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
                
                byte[] htmlBytes = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(200, htmlBytes.length);
                t.getResponseBody().write(htmlBytes);
            } else if ("POST".equals(t.getRequestMethod())) {
                // Procesar solicitud de préstamo
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                String materialIdStr = params.get("material");
                String bibliotecarioIdStr = params.get("bibliotecario");
                String fechaDevolucionStr = params.get("fechaDevolucion");
                
                try {
                    int materialId = Integer.parseInt(materialIdStr);
                    int bibliotecarioId = Integer.parseInt(bibliotecarioIdStr);
                    
                    // Obtener material y bibliotecario
                    Material material = null;
                    List<Material> materiales = controlador.getListaMateriales();
                    for (Material m : materiales) {
                        if (m.getId() == materialId) {
                            material = m;
                            break;
                        }
                    }
                    
                    Bibliotecario bibliotecario = null;
                    List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
                    for (Bibliotecario b : bibliotecarios) {
                        if (b.getId() == bibliotecarioId) {
                            bibliotecario = b;
                            break;
                        }
                    }
                    
                    if (material != null && bibliotecario != null) {
                        // Obtener el lector autenticado desde la sesión
                        Usuario usuario = getUsuarioFromSession(t);
                        
                        if (usuario instanceof Lector) {
                            Lector lector = (Lector) usuario;
                            
                            // Crear DTFecha a partir de la fecha enviada por el formulario
                            DTFecha fechaDevolucion;
                            if (fechaDevolucionStr != null && !fechaDevolucionStr.isEmpty()) {
                                // Parsear fecha en formato YYYY-MM-DD
                                String[] partes = fechaDevolucionStr.split("-");
                                int anio = Integer.parseInt(partes[0]);
                                int mes = Integer.parseInt(partes[1]);
                                int dia = Integer.parseInt(partes[2]);
                                fechaDevolucion = new DTFecha(dia, mes, anio);
                                
                                // Validar que la fecha de devolución sea futura
                                LocalDate fechaHoy = LocalDate.now();
                                LocalDate fechaDevolucionLocal = LocalDate.of(anio, mes, dia);
                                if (fechaDevolucionLocal.isBefore(fechaHoy.plusDays(1))) {
                                    String response = "La fecha de devolución debe ser al menos mañana";
                                    byte[] responseBytes = response.getBytes("UTF-8");
                                    t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                                    t.sendResponseHeaders(400, responseBytes.length);
                                    t.getResponseBody().write(responseBytes);
                                    return;
                                }
                            } else {
                                // Fecha por defecto si no se especifica
                                fechaDevolucion = new DTFecha(18, 10, 2025);
                            }
                            
                            // Usar el método del controlador para crear y persistir el préstamo
                            Integer idPrestamo = controlador.crearPrestamo(material, lector, bibliotecario, fechaDevolucion);
                            
                            if (idPrestamo != null) {
                                String response = "Préstamo solicitado exitosamente. ID: " + idPrestamo;
                                byte[] responseBytes = response.getBytes("UTF-8");
                                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                                t.sendResponseHeaders(200, responseBytes.length);
                                t.getResponseBody().write(responseBytes);
                            } else {
                                String response = "Error al crear el préstamo";
                                byte[] responseBytes = response.getBytes("UTF-8");
                                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                                t.sendResponseHeaders(500, responseBytes.length);
                                t.getResponseBody().write(responseBytes);
                            }
                        } else if (usuario == null) {
                            String response = "Debe iniciar sesión para solicitar préstamos";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.sendResponseHeaders(401, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        } else {
                            String response = "Solo los lectores pueden solicitar préstamos";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.sendResponseHeaders(403, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        }
                    } else {
                        String response = "Material o bibliotecario no encontrado";
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(400, responseBytes.length);
                        t.getResponseBody().write(responseBytes);
                    }
                } catch (Exception e) {
                    String response = "Error al crear préstamo: " + e.getMessage();
                    byte[] responseBytes = response.getBytes("UTF-8");
                    t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                    t.sendResponseHeaders(500, responseBytes.length);
                    t.getResponseBody().write(responseBytes);
                }
            } else {
                String response = "Método no permitido";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(405, responseBytes.length);
                t.getResponseBody().write(responseBytes);
            }
            t.getResponseBody().close();
        }
    }
    
    // Handler para préstamos por lector
    static class PrestamosPorLectorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("GET".equals(t.getRequestMethod())) {
                List<Lector> lectores = controlador.getListaLectores();
                List<Prestamo> prestamos = controlador.getListaPrestamos();
                
                StringBuilder lectoresOptions = new StringBuilder();
                for (Lector lector : lectores) {
                    lectoresOptions.append("<option value=\"").append(lector.getEmail()).append("\">")
                                  .append(lector.getNombre()).append(" (").append(lector.getEmail()).append(")")
                                  .append("</option>");
                }
                
                String html = "<!DOCTYPE html>" +
                    "<html lang=\"es\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "<title>Préstamos por Lector - Biblioteca Comunitaria</title>" +
                    "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                    "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                    "</head>" +
                    "<body>" +
                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                    "<div class=\"container\">" +
                    "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                    "<div class=\"navbar-nav ms-auto\">" +
                    "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                    "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                    "</div>" +
                    "</div>" +
                    "</nav>" +
                    "<div class=\"container mt-4\">" +
                    "<div class=\"row\">" +
                    "<div class=\"col-12\">" +
                    "<h1 class=\"text-white mb-4\">📋 Préstamos por Lector</h1>" +
                    "</div>" +
                    "</div>" +
                    "<div class=\"row\">" +
                    "<div class=\"col-md-6\">" +
                    "<div class=\"card\">" +
                    "<div class=\"card-header bg-info text-white\">" +
                    "<h5 class=\"mb-0\">Seleccionar Lector</h5>" +
                    "</div>" +
                    "<div class=\"card-body\">" +
                    "<form method=\"POST\" action=\"/bibliotecario/prestamos/por-lector\">" +
                    "<div class=\"mb-3\">" +
                    "<label for=\"lector\" class=\"form-label\">Lector:</label>" +
                    "<select class=\"form-select\" id=\"lector\" name=\"lector\" required>" +
                    "<option value=\"\">Seleccione un lector...</option>" +
                    lectoresOptions.toString() +
                    "</select>" +
                    "</div>" +
                    "<button type=\"submit\" class=\"btn btn-info\">Ver Préstamos</button>" +
                    "</form>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "<div class=\"col-md-6\">" +
                    "<div class=\"card\">" +
                    "<div class=\"card-header bg-warning text-white\">" +
                    "<h5 class=\"mb-0\">Estadísticas Generales</h5>" +
                    "</div>" +
                    "<div class=\"card-body\">" +
                    "<p><strong>Total de préstamos:</strong> " + prestamos.size() + "</p>" +
                    "<p><strong>Préstamos activos:</strong> " + prestamos.stream().filter(p -> p.getEstadoP() == EstadoP.ENCURSO).count() + "</p>" +
                    "<p><strong>Préstamos devueltos:</strong> " + prestamos.stream().filter(p -> p.getEstadoP() == EstadoP.DEVUELTO).count() + "</p>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
                
                byte[] htmlBytes = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(200, htmlBytes.length);
                t.getResponseBody().write(htmlBytes);
            } else if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                String emailLector = params.get("lector");
                
                try {
                    Usuario usuario = controlador.obtenerUsuario(emailLector);
                    if (usuario instanceof Lector) {
                        Lector lector = (Lector) usuario;
                        List<Prestamo> prestamos = controlador.getListaPrestamos();
                        List<Prestamo> prestamosLector = new ArrayList<>();
                        
                        for (Prestamo prestamo : prestamos) {
                            if (prestamo.getLector().getEmail().equals(emailLector)) {
                                prestamosLector.add(prestamo);
                            }
                        }
                        
                        StringBuilder prestamosHtml = new StringBuilder();
                        for (Prestamo prestamo : prestamosLector) {
                            String materialInfo = "";
                            if (prestamo.getMaterial() instanceof Libro) {
                                materialInfo = ((Libro) prestamo.getMaterial()).getTitulo();
                            } else if (prestamo.getMaterial() instanceof ArtEspeciales) {
                                materialInfo = ((ArtEspeciales) prestamo.getMaterial()).getDescripcion();
                            }
                            
                            String estadoClass = prestamo.getEstadoP() == EstadoP.ENCURSO ? "bg-warning" : "bg-success";
                            String estadoText = prestamo.getEstadoP() == EstadoP.ENCURSO ? "En Curso" : "Devuelto";
                            
                            prestamosHtml.append("<tr>")
                                        .append("<td>").append(prestamo.getId()).append("</td>")
                                        .append("<td>").append(materialInfo).append("</td>")
                                        .append("<td>").append(prestamo.getFechaSolicitada().getDia()).append("/")
                                        .append(prestamo.getFechaSolicitada().getMes()).append("/")
                                        .append(prestamo.getFechaSolicitada().getAnio()).append("</td>")
                                        .append("<td>").append(prestamo.getFechaDevolucion().getDia()).append("/")
                                        .append(prestamo.getFechaDevolucion().getMes()).append("/")
                                        .append(prestamo.getFechaDevolucion().getAnio()).append("</td>")
                                        .append("<td><span class=\"badge ").append(estadoClass).append("\">").append(estadoText).append("</span></td>")
                                        .append("</tr>");
                        }
                        
                        String html = "<!DOCTYPE html>" +
                            "<html lang=\"es\">" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                            "<title>Préstamos de " + lector.getNombre() + " - Biblioteca Comunitaria</title>" +
                            "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                            "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                            "</head>" +
                            "<body>" +
                            "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                            "<div class=\"container\">" +
                            "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                            "<div class=\"navbar-nav ms-auto\">" +
                            "<a class=\"nav-link\" href=\"/bibliotecario/prestamos/por-lector\">🔙 Volver</a>" +
                            "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                            "</div>" +
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container mt-4\">" +
                            "<div class=\"row\">" +
                            "<div class=\"col-12\">" +
                            "<h1 class=\"text-white mb-4\">📋 Préstamos de " + lector.getNombre() + "</h1>" +
                            "<div class=\"alert alert-info\">" +
                            "<strong>Lector:</strong> " + lector.getNombre() + " (" + lector.getEmail() + ")<br>" +
                            "<strong>Dirección:</strong> " + lector.getDireccion() + "<br>" +
                            "<strong>Zona:</strong> " + lector.getZona() + "<br>" +
                            "<strong>Estado:</strong> " + lector.getEstadoL() + "<br>" +
                            "<strong>Total de préstamos:</strong> " + prestamosLector.size() +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "<div class=\"row\">" +
                            "<div class=\"col-12\">" +
                            "<div class=\"card\">" +
                            "<div class=\"card-header bg-primary text-white\">" +
                            "<h5 class=\"mb-0\">Historial de Préstamos</h5>" +
                            "</div>" +
                            "<div class=\"card-body\">" +
                            "<div class=\"table-responsive\">" +
                            "<table class=\"table table-striped\">" +
                            "<thead>" +
                            "<tr><th>ID</th><th>Material</th><th>Fecha Solicitud</th><th>Fecha Devolución</th><th>Estado</th></tr>" +
                            "</thead>" +
                            "<tbody>" +
                            prestamosHtml.toString() +
                            "</tbody>" +
                            "</table>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</body>" +
                            "</html>";
                        
                        byte[] htmlBytes = html.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                        t.sendResponseHeaders(200, htmlBytes.length);
                        OutputStream os = t.getResponseBody();
                        os.write(htmlBytes);
                        os.close();
                    } else {
                        String response = "Lector no encontrado";
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(400, responseBytes.length);
                        OutputStream os = t.getResponseBody();
                        os.write(responseBytes);
                        os.close();
                    }
                } catch (Exception e) {
                    String response = "Error al obtener préstamos: " + e.getMessage();
                    byte[] responseBytes = response.getBytes("UTF-8");
                    t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                    t.sendResponseHeaders(500, responseBytes.length);
                    OutputStream os = t.getResponseBody();
                    os.write(responseBytes);
                    os.close();
                }
            } else {
                String response = "Método no permitido";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(405, responseBytes.length);
                t.getResponseBody().write(responseBytes);
            }
            t.getResponseBody().close();
        }
    }
    
    // Handler para listar préstamos activos de un lector específico
    static class PrestamosActivosLectorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                if ("GET".equals(t.getRequestMethod())) {
                    // Mostrar formulario para seleccionar lector
                    String query = t.getRequestURI().getQuery();
                    if (query != null && query.contains("lector=")) {
                        String emailLector = query.substring(query.indexOf("lector=") + 7);
                        emailLector = java.net.URLDecoder.decode(emailLector, "UTF-8");
                        
                        Usuario usuario = controlador.obtenerUsuario(emailLector);
                        if (usuario != null && usuario instanceof Lector) {
                            Lector lector = (Lector) usuario;
                            String html = generatePrestamosActivosLectorPage(lector);
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(200, response.length);
                            t.getResponseBody().write(response);
                        } else {
                            String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Lector no encontrado</div>");
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(404, response.length);
                            t.getResponseBody().write(response);
                        }
                    } else {
                        // Mostrar formulario para seleccionar lector
                        String html = generateSelectLectorPage();
                        byte[] response = html.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                        t.sendResponseHeaders(200, response.length);
                        t.getResponseBody().write(response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Error al procesar la solicitud: " + e.getMessage() + "</div>");
                byte[] response = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(500, response.length);
                t.getResponseBody().write(response);
            } finally {
                t.getResponseBody().close();
            }
        }
        
        private String generateSelectLectorPage() {
            List<Lector> lectores = controlador.getListaLectores();
            
            StringBuilder lectoresHtml = new StringBuilder();
            for (Lector lector : lectores) {
                lectoresHtml.append("<option value=\"").append(lector.getEmail()).append("\">")
                    .append(lector.getNombre()).append(" (").append(lector.getEmail()).append(")</option>");
            }
            
            return CompleteWebServer.generateBasePage("Préstamos Activos por Lector", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-user-clock\"></i> Préstamos Activos por Lector</h2>" +
                "<p class=\"text-muted\">Selecciona un lector para ver sus préstamos activos.</p>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<form method=\"GET\" action=\"/bibliotecario/prestamos/activos-lector\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"lector\" class=\"form-label\">Seleccionar Lector:</label>" +
                "<select class=\"form-select\" id=\"lector\" name=\"lector\" required>" +
                "<option value=\"\">-- Selecciona un lector --</option>" +
                lectoresHtml.toString() +
                "</select>" +
                "</div>" +
                "<div class=\"d-flex gap-2\">" +
                "<button type=\"submit\" class=\"btn btn-primary\"><i class=\"fas fa-search\"></i> Ver Préstamos Activos</button>" +
                "<a href=\"/bibliotecario/prestamos\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Volver</a>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>");
        }
        
        private String generatePrestamosActivosLectorPage(Lector lector) {
            List<Prestamo> todosLosPrestamos = controlador.getListaPrestamos();
            List<Prestamo> prestamosActivos = new ArrayList<>();
            
            // Filtrar préstamos activos del lector
            for (Prestamo prestamo : todosLosPrestamos) {
                if (prestamo.getLector().getId().equals(lector.getId()) && 
                    prestamo.getEstadoP() == EstadoP.ENCURSO) {
                    prestamosActivos.add(prestamo);
                }
            }
            
            StringBuilder prestamosHtml = new StringBuilder();
            if (prestamosActivos.isEmpty()) {
                prestamosHtml.append("<tr><td colspan=\"6\" class=\"text-center text-muted\">No tiene préstamos activos</td></tr>");
            } else {
                for (Prestamo prestamo : prestamosActivos) {
                    Material material = prestamo.getMaterial();
                    String materialNombre = "";
                    if (material instanceof Libro) {
                        materialNombre = ((Libro) material).getTitulo();
                    } else if (material instanceof ArtEspeciales) {
                        materialNombre = ((ArtEspeciales) material).getDescripcion();
                    }
                    
                    prestamosHtml.append("<tr>")
                        .append("<td>").append(prestamo.getId()).append("</td>")
                        .append("<td>").append(materialNombre).append("</td>")
                        .append("<td>").append(prestamo.getBibliotecario().getNombre()).append("</td>")
                        .append("<td>").append(prestamo.getFechaSolicitada()).append("</td>")
                        .append("<td>").append(prestamo.getFechaDevolucion()).append("</td>")
                        .append("<td><span class=\"badge bg-warning\">").append(prestamo.getEstadoP()).append("</span></td>")
                        .append("</tr>");
                }
            }
            
            return CompleteWebServer.generateBasePage("Préstamos Activos de " + lector.getNombre(), 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-user-clock\"></i> Préstamos Activos de " + lector.getNombre() + "</h2>" +
                "<div class=\"row mb-3\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card bg-info text-white\">" +
                "<div class=\"card-body\">" +
                "<h5 class=\"card-title\"><i class=\"fas fa-user\"></i> Información del Lector</h5>" +
                "<p class=\"mb-1\"><strong>Nombre:</strong> " + lector.getNombre() + "</p>" +
                "<p class=\"mb-1\"><strong>Email:</strong> " + lector.getEmail() + "</p>" +
                "<p class=\"mb-0\"><strong>Zona:</strong> " + lector.getZona() + "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card bg-primary text-white\">" +
                "<div class=\"card-body\">" +
                "<h5 class=\"card-title\"><i class=\"fas fa-book\"></i> Resumen</h5>" +
                "<p class=\"mb-0\"><strong>Préstamos Activos:</strong> " + prestamosActivos.size() + "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"card\">" +
                "<div class=\"card-header\">" +
                "<h5 class=\"mb-0\"><i class=\"fas fa-list\"></i> Lista de Préstamos Activos</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID Préstamo</th>" +
                "<th>Material</th>" +
                "<th>Bibliotecario</th>" +
                "<th>Fecha Solicitud</th>" +
                "<th>Fecha Devolución</th>" +
                "<th>Estado</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                prestamosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"mt-3\">" +
                "<a href=\"/bibliotecario/prestamos/activos-lector\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Seleccionar Otro Lector</a>" +
                "<a href=\"/bibliotecario/prestamos\" class=\"btn btn-primary ms-2\"><i class=\"fas fa-list\"></i> Ver Todos los Préstamos</a>" +
                "</div>" +
                "</div>");
        }
    }
    
    // Handler para reporte por zona
    static class ReportePorZonaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            List<Lector> lectores = controlador.getListaLectores();
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            
            Map<Zona, Integer> prestamosPorZona = new HashMap<>();
            Map<Zona, Integer> lectoresPorZona = new HashMap<>();
            
            for (Zona zona : Zona.values()) {
                prestamosPorZona.put(zona, 0);
                lectoresPorZona.put(zona, 0);
            }
            
            for (Lector lector : lectores) {
                lectoresPorZona.put(lector.getZona(), lectoresPorZona.get(lector.getZona()) + 1);
            }
            
            for (Prestamo prestamo : prestamos) {
                prestamosPorZona.put(prestamo.getLector().getZona(), prestamosPorZona.get(prestamo.getLector().getZona()) + 1);
            }
            
            StringBuilder reporteHtml = new StringBuilder();
            for (Zona zona : Zona.values()) {
                int numLectores = lectoresPorZona.get(zona);
                int numPrestamos = prestamosPorZona.get(zona);
                double promedio = numLectores > 0 ? (double) numPrestamos / numLectores : 0;
                
                reporteHtml.append("<tr>")
                          .append("<td>").append(zona).append("</td>")
                          .append("<td>").append(numLectores).append("</td>")
                          .append("<td>").append(numPrestamos).append("</td>")
                          .append("<td>").append(String.format("%.2f", promedio)).append("</td>")
                          .append("</tr>");
            }
            
            String html = "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Reporte por Zona - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">📊 Reporte de Préstamos por Zona</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-info text-white\">" +
                "<h5 class=\"mb-0\">Estadísticas por Zona</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr><th>Zona</th><th>Lectores</th><th>Préstamos</th><th>Promedio por Lector</th></tr>" +
                "</thead>" +
                "<tbody>" +
                reporteHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
            
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, html.length());
            t.getResponseBody().write(html.getBytes());
            t.getResponseBody().close();
        }
    }
    
    // Handler para materiales populares
    static class MaterialesPopularesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            List<Material> materiales = controlador.getListaMateriales();
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            
            Map<Material, Integer> prestamosPorMaterial = new HashMap<>();
            for (Material material : materiales) {
                prestamosPorMaterial.put(material, 0);
            }
            
            for (Prestamo prestamo : prestamos) {
                prestamosPorMaterial.put(prestamo.getMaterial(), prestamosPorMaterial.get(prestamo.getMaterial()) + 1);
            }
            
            List<Map.Entry<Material, Integer>> materialesOrdenados = new ArrayList<>(prestamosPorMaterial.entrySet());
            materialesOrdenados.sort((a, b) -> b.getValue().compareTo(a.getValue()));
            
            StringBuilder materialesHtml = new StringBuilder();
            for (Map.Entry<Material, Integer> entry : materialesOrdenados) {
                Material material = entry.getKey();
                int cantidad = entry.getValue();
                
                String materialInfo = "";
                if (material instanceof Libro) {
                    materialInfo = ((Libro) material).getTitulo() + " (Libro)";
                } else if (material instanceof ArtEspeciales) {
                    materialInfo = ((ArtEspeciales) material).getDescripcion() + " (Artículo Especial)";
                }
                
                String badgeClass = cantidad > 5 ? "bg-danger" : cantidad > 2 ? "bg-warning" : "bg-success";
                
                materialesHtml.append("<tr>")
                            .append("<td>").append(material.getId()).append("</td>")
                            .append("<td>").append(materialInfo).append("</td>")
                            .append("<td><span class=\"badge ").append(badgeClass).append("\">").append(cantidad).append("</span></td>")
                            .append("</tr>");
            }
            
            String html = "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Materiales Populares - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">📈 Materiales Más Solicitados</h1>" +
                "<div class=\"alert alert-info\">" +
                "<strong>Leyenda:</strong> " +
                "<span class=\"badge bg-danger\">Alto (5+ préstamos)</span> " +
                "<span class=\"badge bg-warning\">Medio (3-5 préstamos)</span> " +
                "<span class=\"badge bg-success\">Bajo (0-2 préstamos)</span>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white\">" +
                "<h5 class=\"mb-0\">Ranking de Materiales</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr><th>ID</th><th>Material</th><th>Préstamos</th></tr>" +
                "</thead>" +
                "<tbody>" +
                materialesHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
            
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, html.length());
            t.getResponseBody().write(html.getBytes());
            t.getResponseBody().close();
        }
    }
    
    // Handler para historial del bibliotecario
    static class HistorialBibliotecarioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            
            StringBuilder bibliotecariosOptions = new StringBuilder();
            for (Bibliotecario bibliotecario : bibliotecarios) {
                bibliotecariosOptions.append("<option value=\"").append(bibliotecario.getEmail()).append("\">")
                                    .append(bibliotecario.getNombre()).append(" (").append(bibliotecario.getEmail()).append(")")
                                    .append("</option>");
            }
            
            String html = "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Historial de Bibliotecarios - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">📚 Historial de Bibliotecarios</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-danger text-white\">" +
                "<h5 class=\"mb-0\">Seleccionar Bibliotecario</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<form method=\"POST\" action=\"/bibliotecario/historial\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"bibliotecario\" class=\"form-label\">Bibliotecario:</label>" +
                "<select class=\"form-select\" id=\"bibliotecario\" name=\"bibliotecario\" required>" +
                "<option value=\"\">Seleccione un bibliotecario...</option>" +
                bibliotecariosOptions.toString() +
                "</select>" +
                "</div>" +
                "<button type=\"submit\" class=\"btn btn-danger\">Ver Historial</button>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-warning text-white\">" +
                "<h5 class=\"mb-0\">Estadísticas Generales</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p><strong>Total de bibliotecarios:</strong> " + bibliotecarios.size() + "</p>" +
                "<p><strong>Total de préstamos gestionados:</strong> " + prestamos.size() + "</p>" +
                "<p><strong>Promedio por bibliotecario:</strong> " + (bibliotecarios.size() > 0 ? prestamos.size() / bibliotecarios.size() : 0) + "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
            
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, html.length());
            t.getResponseBody().write(html.getBytes());
            t.getResponseBody().close();
        }
    }
    
    // Handler para materiales por fecha
    static class MaterialesPorFechaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("GET".equals(t.getRequestMethod())) {
                String html = "<!DOCTYPE html>" +
                    "<html lang=\"es\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "<title>Materiales por Fecha - Biblioteca Comunitaria</title>" +
                    "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                    "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                    "</head>" +
                    "<body>" +
                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                    "<div class=\"container\">" +
                    "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                    "<div class=\"navbar-nav ms-auto\">" +
                    "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                    "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                    "</div>" +
                    "</div>" +
                    "</nav>" +
                    "<div class=\"container mt-4\">" +
                    "<div class=\"row\">" +
                    "<div class=\"col-12\">" +
                    "<h1 class=\"text-white mb-4\">📅 Materiales por Rango de Fechas</h1>" +
                    "</div>" +
                    "</div>" +
                    "<div class=\"row\">" +
                    "<div class=\"col-md-8\">" +
                    "<div class=\"card\">" +
                    "<div class=\"card-header bg-info text-white\">" +
                    "<h5 class=\"mb-0\">Filtrar por Fecha</h5>" +
                    "</div>" +
                    "<div class=\"card-body\">" +
                    "<form method=\"POST\" action=\"/bibliotecario/materiales/por-fecha\">" +
                    "<div class=\"row\">" +
                    "<div class=\"col-md-6\">" +
                    "<div class=\"mb-3\">" +
                    "<label for=\"fechaInicio\" class=\"form-label\">Fecha de Inicio:</label>" +
                    "<input type=\"date\" class=\"form-control\" id=\"fechaInicio\" name=\"fechaInicio\" required>" +
                    "</div>" +
                    "</div>" +
                    "<div class=\"col-md-6\">" +
                    "<div class=\"mb-3\">" +
                    "<label for=\"fechaFin\" class=\"form-label\">Fecha de Fin:</label>" +
                    "<input type=\"date\" class=\"form-control\" id=\"fechaFin\" name=\"fechaFin\" required>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "<button type=\"submit\" class=\"btn btn-info\">Buscar Materiales</button>" +
                    "</form>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
                
                byte[] htmlBytes = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(200, htmlBytes.length);
                t.getResponseBody().write(htmlBytes);
                t.getResponseBody().close();
            } else if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                String fechaInicio = params.get("fechaInicio");
                String fechaFin = params.get("fechaFin");
                
                try {
                    // Parsear fechas del formulario
                    String[] fechaInicioParts = fechaInicio.split("-");
                    String[] fechaFinParts = fechaFin.split("-");
                    
                    DTFecha fechaInicioDT = new DTFecha(
                        Integer.parseInt(fechaInicioParts[2]), // día
                        Integer.parseInt(fechaInicioParts[1]), // mes
                        Integer.parseInt(fechaInicioParts[0])  // año
                    );
                    
                    DTFecha fechaFinDT = new DTFecha(
                        Integer.parseInt(fechaFinParts[2]), // día
                        Integer.parseInt(fechaFinParts[1]), // mes
                        Integer.parseInt(fechaFinParts[0])  // año
                    );
                    
                    // Obtener materiales filtrados por fecha
                    List<Material> materialesFiltrados = controlador.obtenerDonacionesEnRango(fechaInicioDT, fechaFinDT);
                    
                    StringBuilder materialesHtml = new StringBuilder();
                    for (Material material : materialesFiltrados) {
                        String materialInfo = "";
                        if (material instanceof Libro) {
                            materialInfo = ((Libro) material).getTitulo() + " (" + ((Libro) material).getCantPaginas() + " páginas)";
                        } else if (material instanceof ArtEspeciales) {
                            ArtEspeciales art = (ArtEspeciales) material;
                            DTDimensiones dim = art.getDimensiones();
                            materialInfo = art.getDescripcion() + " (" + art.getPeso() + " kg, " + 
                                         dim.getAncho() + "x" + dim.getAlto() + "x" + dim.getProfundidad() + " cm)";
                        }
                        
                        materialesHtml.append("<tr>")
                                    .append("<td>").append(material.getId()).append("</td>")
                                    .append("<td>").append(materialInfo).append("</td>")
                                    .append("<td>").append(material.getFechaIngreso().getDia()).append("/")
                                    .append(material.getFechaIngreso().getMes()).append("/")
                                    .append(material.getFechaIngreso().getAnio()).append("</td>")
                                    .append("</tr>");
                    }
                    
                    String html = "<!DOCTYPE html>" +
                        "<html lang=\"es\">" +
                        "<head>" +
                        "<meta charset=\"UTF-8\">" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "<title>Materiales Filtrados - Biblioteca Comunitaria</title>" +
                        "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                        "<style>body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }</style>" +
                        "</head>" +
                        "<body>" +
                        "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                        "<div class=\"container\">" +
                        "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                        "<div class=\"navbar-nav ms-auto\">" +
                        "<a class=\"nav-link\" href=\"/bibliotecario/materiales/por-fecha\">🔙 Volver</a>" +
                        "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                        "</div>" +
                        "</div>" +
                        "</nav>" +
                        "<div class=\"container mt-4\">" +
                        "<div class=\"row\">" +
                        "<div class=\"col-12\">" +
                        "<h1 class=\"text-white mb-4\">📅 Materiales del " + fechaInicio + " al " + fechaFin + "</h1>" +
                        "<div class=\"alert alert-info\">" +
                        "<strong>Rango de fechas:</strong> " + fechaInicio + " - " + fechaFin + "<br>" +
                        "<strong>Total encontrados:</strong> " + materialesFiltrados.size() + " materiales" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "<div class=\"row\">" +
                        "<div class=\"col-12\">" +
                        "<div class=\"card\">" +
                        "<div class=\"card-header bg-success text-white\">" +
                        "<h5 class=\"mb-0\">Materiales Registrados</h5>" +
                        "</div>" +
                        "<div class=\"card-body\">" +
                        "<div class=\"table-responsive\">" +
                        "<table class=\"table table-striped\">" +
                        "<thead>" +
                        "<tr><th>ID</th><th>Material</th><th>Fecha de Ingreso</th></tr>" +
                        "</thead>" +
                        "<tbody>" +
                        materialesHtml.toString() +
                        "</tbody>" +
                        "</table>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
                    
                    byte[] htmlBytes = html.getBytes("UTF-8");
                    t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                    t.sendResponseHeaders(200, htmlBytes.length);
                    t.getResponseBody().write(htmlBytes);
                } catch (Exception e) {
                    String response = "Error al filtrar materiales: " + e.getMessage();
                    byte[] responseBytes = response.getBytes("UTF-8");
                    t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                    t.sendResponseHeaders(500, responseBytes.length);
                    t.getResponseBody().write(responseBytes);
                }
            } else {
                String response = "Método no permitido";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(405, responseBytes.length);
                t.getResponseBody().write(responseBytes);
            }
            t.getResponseBody().close();
        }
    }
    
    // Handler para logout
    static class LogoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Limpiar sesión
            String cookieHeader = t.getRequestHeaders().getFirst("Cookie");
            if (cookieHeader != null) {
                String[] cookies = cookieHeader.split(";");
                for (String cookie : cookies) {
                    String[] parts = cookie.trim().split("=");
                    if (parts.length == 2 && parts[0].equals("session")) {
                        String sessionToken = parts[1];
                        sesiones.remove(sessionToken);
                        System.out.println("🔓 Sesión cerrada para token: " + sessionToken);
                        break;
                    }
                }
            }
            
            // Redirigir al login y limpiar cookie
            t.getResponseHeaders().set("Location", "/");
            t.getResponseHeaders().set("Set-Cookie", "session=; Path=/; HttpOnly; Max-Age=0");
            t.sendResponseHeaders(302, 0);
            t.getResponseBody().close();
        }
    }
    
    // Handler para dashboard de bibliotecario
    static class BibliotecarioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateBibliotecarioDashboard();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateBibliotecarioDashboard() {
            // Obtener estadísticas del sistema
            int totalBibliotecarios = controlador.obtenerCantidadBibliotecarios();
            int totalLectores = controlador.obtenerCantidadLectores();
            int totalLibros = controlador.obtenerCantidadLibros();
            int totalArtEspeciales = controlador.obtenerCantidadArtEspeciales();
            int totalPrestamos = controlador.obtenerCantidadPrestamos();
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Dashboard Bibliotecario - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                ".stat-card { background: linear-gradient(45deg, #667eea, #764ba2); color: white; }" +
                ".navbar { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/\">🏠 Inicio</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">👨‍💼 Dashboard Bibliotecario</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-md-3\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-people-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalLectores + "</h3>" +
                "<p class=\"mb-0\">Lectores</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-3\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-book-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalLibros + "</h3>" +
                "<p class=\"mb-0\">Libros</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-3\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-palette-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalArtEspeciales + "</h3>" +
                "<p class=\"mb-0\">Artículos Especiales</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-3\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-arrow-repeat\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalPrestamos + "</h3>" +
                "<p class=\"mb-0\">Préstamos</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12 col-md-4 mb-3\">" +
                "<div class=\"card h-100\">" +
                "<div class=\"card-header bg-primary text-white\">" +
                "<h5 class=\"mb-0\">👥 Gestión de Usuarios</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p>Gestionar lectores y bibliotecarios</p>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/bibliotecario/usuarios\" class=\"btn btn-primary\">Ver Usuarios</a>" +
                "<a href=\"/bibliotecario/usuarios/nuevo\" class=\"btn btn-outline-primary\">Nuevo Usuario</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-12 col-md-4 mb-3\">" +
                "<div class=\"card h-100\">" +
                "<div class=\"card-header bg-success text-white\">" +
                "<h5 class=\"mb-0\">📚 Gestión de Materiales</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p>Registrar y gestionar materiales</p>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/bibliotecario/materiales\" class=\"btn btn-success\">Ver Materiales</a>" +
                "<a href=\"/bibliotecario/materiales/nuevo\" class=\"btn btn-outline-success\">Nuevo Material</a>" +
                "<a href=\"/bibliotecario/materiales/por-fecha\" class=\"btn btn-outline-info\">Por Fecha</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-12 col-md-4 mb-3\">" +
                "<div class=\"card h-100\">" +
                "<div class=\"card-header bg-warning text-white\">" +
                "<h5 class=\"mb-0\">🔄 Gestión de Préstamos</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p>Gestionar préstamos y devoluciones</p>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/bibliotecario/prestamos\" class=\"btn btn-warning\">Ver Préstamos</a>" +
                "<a href=\"/bibliotecario/prestamos/activos\" class=\"btn btn-outline-warning\">Préstamos Activos</a>" +
                "<a href=\"/bibliotecario/prestamos/por-lector\" class=\"btn btn-outline-info\">Préstamos por Lector</a>" +
                "<a href=\"/bibliotecario/prestamos/editar\" class=\"btn btn-outline-primary\">Editar Préstamo</a>" +
                "<a href=\"/bibliotecario/prestamos/activos-lector\" class=\"btn btn-outline-success\">Activos por Lector</a>" +
                "<a href=\"/bibliotecario/materiales/editar\" class=\"btn btn-outline-warning\">Editar Material</a>" +
                "<a href=\"/bibliotecario/usuarios/editar\" class=\"btn btn-outline-secondary\">Editar Usuario</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mt-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-danger text-white\">" +
                "<h5 class=\"mb-0\">👑 Panel de Administrador</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p>Reportes, estadísticas y herramientas avanzadas de administración</p>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/bibliotecario/admin\" class=\"btn btn-danger\">Acceder al Panel</a>" +
                "<a href=\"/bibliotecario/historial\" class=\"btn btn-outline-danger\">Mi Historial</a>" +
                "<a href=\"/bibliotecario/reportes/por-zona\" class=\"btn btn-outline-info\">Reporte por Zona</a>" +
                "<a href=\"/bibliotecario/reportes/materiales-populares\" class=\"btn btn-outline-success\">Materiales Populares</a>" +
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
    
    // Handler para dashboard de lector
    static class LectorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateLectorDashboard();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateLectorDashboard() {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Dashboard Lector - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: #f8f9fa; }" +
                ".card { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); border: none; }" +
                ".navbar { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-success\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/lector\">" +
                "<i class=\"bi bi-book\"></i> Dashboard Lector" +
                "</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/logout\">" +
                "<i class=\"bi bi-box-arrow-right\"></i> Cerrar Sesión" +
                "</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6 mb-4\">" +
                "<div class=\"card h-100\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-search text-primary\" style=\"font-size: 3rem;\"></i>" +
                "<h5 class=\"card-title mt-3\">Consultar Materiales</h5>" +
                "<p class=\"card-text\">Buscar libros y artículos disponibles</p>" +
                "<a href=\"/lector/materiales\" class=\"btn btn-primary\">" +
                "<i class=\"bi bi-arrow-right\"></i> Consultar" +
                "</a>" +
                "</div>" +
                "</div>" +
                "</div>" +
                
                "<div class=\"col-md-6 mb-4\">" +
                "<div class=\"card h-100\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-list-check text-warning\" style=\"font-size: 3rem;\"></i>" +
                "<h5 class=\"card-title mt-3\">Mis Préstamos</h5>" +
                "<p class=\"card-text\">Ver el estado de mis préstamos</p>" +
                "<div class=\"d-grid gap-2\">" +
                "<a href=\"/lector/prestamos\" class=\"btn btn-warning\">" +
                "<i class=\"bi bi-arrow-right\"></i> Ver Préstamos" +
                "</a>" +
                "<a href=\"/lector/prestamos/nuevo\" class=\"btn btn-outline-warning\">" +
                "<i class=\"bi bi-plus-circle\"></i> Solicitar Préstamo" +
                "</a>" +
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
    
    // Handlers placeholder para las demás funcionalidades
    static class GestionUsuariosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateGestionUsuariosPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateGestionUsuariosPage() {
            // Obtener listas de usuarios
            List<Lector> lectores = controlador.getListaLectores();
            List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
            
            StringBuilder lectoresHtml = new StringBuilder();
            for (Lector lector : lectores) {
                lectoresHtml.append("<tr>")
                    .append("<td>").append(lector.getNombre()).append("</td>")
                    .append("<td>").append(lector.getEmail()).append("</td>")
                    .append("<td>").append(lector.getDireccion()).append("</td>")
                    .append("<td>").append(lector.getZona()).append("</td>")
                    .append("<td><span class=\"badge bg-").append(lector.getEstadoL() == EstadoL.ACTIVO ? "success" : "danger").append("\">")
                    .append(lector.getEstadoL()).append("</span></td>")
                    .append("<td>")
                    .append("<div class=\"action-buttons\">")
                    .append("<button class=\"btn btn-sm btn-warning\" onclick=\"cambiarEstado('").append(lector.getEmail()).append("')\">Estado</button>")
                    .append("<button class=\"btn btn-sm btn-info\" onclick=\"cambiarZona('").append(lector.getEmail()).append("')\">Zona</button>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            StringBuilder bibliotecariosHtml = new StringBuilder();
            for (Bibliotecario bibliotecario : bibliotecarios) {
                bibliotecariosHtml.append("<tr>")
                    .append("<td>").append(bibliotecario.getNombre()).append("</td>")
                    .append("<td>").append(bibliotecario.getEmail()).append("</td>")
                    .append("<td>").append(bibliotecario.getNroEmpleado()).append("</td>")
                    .append("<td><span class=\"badge bg-success\">Activo</span></td>")
                    .append("<td>")
                    .append("<div class=\"action-buttons\">")
                    .append("<button class=\"btn btn-sm btn-primary\" onclick=\"editarBibliotecario('").append(bibliotecario.getEmail()).append("')\">Editar</button>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Gestión de Usuarios - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">👥 Gestión de Usuarios</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white d-flex justify-content-between align-items-center\">" +
                "<h5 class=\"mb-0\">📚 Lectores</h5>" +
                "<a href=\"/bibliotecario/usuarios/registrar-lector\" class=\"btn btn-light btn-sm\">+ Nuevo Lector</a>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>Nombre</th>" +
                "<th>Email</th>" +
                "<th>Dirección</th>" +
                "<th>Zona</th>" +
                "<th>Estado</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                lectoresHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white d-flex justify-content-between align-items-center\">" +
                "<h5 class=\"mb-0\">👨‍💼 Bibliotecarios</h5>" +
                "<a href=\"/bibliotecario/usuarios/registrar-bibliotecario\" class=\"btn btn-light btn-sm\">+ Nuevo Bibliotecario</a>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>Nombre</th>" +
                "<th>Email</th>" +
                "<th>Nro. Empleado</th>" +
                "<th>Estado</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                bibliotecariosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "function cambiarEstado(email) {" +
                "    if (confirm('¿Estás seguro de cambiar el estado de este lector?')) {" +
                "        fetch('/bibliotecario/usuarios/cambiar-estado', {" +
                "            method: 'POST'," +
                "            headers: {'Content-Type': 'application/x-www-form-urlencoded'}," +
                "            body: 'email=' + encodeURIComponent(email)" +
                "        }).then(response => response.text()).then(data => {" +
                "            alert('Estado cambiado exitosamente');" +
                "            location.reload();" +
                "        }).catch(error => {" +
                "            alert('Error al cambiar estado: ' + error);" +
                "        });" +
                "    }" +
                "}" +
                "function cambiarZona(email) {" +
                "    // Crear modal dinámico para seleccionar zona" +
                "    const modalHtml = `" +
                "        <div class='modal fade' id='zonaModal' tabindex='-1'>" +
                "            <div class='modal-dialog'>" +
                "                <div class='modal-content'>" +
                "                    <div class='modal-header'>" +
                "                        <h5 class='modal-title'>Cambiar Zona del Lector</h5>" +
                "                        <button type='button' class='btn-close' data-bs-dismiss='modal'></button>" +
                "                    </div>" +
                "                    <div class='modal-body'>" +
                "                        <p>Selecciona la nueva zona para <strong>\" + email + \"</strong>:</p>" +
                "                        <select class='form-select' id='nuevaZonaSelect'>" +
                "                            <option value=''>Selecciona una zona...</option>" +
                "                            <option value='BIBLOTECA_CENTRAL'>Biblioteca Central</option>" +
                "                            <option value='SUCURSAL_ESTE'>Sucursal Este</option>" +
                "                            <option value='SUCURSAL_OESTE'>Sucursal Oeste</option>" +
                "                            <option value='BIBLOTECA_INFANTIL'>Biblioteca Infantil</option>" +
                "                            <option value='ARCHIVO_GENERAL'>Archivo General</option>" +
                "                        </select>" +
                "                    </div>" +
                "                    <div class='modal-footer'>" +
                "                        <button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>Cancelar</button>" +
                "                        <button type='button' class='btn btn-primary' onclick='confirmarCambioZona()'>Cambiar Zona</button>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "        </div>" +
                "    `;" +
                "    " +
                "    // Remover modal anterior si existe" +
                "    const modalAnterior = document.getElementById('zonaModal');" +
                "    if (modalAnterior) {" +
                "        modalAnterior.remove();" +
                "    }" +
                "    " +
                "    // Agregar modal al body" +
                "    document.body.insertAdjacentHTML('beforeend', modalHtml);" +
                "    " +
                "    // Mostrar modal" +
                "    const modal = new bootstrap.Modal(document.getElementById('zonaModal'));" +
                "    modal.show();" +
                "    " +
                "    // Almacenar email para uso posterior" +
                "    window.lectorEmailActual = email;" +
                "    " +
                "    // Limpiar modal cuando se cierre" +
                "    document.getElementById('zonaModal').addEventListener('hidden.bs.modal', function() {" +
                "        this.remove();" +
                "    });" +
                "}" +
                "" +
                "function confirmarCambioZona() {" +
                "    const nuevaZona = document.getElementById('nuevaZonaSelect').value;" +
                "    const email = window.lectorEmailActual;" +
                "    " +
                "    if (!nuevaZona) {" +
                "        alert('Por favor selecciona una zona.');" +
                "        return;" +
                "    }" +
                "    " +
                "    fetch('/bibliotecario/usuarios/cambiar-zona', {" +
                "        method: 'POST'," +
                "        headers: {'Content-Type': 'application/x-www-form-urlencoded'}," +
                "        body: 'email=' + encodeURIComponent(email) + '&zona=' + encodeURIComponent(nuevaZona)" +
                "    }).then(response => response.text()).then(data => {" +
                "        alert('Zona cambiada exitosamente');" +
                "        location.reload();" +
                "    }).catch(error => {" +
                "        alert('Error al cambiar zona: ' + error);" +
                "    });" +
                "}" +
                "function editarBibliotecario(email) {" +
                "    alert('Funcionalidad de edición en desarrollo para: ' + email);" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para nuevo usuario
    static class NuevoUsuarioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateNuevoUsuarioPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateNuevoUsuarioPage() {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Nuevo Usuario - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/usuarios\">👥 Usuarios</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-8\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white\">" +
                "<h5 class=\"mb-0\">➕ Nuevo Usuario</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<form>" +
                "<div class=\"row\">" +
                "<div class=\"col-12 col-md-6 mb-3\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"nombre\" class=\"form-label\">Nombre Completo</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"nombre\" required>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-12 col-md-6 mb-3\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"email\" class=\"form-label\">Email</label>" +
                "<input type=\"email\" class=\"form-control\" id=\"email\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"tipo\" class=\"form-label\">Tipo de Usuario</label>" +
                "<select class=\"form-select\" id=\"tipo\" onchange=\"toggleFields()\">" +
                "<option value=\"lector\">Lector</option>" +
                "<option value=\"bibliotecario\">Bibliotecario</option>" +
                "</select>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\" id=\"nroEmpleadoField\" style=\"display: none;\">" +
                "<label for=\"nroEmpleado\" class=\"form-label\">Número de Empleado</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"nroEmpleado\">" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\" id=\"lectorFields\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"direccion\" class=\"form-label\">Dirección</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"direccion\">" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"zona\" class=\"form-label\">Zona</label>" +
                "<select class=\"form-select\" id=\"zona\">" +
                "<option value=\"BIBLOTECA_CENTRAL\">Biblioteca Central</option>" +
                "<option value=\"SUCURSAL_ESTE\">Sucursal Este</option>" +
                "<option value=\"SUCURSAL_OESTE\">Sucursal Oeste</option>" +
                "</select>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-grid gap-2 d-md-flex justify-content-md-end\">" +
                "<a href=\"/bibliotecario/usuarios\" class=\"btn btn-secondary me-md-2\">Cancelar</a>" +
                "<button type=\"submit\" class=\"btn btn-primary\">Crear Usuario</button>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "function toggleFields() {" +
                "    const tipo = document.getElementById('tipo').value;" +
                "    const lectorFields = document.getElementById('lectorFields');" +
                "    const nroEmpleadoField = document.getElementById('nroEmpleadoField');" +
                "    " +
                "    if (tipo === 'bibliotecario') {" +
                "        lectorFields.style.display = 'none';" +
                "        nroEmpleadoField.style.display = 'block';" +
                "    } else {" +
                "        lectorFields.style.display = 'block';" +
                "        nroEmpleadoField.style.display = 'none';" +
                "    }" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
    
    static class GestionMaterialesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generateGestionMaterialesPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateGestionMaterialesPage() {
            // Obtener listas de materiales
            List<Libro> libros = controlador.obtenerTodosLosLibros();
            List<ArtEspeciales> artEspeciales = controlador.obtenerTodosLosArtEspeciales();
            
            StringBuilder librosHtml = new StringBuilder();
            for (Libro libro : libros) {
                // Verificar si el material está disponible
                boolean disponible = controlador.estaMaterialDisponible(libro);
                String estadoBadge = disponible ? 
                    "<span class=\"badge bg-success\">Disponible</span>" : 
                    "<span class=\"badge bg-warning\">En préstamo</span>";
                
                librosHtml.append("<tr>")
                    .append("<td>").append(libro.getId()).append("</td>")
                    .append("<td>").append(libro.getTitulo()).append("</td>")
                    .append("<td>").append(libro.getCantPaginas()).append(" páginas</td>")
                    .append("<td>").append(libro.getFechaIngreso()).append("</td>")
                    .append("<td>").append(estadoBadge).append("</td>")
                    .append("<td>")
                    .append("<div class=\"action-buttons\">")
                    .append("<button class=\"btn btn-sm btn-primary\" onclick=\"editarLibro(").append(libro.getId()).append(")\">Editar</button>")
                    .append("<button class=\"btn btn-sm btn-danger\" onclick=\"eliminarLibro(").append(libro.getId()).append(")\">Eliminar</button>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            StringBuilder artEspecialesHtml = new StringBuilder();
            for (ArtEspeciales art : artEspeciales) {
                // Verificar si el material está disponible
                boolean disponible = controlador.estaMaterialDisponible(art);
                String estadoBadge = disponible ? 
                    "<span class=\"badge bg-success\">Disponible</span>" : 
                    "<span class=\"badge bg-warning\">En préstamo</span>";
                
                artEspecialesHtml.append("<tr>")
                    .append("<td>").append(art.getId()).append("</td>")
                    .append("<td>").append(art.getDescripcion()).append("</td>")
                    .append("<td>").append(art.getPeso()).append(" kg</td>")
                    .append("<td>").append(art.getDimensiones().getAncho()).append("x").append(art.getDimensiones().getAlto()).append("x").append(art.getDimensiones().getProfundidad()).append(" cm</td>")
                    .append("<td>").append(art.getFechaIngreso()).append("</td>")
                    .append("<td>").append(estadoBadge).append("</td>")
                    .append("<td>")
                    .append("<div class=\"action-buttons\">")
                    .append("<button class=\"btn btn-sm btn-primary\" onclick=\"editarArtEspecial(").append(art.getId()).append(")\">Editar</button>")
                    .append("<button class=\"btn btn-sm btn-danger\" onclick=\"eliminarArtEspecial(").append(art.getId()).append(")\">Eliminar</button>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Gestión de Materiales - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/usuarios\">👥 Usuarios</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">📚 Gestión de Materiales</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white d-flex justify-content-between align-items-center\">" +
                "<h5 class=\"mb-0\">📖 Libros</h5>" +
                "<a href=\"/bibliotecario/materiales/registrar-donacion\" class=\"btn btn-light btn-sm\">+ Nuevo Libro</a>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Título</th>" +
                "<th>Páginas</th>" +
                "<th>Fecha Ingreso</th>" +
                "<th>Estado</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                librosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white d-flex justify-content-between align-items-center\">" +
                "<h5 class=\"mb-0\">🎨 Artículos Especiales</h5>" +
                "<a href=\"/bibliotecario/materiales/registrar-donacion\" class=\"btn btn-light btn-sm\">+ Nuevo Artículo</a>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Descripción</th>" +
                "<th>Peso</th>" +
                "<th>Dimensiones</th>" +
                "<th>Fecha Ingreso</th>" +
                "<th>Estado</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                artEspecialesHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "function editarLibro(id) {" +
                "    alert('Funcionalidad de edición en desarrollo para libro ID: ' + id);" +
                "}" +
                "function eliminarLibro(id) {" +
                "    if (confirm('¿Estás seguro de eliminar este libro?')) {" +
                "        alert('Funcionalidad de eliminación en desarrollo para libro ID: ' + id);" +
                "    }" +
                "}" +
                "function editarArtEspecial(id) {" +
                "    alert('Funcionalidad de edición en desarrollo para artículo ID: ' + id);" +
                "}" +
                "function eliminarArtEspecial(id) {" +
                "    if (confirm('¿Estás seguro de eliminar este artículo especial?')) {" +
                "        alert('Funcionalidad de eliminación en desarrollo para artículo ID: ' + id);" +
                "    }" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
    
    static class GestionPrestamosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                String response = generateGestionPrestamosPage();
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(200, responseBytes.length);
                OutputStream os = t.getResponseBody();
                os.write(responseBytes);
                os.close();
            } catch (Exception e) {
                System.err.println("Error en GestionPrestamosHandler: " + e.getMessage());
                e.printStackTrace();
                String errorResponse = "Error interno del servidor: " + e.getMessage();
                byte[] errorBytes = errorResponse.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(500, errorBytes.length);
                OutputStream os = t.getResponseBody();
                os.write(errorBytes);
                os.close();
            }
        }
        
        private String generateGestionPrestamosPage() {
            // Obtener lista de préstamos
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            
            // Generar tabla de préstamos
            StringBuilder prestamosHtml = new StringBuilder();
            for (Prestamo prestamo : prestamos) {
                String materialDesc = "";
                Material material = prestamo.getMaterial();
                try {
                    if (material instanceof Libro) {
                        materialDesc = ((Libro) material).getTitulo();
                    } else if (material instanceof ArtEspeciales) {
                        materialDesc = ((ArtEspeciales) material).getDescripcion();
                    } else {
                        materialDesc = "Material ID: " + material.getId();
                    }
                } catch (Exception e) {
                    materialDesc = "Material ID: " + material.getId();
                }
                
                prestamosHtml.append("<tr>")
                    .append("<td>").append(prestamo.getId()).append("</td>")
                    .append("<td>").append(materialDesc).append("</td>")
                    .append("<td>").append(prestamo.getLector() != null ? prestamo.getLector().getNombre() : "N/A").append("</td>")
                    .append("<td>").append(prestamo.getBibliotecario() != null ? prestamo.getBibliotecario().getNombre() : "N/A").append("</td>")
                    .append("<td>").append(prestamo.getFechaSolicitada()).append("</td>")
                    .append("<td>").append(prestamo.getFechaDevolucion()).append("</td>")
                    .append("<td><span class=\"badge bg-").append(prestamo.getEstadoP() == EstadoP.ENCURSO ? "warning" : "success").append("\">")
                    .append(prestamo.getEstadoP()).append("</span></td>")
                    .append("<td>")
                    .append("<div class=\"action-buttons\">")
                    .append("<button class=\"btn btn-sm btn-primary\" onclick=\"editarPrestamo(").append(prestamo.getId()).append(")\">Editar</button>")
                    .append("<button class=\"btn btn-sm btn-").append(prestamo.getEstadoP() == EstadoP.ENCURSO ? "success" : "warning").append("\" onclick=\"cambiarEstadoPrestamo(").append(prestamo.getId()).append(")\">")
                    .append(prestamo.getEstadoP() == EstadoP.ENCURSO ? "Devolver" : "En Curso")
                    .append("</button>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Gestión de Préstamos - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/usuarios\">👥 Usuarios</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/materiales\">📚 Materiales</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">🔄 Gestión de Préstamos</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-info text-white d-flex justify-content-between align-items-center\">" +
                "<h5 class=\"mb-0\">🔍 Gestión de Préstamos</h5>" +
                "<div>" +
                "<a href=\"/bibliotecario/prestamos/por-lector\" class=\"btn btn-light btn-sm me-2\">📋 Por Lector</a>" +
                "<a href=\"/bibliotecario/prestamos/activos\" class=\"btn btn-light btn-sm\">⏰ Activos</a>" +
                "</div>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p class=\"mb-0\">Gestiona el estado de los préstamos y consulta información detallada de los lectores.</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-warning text-white\">" +
                "<h5 class=\"mb-0\">📋 Lista de Préstamos</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Material</th>" +
                "<th>Lector</th>" +
                "<th>Bibliotecario</th>" +
                "<th>Fecha Solicitud</th>" +
                "<th>Fecha Devolución</th>" +
                "<th>Estado</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                prestamosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "function editarPrestamo(id) {" +
                "    alert('Funcionalidad de edición en desarrollo para préstamo ID: ' + id);" +
                "}" +
                "function cambiarEstadoPrestamo(id) {" +
                "    if (confirm('¿Estás seguro de cambiar el estado de este préstamo?')) {" +
                "        fetch('/bibliotecario/prestamos/cambiar-estado', {" +
                "            method: 'POST'," +
                "            headers: {'Content-Type': 'application/x-www-form-urlencoded'}," +
                "            body: 'id=' + id" +
                "        }).then(response => response.text()).then(data => {" +
                "            alert('Estado del préstamo cambiado exitosamente');" +
                "            location.reload();" +
                "        }).catch(error => {" +
                "            alert('Error al cambiar estado: ' + error);" +
                "        });" +
                "    }" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para editar préstamos
    static class EditarPrestamoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                if ("GET".equals(t.getRequestMethod())) {
                    // Mostrar formulario de edición
                    String query = t.getRequestURI().getQuery();
                    if (query != null && query.contains("id=")) {
                        String idStr = query.substring(query.indexOf("id=") + 3);
                        int id = Integer.parseInt(idStr);
                        
                        Prestamo prestamo = controlador.obtenerPrestamo(id);
                        if (prestamo != null) {
                            String html = generateEditarPrestamoPage(prestamo);
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(200, response.length);
                            t.getResponseBody().write(response);
                        } else {
                            String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Préstamo no encontrado</div>");
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(404, response.length);
                            t.getResponseBody().write(response);
                        }
                    } else {
                        // Listar préstamos para seleccionar uno
                        String html = generateListaPrestamosParaEditarPage();
                        byte[] response = html.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                        t.sendResponseHeaders(200, response.length);
                        t.getResponseBody().write(response);
                    }
                } else if ("POST".equals(t.getRequestMethod())) {
                    // Procesar actualización completa
                    String requestBody = new String(t.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(requestBody);
                    
                    int id = Integer.parseInt(params.get("id"));
                    
                    // Obtener nuevos valores del formulario
                    String nuevoMaterialIdStr = params.get("materialId");
                    String nuevoLectorEmail = params.get("lectorEmail");
                    String nuevoBibliotecarioEmail = params.get("bibliotecarioEmail");
                    String nuevoEstadoStr = params.get("estado");
                    String fechaDevolucionStr = params.get("fechaDevolucion");
                    String fechaSolicitudStr = params.get("fechaSolicitud");
                    
                    Prestamo prestamo = controlador.obtenerPrestamo(id);
                    if (prestamo != null) {
                        // Obtener objetos actualizados
                        Material nuevoMaterial = prestamo.getMaterial();
                        Lector nuevoLector = prestamo.getLector();
                        Bibliotecario nuevoBibliotecario = prestamo.getBibliotecario();
                        EstadoP nuevoEstado = prestamo.getEstadoP();
                        DTFecha nuevaFechaDevolucion = prestamo.getFechaDevolucion();
                        DTFecha nuevaFechaSolicitud = prestamo.getFechaSolicitada();
                        
                        // Actualizar material si se proporciona
                        if (nuevoMaterialIdStr != null && !nuevoMaterialIdStr.isEmpty()) {
                            int nuevoMaterialId = Integer.parseInt(nuevoMaterialIdStr);
                            nuevoMaterial = controlador.obtenerMaterial(nuevoMaterialId);
                        }
                        
                        // Actualizar lector si se proporciona
                        if (nuevoLectorEmail != null && !nuevoLectorEmail.isEmpty()) {
                            nuevoLector = controlador.obtenerLector(nuevoLectorEmail);
                        }
                        
                        // Actualizar bibliotecario si se proporciona
                        if (nuevoBibliotecarioEmail != null && !nuevoBibliotecarioEmail.isEmpty()) {
                            nuevoBibliotecario = controlador.obtenerBibliotecario(nuevoBibliotecarioEmail);
                        }
                        
                        // Actualizar estado si se proporciona
                        if (nuevoEstadoStr != null && !nuevoEstadoStr.isEmpty()) {
                            nuevoEstado = EstadoP.valueOf(nuevoEstadoStr);
                        }
                        
                        // Actualizar fecha de devolución si se proporciona
                        if (fechaDevolucionStr != null && !fechaDevolucionStr.isEmpty()) {
                            String[] partes = fechaDevolucionStr.split("-");
                            int anio = Integer.parseInt(partes[0]);
                            int mes = Integer.parseInt(partes[1]);
                            int dia = Integer.parseInt(partes[2]);
                            nuevaFechaDevolucion = new DTFecha(dia, mes, anio);
                        }
                        
                        // Actualizar fecha de solicitud si se proporciona
                        if (fechaSolicitudStr != null && !fechaSolicitudStr.isEmpty()) {
                            String[] partes = fechaSolicitudStr.split("-");
                            int anio = Integer.parseInt(partes[0]);
                            int mes = Integer.parseInt(partes[1]);
                            int dia = Integer.parseInt(partes[2]);
                            nuevaFechaSolicitud = new DTFecha(dia, mes, anio);
                        }
                        
                        // Usar el método de actualización completa
                        boolean actualizado = controlador.actualizarPrestamoCompleto(
                            id, nuevoMaterial, nuevoLector, nuevoBibliotecario, 
                            nuevoEstado, nuevaFechaSolicitud, nuevaFechaDevolucion
                        );
                        
                        if (actualizado) {
                            String response = "Préstamo actualizado exitosamente";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.getResponseHeaders().set("Location", "/bibliotecario/prestamos");
                            t.sendResponseHeaders(302, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        } else {
                            String response = "Error al actualizar el préstamo";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.sendResponseHeaders(500, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        }
                    } else {
                        String response = "Préstamo no encontrado";
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(404, responseBytes.length);
                        t.getResponseBody().write(responseBytes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Error al procesar la solicitud: " + e.getMessage() + "</div>");
                byte[] response = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(500, response.length);
                t.getResponseBody().write(response);
            } finally {
                t.getResponseBody().close();
            }
        }
        
        private String generateListaPrestamosParaEditarPage() {
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            
            StringBuilder prestamosHtml = new StringBuilder();
            for (Prestamo prestamo : prestamos) {
                Material material = prestamo.getMaterial();
                String materialNombre = "";
                if (material instanceof Libro) {
                    materialNombre = ((Libro) material).getTitulo();
                } else if (material instanceof ArtEspeciales) {
                    materialNombre = ((ArtEspeciales) material).getDescripcion();
                }
                
                prestamosHtml.append("<tr>")
                    .append("<td>").append(prestamo.getId()).append("</td>")
                    .append("<td>").append(materialNombre).append("</td>")
                    .append("<td>").append(prestamo.getLector().getNombre()).append("</td>")
                    .append("<td>").append(prestamo.getBibliotecario().getNombre()).append("</td>")
                    .append("<td>").append(prestamo.getFechaSolicitada()).append("</td>")
                    .append("<td>").append(prestamo.getFechaDevolucion()).append("</td>")
                    .append("<td><span class=\"badge bg-").append(prestamo.getEstadoP() == EstadoP.ENCURSO ? "warning" : "success").append("\">").append(prestamo.getEstadoP()).append("</span></td>")
                    .append("<td><a href=\"/bibliotecario/prestamos/editar?id=").append(prestamo.getId()).append("\" class=\"btn btn-sm btn-primary\">Editar</a></td>")
                    .append("</tr>");
            }
            
            return CompleteWebServer.generateBasePage("Editar Préstamo", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-edit\"></i> Editar Préstamo</h2>" +
                "<p class=\"text-muted\">Selecciona un préstamo para editar su información.</p>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Material</th>" +
                "<th>Lector</th>" +
                "<th>Bibliotecario</th>" +
                "<th>Fecha Solicitud</th>" +
                "<th>Fecha Devolución</th>" +
                "<th>Estado</th>" +
                "<th>Acción</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                prestamosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"mt-3\">" +
                "<a href=\"/bibliotecario/prestamos\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Volver a Préstamos</a>" +
                "</div>" +
                "</div>");
        }
        
        private String generateEditarPrestamoPage(Prestamo prestamo) {
            Material material = prestamo.getMaterial();
            String materialNombre = "";
            if (material instanceof Libro) {
                materialNombre = ((Libro) material).getTitulo();
            } else if (material instanceof ArtEspeciales) {
                materialNombre = ((ArtEspeciales) material).getDescripcion();
            }
            
            String fechaDevolucion = prestamo.getFechaDevolucion() != null ? 
                String.format("%04d-%02d-%02d", prestamo.getFechaDevolucion().getAnio(), 
                             prestamo.getFechaDevolucion().getMes(), 
                             prestamo.getFechaDevolucion().getDia()) : "";
            
            String fechaSolicitud = prestamo.getFechaSolicitada() != null ? 
                String.format("%04d-%02d-%02d", prestamo.getFechaSolicitada().getAnio(), 
                             prestamo.getFechaSolicitada().getMes(), 
                             prestamo.getFechaSolicitada().getDia()) : "";
            
            // Generar opciones de materiales
            List<Material> materiales = controlador.getListaMateriales();
            StringBuilder materialesOptions = new StringBuilder();
            for (Material mat : materiales) {
                String nombre = "";
                if (mat instanceof Libro) {
                    nombre = ((Libro) mat).getTitulo();
                } else if (mat instanceof ArtEspeciales) {
                    nombre = ((ArtEspeciales) mat).getDescripcion();
                }
                String selected = mat.getId() == material.getId() ? " selected" : "";
                materialesOptions.append("<option value=\"").append(mat.getId()).append("\"").append(selected).append(">")
                    .append(nombre).append(" (ID: ").append(mat.getId()).append(")</option>");
            }
            
            // Generar opciones de lectores
            List<Lector> lectores = controlador.getListaLectores();
            StringBuilder lectoresOptions = new StringBuilder();
            for (Lector lector : lectores) {
                String selected = lector.getEmail().equals(prestamo.getLector().getEmail()) ? " selected" : "";
                lectoresOptions.append("<option value=\"").append(lector.getEmail()).append("\"").append(selected).append(">")
                    .append(lector.getNombre()).append(" (").append(lector.getEmail()).append(")</option>");
            }
            
            // Generar opciones de bibliotecarios
            List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
            StringBuilder bibliotecariosOptions = new StringBuilder();
            for (Bibliotecario biblio : bibliotecarios) {
                String selected = biblio.getEmail().equals(prestamo.getBibliotecario().getEmail()) ? " selected" : "";
                bibliotecariosOptions.append("<option value=\"").append(biblio.getEmail()).append("\"").append(selected).append(">")
                    .append(biblio.getNombre()).append(" (").append(biblio.getEmail()).append(")</option>");
            }
            
            return CompleteWebServer.generateBasePage("Editar Préstamo", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-edit\"></i> Editar Préstamo #" + prestamo.getId() + "</h2>" +
                "<div class=\"alert alert-info\">" +
                "<i class=\"fas fa-info-circle\"></i> Puedes editar todos los campos del préstamo." +
                "</div>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<form method=\"POST\" action=\"/bibliotecario/prestamos/editar\">" +
                "<input type=\"hidden\" name=\"id\" value=\"" + prestamo.getId() + "\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"materialId\" class=\"form-label\">Material:</label>" +
                "<select class=\"form-select\" id=\"materialId\" name=\"materialId\" required>" +
                materialesOptions.toString() +
                "</select>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"lectorEmail\" class=\"form-label\">Lector:</label>" +
                "<select class=\"form-select\" id=\"lectorEmail\" name=\"lectorEmail\" required>" +
                lectoresOptions.toString() +
                "</select>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"bibliotecarioEmail\" class=\"form-label\">Bibliotecario:</label>" +
                "<select class=\"form-select\" id=\"bibliotecarioEmail\" name=\"bibliotecarioEmail\" required>" +
                bibliotecariosOptions.toString() +
                "</select>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"estado\" class=\"form-label\">Estado:</label>" +
                "<select class=\"form-select\" id=\"estado\" name=\"estado\" required>" +
                "<option value=\"ENCURSO\"" + (prestamo.getEstadoP() == EstadoP.ENCURSO ? " selected" : "") + ">En Curso</option>" +
                "<option value=\"DEVUELTO\"" + (prestamo.getEstadoP() == EstadoP.DEVUELTO ? " selected" : "") + ">Devuelto</option>" +
                "</select>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"fechaSolicitud\" class=\"form-label\">Fecha Solicitud:</label>" +
                "<input type=\"date\" class=\"form-control\" id=\"fechaSolicitud\" name=\"fechaSolicitud\" value=\"" + fechaSolicitud + "\">" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"fechaDevolucion\" class=\"form-label\">Fecha Devolución:</label>" +
                "<input type=\"date\" class=\"form-control\" id=\"fechaDevolucion\" name=\"fechaDevolucion\" value=\"" + fechaDevolucion + "\">" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-flex gap-2\">" +
                "<button type=\"submit\" class=\"btn btn-primary\"><i class=\"fas fa-save\"></i> Guardar Cambios</button>" +
                "<a href=\"/bibliotecario/prestamos\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Cancelar</a>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>");
        }
    }
    
    // Handler para editar materiales
    static class EditarMaterialHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                if ("GET".equals(t.getRequestMethod())) {
                    String query = t.getRequestURI().getQuery();
                    if (query != null && query.contains("id=")) {
                        String idStr = query.substring(query.indexOf("id=") + 3);
                        int id = Integer.parseInt(idStr);
                        
                        Material material = controlador.obtenerMaterial(id);
                        if (material != null) {
                            String html = generateEditarMaterialPage(material);
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(200, response.length);
                            t.getResponseBody().write(response);
                        } else {
                            String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Material no encontrado</div>");
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(404, response.length);
                            t.getResponseBody().write(response);
                        }
                    } else {
                        String html = generateListaMaterialesParaEditarPage();
                        byte[] response = html.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                        t.sendResponseHeaders(200, response.length);
                        t.getResponseBody().write(response);
                    }
                } else if ("POST".equals(t.getRequestMethod())) {
                    String requestBody = new String(t.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(requestBody);
                    
                    int id = Integer.parseInt(params.get("id"));
                    String fechaIngresoStr = params.get("fechaIngreso");
                    
                    if (fechaIngresoStr != null && !fechaIngresoStr.isEmpty()) {
                        String[] partes = fechaIngresoStr.split("-");
                        int anio = Integer.parseInt(partes[0]);
                        int mes = Integer.parseInt(partes[1]);
                        int dia = Integer.parseInt(partes[2]);
                        DTFecha nuevaFechaIngreso = new DTFecha(dia, mes, anio);
                        
                        boolean actualizado = controlador.editarMaterial(id, nuevaFechaIngreso);
                        
                        if (actualizado) {
                            String response = "Material actualizado exitosamente";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.getResponseHeaders().set("Location", "/bibliotecario/materiales");
                            t.sendResponseHeaders(302, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        } else {
                            String response = "Error al actualizar el material";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.sendResponseHeaders(500, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        }
                    } else {
                        String response = "Fecha de ingreso requerida";
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(400, responseBytes.length);
                        t.getResponseBody().write(responseBytes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Error al procesar la solicitud: " + e.getMessage() + "</div>");
                byte[] response = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(500, response.length);
                t.getResponseBody().write(response);
            } finally {
                t.getResponseBody().close();
            }
        }
        
        private String generateListaMaterialesParaEditarPage() {
            List<Material> materiales = controlador.getListaMateriales();
            
            StringBuilder materialesHtml = new StringBuilder();
            for (Material material : materiales) {
                String materialNombre = "";
                String tipo = "";
                if (material instanceof Libro) {
                    materialNombre = ((Libro) material).getTitulo();
                    tipo = "Libro";
                } else if (material instanceof ArtEspeciales) {
                    materialNombre = ((ArtEspeciales) material).getDescripcion();
                    tipo = "Artículo Especial";
                }
                
                materialesHtml.append("<tr>")
                    .append("<td>").append(material.getId()).append("</td>")
                    .append("<td>").append(materialNombre).append("</td>")
                    .append("<td>").append(tipo).append("</td>")
                    .append("<td>").append(material.getFechaIngreso()).append("</td>")
                    .append("<td><a href=\"/bibliotecario/materiales/editar?id=").append(material.getId()).append("\" class=\"btn btn-sm btn-primary\">Editar</a></td>")
                    .append("</tr>");
            }
            
            return CompleteWebServer.generateBasePage("Editar Material", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-edit\"></i> Editar Material</h2>" +
                "<p class=\"text-muted\">Selecciona un material para editar su información.</p>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Nombre/Título</th>" +
                "<th>Tipo</th>" +
                "<th>Fecha Ingreso</th>" +
                "<th>Acción</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                materialesHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"mt-3\">" +
                "<a href=\"/bibliotecario/materiales\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Volver a Materiales</a>" +
                "</div>" +
                "</div>");
        }
        
        private String generateEditarMaterialPage(Material material) {
            String materialNombre = "";
            String tipo = "";
            if (material instanceof Libro) {
                materialNombre = ((Libro) material).getTitulo();
                tipo = "Libro";
            } else if (material instanceof ArtEspeciales) {
                materialNombre = ((ArtEspeciales) material).getDescripcion();
                tipo = "Artículo Especial";
            }
            
            String fechaIngreso = material.getFechaIngreso() != null ? 
                String.format("%04d-%02d-%02d", material.getFechaIngreso().getAnio(), 
                             material.getFechaIngreso().getMes(), 
                             material.getFechaIngreso().getDia()) : "";
            
            return CompleteWebServer.generateBasePage("Editar Material", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-edit\"></i> Editar Material #" + material.getId() + "</h2>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<form method=\"POST\" action=\"/bibliotecario/materiales/editar\">" +
                "<input type=\"hidden\" name=\"id\" value=\"" + material.getId() + "\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label class=\"form-label\">Nombre/Título:</label>" +
                "<input type=\"text\" class=\"form-control\" value=\"" + materialNombre + "\" readonly>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label class=\"form-label\">Tipo:</label>" +
                "<input type=\"text\" class=\"form-control\" value=\"" + tipo + "\" readonly>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"fechaIngreso\" class=\"form-label\">Fecha de Ingreso:</label>" +
                "<input type=\"date\" class=\"form-control\" id=\"fechaIngreso\" name=\"fechaIngreso\" value=\"" + fechaIngreso + "\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-flex gap-2\">" +
                "<button type=\"submit\" class=\"btn btn-primary\"><i class=\"fas fa-save\"></i> Guardar Cambios</button>" +
                "<a href=\"/bibliotecario/materiales\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Cancelar</a>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>");
        }
    }
    
    // Handler para editar usuarios
    static class EditarUsuarioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                if ("GET".equals(t.getRequestMethod())) {
                    String query = t.getRequestURI().getQuery();
                    if (query != null && query.contains("email=")) {
                        String email = query.substring(query.indexOf("email=") + 6);
                        email = java.net.URLDecoder.decode(email, "UTF-8");
                        
                        Usuario usuario = controlador.obtenerUsuario(email);
                        if (usuario != null) {
                            String html = generateEditarUsuarioPage(usuario);
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(200, response.length);
                            t.getResponseBody().write(response);
                        } else {
                            String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Usuario no encontrado</div>");
                            byte[] response = html.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                            t.sendResponseHeaders(404, response.length);
                            t.getResponseBody().write(response);
                        }
                    } else {
                        String html = generateListaUsuariosParaEditarPage();
                        byte[] response = html.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                        t.sendResponseHeaders(200, response.length);
                        t.getResponseBody().write(response);
                    }
                } else if ("POST".equals(t.getRequestMethod())) {
                    String requestBody = new String(t.getRequestBody().readAllBytes());
                    Map<String, String> params = parseFormData(requestBody);
                    
                    String email = params.get("email");
                    String nuevoNombre = params.get("nombre");
                    
                    if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                        boolean actualizado = controlador.editarUsuario(email, nuevoNombre);
                        
                        if (actualizado) {
                            String response = "Usuario actualizado exitosamente";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.getResponseHeaders().set("Location", "/bibliotecario/usuarios");
                            t.sendResponseHeaders(302, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        } else {
                            String response = "Error al actualizar el usuario";
                            byte[] responseBytes = response.getBytes("UTF-8");
                            t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                            t.sendResponseHeaders(500, responseBytes.length);
                            t.getResponseBody().write(responseBytes);
                        }
                    } else {
                        String response = "Nombre requerido";
                        byte[] responseBytes = response.getBytes("UTF-8");
                        t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                        t.sendResponseHeaders(400, responseBytes.length);
                        t.getResponseBody().write(responseBytes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String html = CompleteWebServer.generateBasePage("Error", "<div class=\"alert alert-danger\">Error al procesar la solicitud: " + e.getMessage() + "</div>");
                byte[] response = html.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(500, response.length);
                t.getResponseBody().write(response);
            } finally {
                t.getResponseBody().close();
            }
        }
        
        private String generateListaUsuariosParaEditarPage() {
            List<Usuario> usuarios = controlador.getListaUsuarios();
            
            StringBuilder usuariosHtml = new StringBuilder();
            for (Usuario usuario : usuarios) {
                String tipo = "";
                if (usuario instanceof Lector) {
                    tipo = "Lector";
                } else if (usuario instanceof Bibliotecario) {
                    tipo = "Bibliotecario";
                }
                
                usuariosHtml.append("<tr>")
                    .append("<td>").append(usuario.getNombre()).append("</td>")
                    .append("<td>").append(usuario.getEmail()).append("</td>")
                    .append("<td>").append(tipo).append("</td>")
                    .append("<td><a href=\"/bibliotecario/usuarios/editar?email=").append(usuario.getEmail()).append("\" class=\"btn btn-sm btn-primary\">Editar</a></td>")
                    .append("</tr>");
            }
            
            return CompleteWebServer.generateBasePage("Editar Usuario", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-edit\"></i> Editar Usuario</h2>" +
                "<p class=\"text-muted\">Selecciona un usuario para editar su información.</p>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>Nombre</th>" +
                "<th>Email</th>" +
                "<th>Tipo</th>" +
                "<th>Acción</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                usuariosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"mt-3\">" +
                "<a href=\"/bibliotecario/usuarios\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Volver a Usuarios</a>" +
                "</div>" +
                "</div>");
        }
        
        private String generateEditarUsuarioPage(Usuario usuario) {
            String tipo = "";
            if (usuario instanceof Lector) {
                tipo = "Lector";
            } else if (usuario instanceof Bibliotecario) {
                tipo = "Bibliotecario";
            }
            
            return CompleteWebServer.generateBasePage("Editar Usuario", 
                "<div class=\"container mt-4\">" +
                "<h2><i class=\"fas fa-edit\"></i> Editar Usuario</h2>" +
                "<div class=\"card\">" +
                "<div class=\"card-body\">" +
                "<form method=\"POST\" action=\"/bibliotecario/usuarios/editar\">" +
                "<input type=\"hidden\" name=\"email\" value=\"" + usuario.getEmail() + "\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label class=\"form-label\">Email:</label>" +
                "<input type=\"text\" class=\"form-control\" value=\"" + usuario.getEmail() + "\" readonly>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label class=\"form-label\">Tipo:</label>" +
                "<input type=\"text\" class=\"form-control\" value=\"" + tipo + "\" readonly>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"nombre\" class=\"form-label\">Nombre:</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"nombre\" name=\"nombre\" value=\"" + usuario.getNombre() + "\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-flex gap-2\">" +
                "<button type=\"submit\" class=\"btn btn-primary\"><i class=\"fas fa-save\"></i> Guardar Cambios</button>" +
                "<a href=\"/bibliotecario/usuarios\" class=\"btn btn-secondary\"><i class=\"fas fa-arrow-left\"></i> Cancelar</a>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>");
        }
    }
    
    static class PrestamosActivosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                String response = generatePrestamosActivosPage();
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                t.sendResponseHeaders(200, responseBytes.length);
                OutputStream os = t.getResponseBody();
                os.write(responseBytes);
                os.close();
            } catch (Exception e) {
                System.err.println("Error en PrestamosActivosHandler: " + e.getMessage());
                e.printStackTrace();
                String errorResponse = "Error interno del servidor: " + e.getMessage();
                byte[] errorBytes = errorResponse.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(500, errorBytes.length);
                OutputStream os = t.getResponseBody();
                os.write(errorBytes);
                os.close();
            }
        }
        
        private String generatePrestamosActivosPage() {
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            List<Prestamo> prestamosActivos = new ArrayList<>();
            
            // Filtrar solo préstamos activos (EN CURSO)
            for (Prestamo prestamo : prestamos) {
                if (prestamo.getEstadoP() == EstadoP.ENCURSO) {
                    prestamosActivos.add(prestamo);
                }
            }
            
            // Generar tabla de préstamos activos
            StringBuilder prestamosHtml = new StringBuilder();
            for (Prestamo prestamo : prestamosActivos) {
                String materialDesc = "";
                Material material = prestamo.getMaterial();
                if (material instanceof Libro) {
                    materialDesc = ((Libro) material).getTitulo();
                } else if (material instanceof ArtEspeciales) {
                    materialDesc = ((ArtEspeciales) material).getDescripcion();
                } else {
                    materialDesc = "Material ID: " + material.getId();
                }
                
                prestamosHtml.append("<tr>")
                    .append("<td>").append(prestamo.getId()).append("</td>")
                    .append("<td>").append(materialDesc).append("</td>")
                    .append("<td>").append(prestamo.getLector().getNombre()).append("</td>")
                    .append("<td>").append(prestamo.getLector().getEmail()).append("</td>")
                    .append("<td>").append(prestamo.getBibliotecario().getNombre()).append("</td>")
                    .append("<td>").append(prestamo.getFechaSolicitada()).append("</td>")
                    .append("<td>").append(prestamo.getFechaDevolucion()).append("</td>")
                    .append("<td><span class=\"badge bg-warning\">EN CURSO</span></td>")
                    .append("<td>")
                    .append("<div class=\"action-buttons\">")
                    .append("<button class=\"btn btn-sm btn-success\" onclick=\"cambiarEstadoPrestamo(").append(prestamo.getId()).append(")\">Devolver</button>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Préstamos Activos - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/prestamos\">🔄 Préstamos</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">⏰ Préstamos Activos</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"alert alert-info\">" +
                "<h5><i class=\"bi bi-info-circle\"></i> Información</h5>" +
                "<p class=\"mb-0\">Lista de todos los préstamos que están actualmente en curso. Total: <strong>" + prestamosActivos.size() + "</strong> préstamos activos.</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-warning text-white\">" +
                "<h5 class=\"mb-0\">📋 Préstamos en Curso</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>ID</th>" +
                "<th>Material</th>" +
                "<th>Lector</th>" +
                "<th>Email</th>" +
                "<th>Bibliotecario</th>" +
                "<th>Fecha Solicitud</th>" +
                "<th>Fecha Devolución</th>" +
                "<th>Estado</th>" +
                "<th>Acciones</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                prestamosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "function cambiarEstadoPrestamo(id) {" +
                "    if (confirm('¿Estás seguro de marcar este préstamo como devuelto?')) {" +
                "        fetch('/bibliotecario/prestamos/cambiar-estado', {" +
                "            method: 'POST'," +
                "            headers: {'Content-Type': 'application/x-www-form-urlencoded'}," +
                "            body: 'id=' + id" +
                "        }).then(response => response.text()).then(data => {" +
                "            alert('Préstamo marcado como devuelto exitosamente');" +
                "            location.reload();" +
                "        }).catch(error => {" +
                "            alert('Error al cambiar estado: ' + error);" +
                "        });" +
                "    }" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
    
    static class ConsultarMaterialesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            List<Material> materiales = controlador.getListaMateriales();
            
            StringBuilder librosHtml = new StringBuilder();
            StringBuilder articulosHtml = new StringBuilder();
            
            for (Material material : materiales) {
                if (material instanceof Libro) {
                    Libro libro = (Libro) material;
                    librosHtml.append("<tr>")
                        .append("<td>").append(libro.getId()).append("</td>")
                        .append("<td>").append(libro.getTitulo()).append("</td>")
                        .append("<td>").append(libro.getCantPaginas()).append(" páginas</td>")
                        .append("<td>").append(libro.getFechaIngreso().getDia()).append("/")
                        .append(libro.getFechaIngreso().getMes()).append("/")
                        .append(libro.getFechaIngreso().getAnio()).append("</td>")
                        .append("<td><span class=\"badge bg-success\">Disponible</span></td>")
                        .append("</tr>");
                } else if (material instanceof ArtEspeciales) {
                    ArtEspeciales artEspecial = (ArtEspeciales) material;
                    articulosHtml.append("<tr>")
                        .append("<td>").append(artEspecial.getId()).append("</td>")
                        .append("<td>").append(artEspecial.getDescripcion()).append("</td>")
                        .append("<td>").append(artEspecial.getPeso()).append(" kg</td>")
                        .append("<td>").append(artEspecial.getDimensiones().getAncho()).append("x")
                        .append(artEspecial.getDimensiones().getAlto()).append("x")
                        .append(artEspecial.getDimensiones().getProfundidad()).append(" cm</td>")
                        .append("<td>").append(artEspecial.getFechaIngreso().getDia()).append("/")
                        .append(artEspecial.getFechaIngreso().getMes()).append("/")
                        .append(artEspecial.getFechaIngreso().getAnio()).append("</td>")
                        .append("<td><span class=\"badge bg-success\">Disponible</span></td>")
                        .append("</tr>");
                }
            }
            
            String response = "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Consultar Materiales - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: #f8f9fa; }" +
                ".card { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); border: none; }" +
                ".navbar { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-success\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/lector\"><i class=\"bi bi-book\"></i> Dashboard Lector</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/lector\"><i class=\"bi bi-house\"></i> Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\"><i class=\"bi bi-box-arrow-right\"></i> Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"mb-4\"><i class=\"bi bi-search\"></i> Consultar Materiales</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white\">" +
                "<h5 class=\"mb-0\"><i class=\"bi bi-book\"></i> Libros Disponibles</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr><th>ID</th><th>Título</th><th>Páginas</th><th>Fecha Ingreso</th><th>Estado</th></tr>" +
                "</thead>" +
                "<tbody>" +
                librosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white\">" +
                "<h5 class=\"mb-0\"><i class=\"bi bi-palette\"></i> Artículos Especiales Disponibles</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr><th>ID</th><th>Descripción</th><th>Peso</th><th>Dimensiones</th><th>Fecha Ingreso</th><th>Estado</th></tr>" +
                "</thead>" +
                "<tbody>" +
                articulosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
            
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class MisPrestamosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Obtener el lector autenticado desde la sesión
            Usuario usuario = getUsuarioFromSession(t);
            
            if (!(usuario instanceof Lector)) {
                String response = "Debe iniciar sesión como lector para ver sus préstamos";
                byte[] responseBytes = response.getBytes("UTF-8");
                t.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                t.sendResponseHeaders(401, responseBytes.length);
                t.getResponseBody().write(responseBytes);
                return;
            }
            
            Lector lector = (Lector) usuario;
            List<Prestamo> prestamos = controlador.getListaPrestamos();
            
            StringBuilder prestamosActivosHtml = new StringBuilder();
            StringBuilder prestamosDevueltosHtml = new StringBuilder();
            
            for (Prestamo prestamo : prestamos) {
                // Solo mostrar préstamos del lector autenticado
                if (!prestamo.getLector().getId().equals(lector.getId())) {
                    continue;
                }
                String materialInfo = "";
                String lectorInfo = "";
                String bibliotecarioInfo = "";
                
                // Obtener información del material
                Material material = prestamo.getMaterial();
                
                if (material instanceof Libro) {
                    materialInfo = ((Libro) material).getTitulo();
                } else if (material instanceof ArtEspeciales) {
                    materialInfo = ((ArtEspeciales) material).getDescripcion();
                }
                
                // Obtener información del lector
                lectorInfo = prestamo.getLector().getNombre();
                
                // Obtener información del bibliotecario
                bibliotecarioInfo = prestamo.getBibliotecario().getNombre();
                
                String estadoClass = prestamo.getEstadoP() == EstadoP.ENCURSO ? "bg-warning" : "bg-success";
                String estadoText = prestamo.getEstadoP() == EstadoP.ENCURSO ? "En Curso" : "Devuelto";
                
                String rowHtml = "<tr>" +
                    "<td>" + prestamo.getId() + "</td>" +
                    "<td>" + materialInfo + "</td>" +
                    "<td>" + lectorInfo + "</td>" +
                    "<td>" + bibliotecarioInfo + "</td>" +
                    "<td>" + prestamo.getFechaSolicitada().getDia() + "/" + 
                           prestamo.getFechaSolicitada().getMes() + "/" + 
                           prestamo.getFechaSolicitada().getAnio() + "</td>" +
                    "<td>" + prestamo.getFechaDevolucion().getDia() + "/" + 
                           prestamo.getFechaDevolucion().getMes() + "/" + 
                           prestamo.getFechaDevolucion().getAnio() + "</td>" +
                    "<td><span class=\"badge " + estadoClass + "\">" + estadoText + "</span></td>" +
                    "</tr>";
                
                if (prestamo.getEstadoP() == EstadoP.ENCURSO) {
                    prestamosActivosHtml.append(rowHtml);
                } else {
                    prestamosDevueltosHtml.append(rowHtml);
                }
            }
            
            String response = "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Mis Préstamos - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: #f8f9fa; }" +
                ".card { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); border: none; }" +
                ".navbar { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075); }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-success\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/lector\"><i class=\"bi bi-book\"></i> Dashboard Lector</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/lector\"><i class=\"bi bi-house\"></i> Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\"><i class=\"bi bi-box-arrow-right\"></i> Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"mb-4\"><i class=\"bi bi-list-check\"></i> Mis Préstamos</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-warning text-dark\">" +
                "<h5 class=\"mb-0\"><i class=\"bi bi-clock\"></i> Préstamos Activos</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr><th>ID</th><th>Material</th><th>Lector</th><th>Bibliotecario</th><th>Fecha Solicitud</th><th>Fecha Devolución</th><th>Estado</th></tr>" +
                "</thead>" +
                "<tbody>" +
                prestamosActivosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white\">" +
                "<h5 class=\"mb-0\"><i class=\"bi bi-check-circle\"></i> Préstamos Devueltos</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr><th>ID</th><th>Material</th><th>Lector</th><th>Bibliotecario</th><th>Fecha Solicitud</th><th>Fecha Devolución</th><th>Estado</th></tr>" +
                "</thead>" +
                "<tbody>" +
                prestamosDevueltosHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
            
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // API Handlers
    static class ApiUsuariosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{\"status\": \"ok\", \"message\": \"API Usuarios funcionando\"}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class ApiMaterialesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{\"status\": \"ok\", \"message\": \"API Materiales funcionando\"}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class ApiPrestamosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{\"status\": \"ok\", \"message\": \"API Préstamos funcionando\"}";
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    // Handler para Panel de Administrador
    static class PanelAdminHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = generatePanelAdminPage();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generatePanelAdminPage() {
            // Obtener estadísticas del sistema
            int totalBibliotecarios = controlador.obtenerCantidadBibliotecarios();
            int totalLectores = controlador.obtenerCantidadLectores();
            int totalLibros = controlador.obtenerCantidadLibros();
            int totalArtEspeciales = controlador.obtenerCantidadArtEspeciales();
            int totalPrestamos = controlador.obtenerCantidadPrestamos();
            // Contar préstamos por estado manualmente
            List<Prestamo> todosPrestamos = controlador.getListaPrestamos();
            int prestamosEnCurso = 0;
            int prestamosDevueltos = 0;
            for (Prestamo prestamo : todosPrestamos) {
                if (prestamo.getEstadoP() == EstadoP.ENCURSO) {
                    prestamosEnCurso++;
                } else if (prestamo.getEstadoP() == EstadoP.DEVUELTO) {
                    prestamosDevueltos++;
                }
            }
            
            // Obtener listas para reportes
            List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
            List<Lector> lectoresPorZona = controlador.getListaLectores();
            
            // Generar opciones para bibliotecarios
            StringBuilder bibliotecariosOptions = new StringBuilder();
            for (Bibliotecario bibliotecario : bibliotecarios) {
                bibliotecariosOptions.append("<option value=\"").append(bibliotecario.getEmail()).append("\">")
                    .append(bibliotecario.getNombre()).append(" (Nro. ").append(bibliotecario.getNroEmpleado()).append(")")
                    .append("</option>");
            }
            
            // Generar estadísticas por zona
            Map<Zona, Integer> lectoresPorZonaMap = new HashMap<>();
            for (Lector lector : lectoresPorZona) {
                lectoresPorZonaMap.put(lector.getZona(), lectoresPorZonaMap.getOrDefault(lector.getZona(), 0) + 1);
            }
            
            StringBuilder zonaStatsHtml = new StringBuilder();
            for (Map.Entry<Zona, Integer> entry : lectoresPorZonaMap.entrySet()) {
                zonaStatsHtml.append("<tr>")
                    .append("<td>").append(entry.getKey()).append("</td>")
                    .append("<td>").append(entry.getValue()).append("</td>")
                    .append("<td>")
                    .append("<div class=\"progress\">")
                    .append("<div class=\"progress-bar\" role=\"progressbar\" style=\"width: ").append((entry.getValue() * 100) / totalLectores).append("%\"></div>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
            }
            
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Panel de Administrador - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                ".stat-card { background: linear-gradient(45deg, #667eea, #764ba2); color: white; }" +
                ".admin-card { background: linear-gradient(45deg, #ff6b6b, #ee5a24); color: white; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row\">" +
                "<div class=\"col-12\">" +
                "<h1 class=\"text-white mb-4\">👑 Panel de Administrador</h1>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mb-4\">" +
                "<div class=\"col-md-2\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-people-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalLectores + "</h3>" +
                "<p class=\"mb-0\">Lectores</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-2\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-person-badge-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalBibliotecarios + "</h3>" +
                "<p class=\"mb-0\">Bibliotecarios</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-2\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-book-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalLibros + "</h3>" +
                "<p class=\"mb-0\">Libros</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-2\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-palette-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalArtEspeciales + "</h3>" +
                "<p class=\"mb-0\">Artículos</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-2\">" +
                "<div class=\"card stat-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-arrow-repeat\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + totalPrestamos + "</h3>" +
                "<p class=\"mb-0\">Total Préstamos</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-2\">" +
                "<div class=\"card admin-card\">" +
                "<div class=\"card-body text-center\">" +
                "<i class=\"bi bi-clock-fill\" style=\"font-size: 2rem;\"></i>" +
                "<h3>" + prestamosEnCurso + "</h3>" +
                "<p class=\"mb-0\">En Curso</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white\">" +
                "<h5 class=\"mb-0\">📊 Reporte por Zona</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"table-responsive\">" +
                "<table class=\"table table-striped\">" +
                "<thead>" +
                "<tr>" +
                "<th>Zona</th>" +
                "<th>Lectores</th>" +
                "<th>Distribución</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                zonaStatsHtml.toString() +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white\">" +
                "<h5 class=\"mb-0\">📈 Estadísticas de Préstamos</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"row text-center\">" +
                "<div class=\"col-6\">" +
                "<h3 class=\"text-warning\">" + prestamosEnCurso + "</h3>" +
                "<p>Préstamos en Curso</p>" +
                "</div>" +
                "<div class=\"col-6\">" +
                "<h3 class=\"text-success\">" + prestamosDevueltos + "</h3>" +
                "<p>Préstamos Devueltos</p>" +
                "</div>" +
                "</div>" +
                "<div class=\"progress mb-3\">" +
                "<div class=\"progress-bar bg-warning\" role=\"progressbar\" style=\"width: " + ((prestamosEnCurso * 100) / totalPrestamos) + "%\"></div>" +
                "<div class=\"progress-bar bg-success\" role=\"progressbar\" style=\"width: " + ((prestamosDevueltos * 100) / totalPrestamos) + "%\"></div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row mt-4\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-warning text-white\">" +
                "<h5 class=\"mb-0\">👨‍💼 Historial por Bibliotecario</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"bibliotecarioReporte\" class=\"form-label\">Seleccionar Bibliotecario</label>" +
                "<select class=\"form-select\" id=\"bibliotecarioReporte\">" +
                "<option value=\"\">Seleccionar bibliotecario...</option>" +
                bibliotecariosOptions.toString() +
                "</select>" +
                "</div>" +
                "<button class=\"btn btn-warning\" onclick=\"generarReporteBibliotecario()\">Generar Reporte</button>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-danger text-white\">" +
                "<h5 class=\"mb-0\">⚠️ Materiales Pendientes</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<p>Identificar materiales con muchos préstamos pendientes</p>" +
                "<button class=\"btn btn-danger\" onclick=\"identificarMaterialesPendientes()\">Identificar Materiales</button>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "function generarReporteBibliotecario() {" +
                "    const bibliotecario = document.getElementById('bibliotecarioReporte').value;" +
                "    if (bibliotecario) {" +
                "        alert('Generando reporte para: ' + bibliotecario);" +
                "    } else {" +
                "        alert('Por favor selecciona un bibliotecario');" +
                "    }" +
                "}" +
                "function identificarMaterialesPendientes() {" +
                "    alert('Identificando materiales con préstamos pendientes...');" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para registro de lectores
    static class RegistrarLectorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                
                String nombre = params.get("nombre");
                String email = params.get("email");
                String direccion = params.get("direccion");
                String zonaStr = params.get("zona");
                
                try {
                    if (nombre != null && email != null && direccion != null && zonaStr != null) {
                        Zona zona = Zona.valueOf(zonaStr);
                        LocalDate hoy = LocalDate.now();
                        DTFecha fechaRegistro = new DTFecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
                        
                        boolean exito = controlador.registrarLector(nombre.trim(), email.trim(), direccion.trim(), fechaRegistro, zona);
                        
                        if (exito) {
                            t.getResponseHeaders().set("Location", "/bibliotecario/usuarios");
                            t.sendResponseHeaders(302, 0);
                            t.getResponseBody().close();
                            return;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al registrar lector: " + e.getMessage());
                }
            }
            
            // Mostrar formulario
            String response = generateRegistrarLectorForm();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateRegistrarLectorForm() {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Registrar Lector - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/usuarios\">👥 Usuarios</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-8\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white\">" +
                "<h5 class=\"mb-0\">👤 Registrar Nuevo Lector</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<form method=\"post\" action=\"/bibliotecario/usuarios/registrar-lector\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"nombre\" class=\"form-label\">Nombre Completo</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"nombre\" name=\"nombre\" required>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"email\" class=\"form-label\">Email</label>" +
                "<input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"direccion\" class=\"form-label\">Dirección</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"direccion\" name=\"direccion\" required>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"zona\" class=\"form-label\">Zona</label>" +
                "<select class=\"form-select\" id=\"zona\" name=\"zona\" required>" +
                "<option value=\"\">Seleccionar zona...</option>" +
                "<option value=\"BIBLOTECA_CENTRAL\">Biblioteca Central</option>" +
                "<option value=\"SUCURSAL_ESTE\">Sucursal Este</option>" +
                "<option value=\"SUCURSAL_OESTE\">Sucursal Oeste</option>" +
                "</select>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-grid gap-2 d-md-flex justify-content-md-end\">" +
                "<a href=\"/bibliotecario/usuarios\" class=\"btn btn-secondary me-md-2\">Cancelar</a>" +
                "<button type=\"submit\" class=\"btn btn-primary\">Registrar Lector</button>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para registro de bibliotecarios
    static class RegistrarBibliotecarioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                
                String nombre = params.get("nombre");
                String email = params.get("email");
                String nroEmpleadoStr = params.get("nroEmpleado");
                
                try {
                    if (nombre != null && email != null && nroEmpleadoStr != null) {
                        int nroEmpleado = Integer.parseInt(nroEmpleadoStr.trim());
                        boolean exito = controlador.registrarBibliotecario(nombre.trim(), email.trim(), nroEmpleado);
                        
                        if (exito) {
                            t.getResponseHeaders().set("Location", "/bibliotecario/usuarios");
                            t.sendResponseHeaders(302, 0);
                            t.getResponseBody().close();
                            return;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al registrar bibliotecario: " + e.getMessage());
                }
            }
            
            // Mostrar formulario
            String response = generateRegistrarBibliotecarioForm();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateRegistrarBibliotecarioForm() {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Registrar Bibliotecario - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/usuarios\">👥 Usuarios</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-8\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-success text-white\">" +
                "<h5 class=\"mb-0\">👨‍💼 Registrar Nuevo Bibliotecario</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<form method=\"post\" action=\"/bibliotecario/usuarios/registrar-bibliotecario\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"nombre\" class=\"form-label\">Nombre Completo</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"nombre\" name=\"nombre\" required>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"email\" class=\"form-label\">Email</label>" +
                "<input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-6\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"nroEmpleado\" class=\"form-label\">Número de Empleado</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"nroEmpleado\" name=\"nroEmpleado\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-grid gap-2 d-md-flex justify-content-md-end\">" +
                "<a href=\"/bibliotecario/usuarios\" class=\"btn btn-secondary me-md-2\">Cancelar</a>" +
                "<button type=\"submit\" class=\"btn btn-success\">Registrar Bibliotecario</button>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        }
    }
    
    // Handler para registro de donaciones
    static class RegistrarDonacionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                String body = new String(t.getRequestBody().readAllBytes());
                Map<String, String> params = parseFormData(body);
                
                String tipo = params.get("tipo");
                
                try {
                    if ("libro".equals(tipo)) {
                        String titulo = params.get("titulo");
                        String cantPaginasStr = params.get("cantPaginas");
                        
                        if (titulo != null && cantPaginasStr != null) {
                            int cantPaginas = Integer.parseInt(cantPaginasStr.trim());
                            Integer idMaterial = controlador.registrarDonacionLibro(titulo.trim(), cantPaginas);
                            
                            if (idMaterial != null) {
                                t.getResponseHeaders().set("Location", "/bibliotecario/materiales");
                                t.sendResponseHeaders(302, 0);
                                t.getResponseBody().close();
                                return;
                            }
                        }
                    } else if ("artespecial".equals(tipo)) {
                        String descripcion = params.get("descripcion");
                        String pesoStr = params.get("peso");
                        String anchoStr = params.get("ancho");
                        String altoStr = params.get("alto");
                        String profundidadStr = params.get("profundidad");
                        
                        if (descripcion != null && pesoStr != null && anchoStr != null && altoStr != null && profundidadStr != null) {
                            double peso = Double.parseDouble(pesoStr.trim());
                            double ancho = Double.parseDouble(anchoStr.trim());
                            double alto = Double.parseDouble(altoStr.trim());
                            double profundidad = Double.parseDouble(profundidadStr.trim());
                            
                            DTDimensiones dimensiones = new DTDimensiones(ancho, alto, profundidad);
                            Integer idMaterial = controlador.registrarDonacionArtEspecial(descripcion.trim(), peso, dimensiones);
                            
                            if (idMaterial != null) {
                                t.getResponseHeaders().set("Location", "/bibliotecario/materiales");
                                t.sendResponseHeaders(302, 0);
                                t.getResponseBody().close();
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al registrar donación: " + e.getMessage());
                }
            }
            
            // Mostrar formulario
            String response = generateRegistrarDonacionForm();
            t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
        private String generateRegistrarDonacionForm() {
            return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Registrar Donación - Biblioteca Comunitaria</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                "<style>" +
                "body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }" +
                ".card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }" +
                ".btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "<div class=\"container\">" +
                "<a class=\"navbar-brand\" href=\"/\">📚 Biblioteca Comunitaria</a>" +
                "<div class=\"navbar-nav ms-auto\">" +
                "<a class=\"nav-link\" href=\"/bibliotecario\">🏠 Dashboard</a>" +
                "<a class=\"nav-link\" href=\"/bibliotecario/materiales\">📚 Materiales</a>" +
                "<a class=\"nav-link\" href=\"/logout\">🚪 Cerrar Sesión</a>" +
                "</div>" +
                "</div>" +
                "</nav>" +
                "<div class=\"container mt-4\">" +
                "<div class=\"row justify-content-center\">" +
                "<div class=\"col-md-10\">" +
                "<div class=\"card\">" +
                "<div class=\"card-header bg-primary text-white\">" +
                "<h5 class=\"mb-0\">📚 Registrar Nueva Donación</h5>" +
                "</div>" +
                "<div class=\"card-body\">" +
                "<div class=\"mb-4\">" +
                "<label class=\"form-label\">Tipo de Material</label>" +
                "<div class=\"btn-group w-100\" role=\"group\">" +
                "<input type=\"radio\" class=\"btn-check\" name=\"tipo\" id=\"libro\" value=\"libro\" checked>" +
                "<label class=\"btn btn-outline-primary\" for=\"libro\">📖 Libro</label>" +
                "<input type=\"radio\" class=\"btn-check\" name=\"tipo\" id=\"artespecial\" value=\"artespecial\">" +
                "<label class=\"btn btn-outline-primary\" for=\"artespecial\">🎨 Artículo Especial</label>" +
                "</div>" +
                "</div>" +
                "<form method=\"post\" action=\"/bibliotecario/materiales/registrar-donacion\">" +
                "<input type=\"hidden\" name=\"tipo\" id=\"tipoHidden\" value=\"libro\">" +
                "<div id=\"formularioLibro\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-8\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"titulo\" class=\"form-label\">Título del Libro</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"titulo\" name=\"titulo\" required>" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-4\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"cantPaginas\" class=\"form-label\">Cantidad de Páginas</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"cantPaginas\" name=\"cantPaginas\" min=\"1\" required>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div id=\"formularioArtEspecial\" style=\"display: none;\">" +
                "<div class=\"row\">" +
                "<div class=\"col-md-12\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"descripcion\" class=\"form-label\">Descripción del Artículo</label>" +
                "<input type=\"text\" class=\"form-control\" id=\"descripcion\" name=\"descripcion\">" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"row\">" +
                "<div class=\"col-md-3\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"peso\" class=\"form-label\">Peso (kg)</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"peso\" name=\"peso\" step=\"0.1\" min=\"0.1\">" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-3\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"ancho\" class=\"form-label\">Ancho (cm)</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"ancho\" name=\"ancho\" step=\"0.1\" min=\"0.1\">" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-3\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"alto\" class=\"form-label\">Alto (cm)</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"alto\" name=\"alto\" step=\"0.1\" min=\"0.1\">" +
                "</div>" +
                "</div>" +
                "<div class=\"col-md-3\">" +
                "<div class=\"mb-3\">" +
                "<label for=\"profundidad\" class=\"form-label\">Profundidad (cm)</label>" +
                "<input type=\"number\" class=\"form-control\" id=\"profundidad\" name=\"profundidad\" step=\"0.1\" min=\"0.1\">" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"d-grid gap-2 d-md-flex justify-content-md-end\">" +
                "<a href=\"/bibliotecario/materiales\" class=\"btn btn-secondary me-md-2\">Cancelar</a>" +
                "<button type=\"submit\" class=\"btn btn-primary\">Registrar Donación</button>" +
                "</div>" +
                "</form>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<script>" +
                "document.querySelectorAll('input[name=\"tipo\"]').forEach(radio => {" +
                "    radio.addEventListener('change', function() {" +
                "        const tipo = this.value;" +
                "        const formularioLibro = document.getElementById('formularioLibro');" +
                "        const formularioArtEspecial = document.getElementById('formularioArtEspecial');" +
                "        const tipoHidden = document.getElementById('tipoHidden');" +
                "        tipoHidden.value = tipo;" +
                "        if (tipo === 'libro') {" +
                "            formularioLibro.style.display = 'block';" +
                "            formularioArtEspecial.style.display = 'none';" +
                "            document.getElementById('titulo').required = true;" +
                "            document.getElementById('cantPaginas').required = true;" +
                "            document.getElementById('descripcion').required = false;" +
                "            document.getElementById('peso').required = false;" +
                "            document.getElementById('ancho').required = false;" +
                "            document.getElementById('alto').required = false;" +
                "            document.getElementById('profundidad').required = false;" +
                "        } else {" +
                "            formularioLibro.style.display = 'none';" +
                "            formularioArtEspecial.style.display = 'block';" +
                "            document.getElementById('titulo').required = false;" +
                "            document.getElementById('cantPaginas').required = false;" +
                "            document.getElementById('descripcion').required = true;" +
                "            document.getElementById('peso').required = true;" +
                "            document.getElementById('ancho').required = true;" +
                "            document.getElementById('alto').required = true;" +
                "            document.getElementById('profundidad').required = true;" +
                "        }" +
                "    });" +
                "});" +
                "</script>" +
                "</body>" +
                "</html>";
        }
    }
}
