package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet para gestión de usuarios (solo bibliotecarios)
 */
@WebServlet(name = "GestionUsuariosServlet", urlPatterns = {"/app/gestion-usuarios"})
public class GestionUsuariosServlet extends HttpServlet {

    private IControladorFachada controlador;

    @Override
    public void init() throws ServletException {
        super.init();
        if (!ConfiguracionBD.estaConectado()) {
            ConfiguracionBD.inicializarConexion();
        }
        controlador = ControladorFachada.getInstancia();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que sea un bibliotecario
        if (!esBibliotecario(request)) {
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=acceso_denegado");
            return;
        }

        try {
            // Obtener todos los lectores
            java.util.List<Lector> lectores = controlador.obtenerTodosLosLectores();
            request.setAttribute("lectores", lectores);
            
            // Obtener estadísticas
            int totalLectores = controlador.obtenerCantidadLectores();
            int lectoresActivos = controlador.obtenerCantidadLectoresPorEstado(EstadoL.ACTIVO);
            int lectoresSuspendidos = controlador.obtenerCantidadLectoresPorEstado(EstadoL.SUSPENDIDO);
            
            request.setAttribute("totalLectores", totalLectores);
            request.setAttribute("lectoresActivos", lectoresActivos);
            request.setAttribute("lectoresSuspendidos", lectoresSuspendidos);
            
            request.getRequestDispatcher("/app/bibliotecario/gestion-usuarios.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard.jsp?error=error_cargar_usuarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que sea un bibliotecario
        if (!esBibliotecario(request)) {
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=acceso_denegado");
            return;
        }

        String accion = request.getParameter("accion");
        
        try {
            switch (accion) {
                case "suspender":
                    suspenderLector(request, response);
                    break;
                case "activar":
                    activarLector(request, response);
                    break;
                case "cambiar_zona":
                    cambiarZonaLector(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=accion_invalida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=error_procesar_accion");
        }
    }

    private void suspenderLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String emailLector = request.getParameter("emailLector");
        
        if (emailLector == null || emailLector.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=email_requerido");
            return;
        }
        
        boolean exito = controlador.suspenderLector(emailLector);
        
        if (exito) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?mensaje=lector_suspendido");
        } else {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=error_suspender_lector");
        }
    }

    private void activarLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String emailLector = request.getParameter("emailLector");
        
        if (emailLector == null || emailLector.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=email_requerido");
            return;
        }
        
        boolean exito = controlador.activarLector(emailLector);
        
        if (exito) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?mensaje=lector_activado");
        } else {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=error_activar_lector");
        }
    }

    private void cambiarZonaLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String emailLector = request.getParameter("emailLector");
        String nuevaZona = request.getParameter("nuevaZona");
        
        if (emailLector == null || emailLector.trim().isEmpty() || 
            nuevaZona == null || nuevaZona.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=parametros_requeridos");
            return;
        }
        
        try {
            Zona zona = Zona.valueOf(nuevaZona);
            boolean exito = controlador.cambiarZonaLector(emailLector, zona);
            
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?mensaje=zona_actualizada");
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=error_cambiar_zona");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=zona_invalida");
        }
    }

    private boolean esBibliotecario(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        return "bibliotecario".equals(tipoUsuario);
    }
}
