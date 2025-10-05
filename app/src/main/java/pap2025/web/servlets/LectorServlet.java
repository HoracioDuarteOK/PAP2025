package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet específico para funcionalidades de lectores
 */
@WebServlet(name = "LectorServlet", urlPatterns = {"/app/lector/*"})
public class LectorServlet extends HttpServlet {

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
        
        // Verificar que sea un lector
        if (!esLector(request)) {
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard.jsp?error=acceso_denegado");
            return;
        }

        String pathInfo = request.getPathInfo();
        String emailLector = (String) request.getSession().getAttribute("email");
        
        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
                cargarDashboard(request, response, emailLector);
            } else if (pathInfo.equals("/perfil")) {
                cargarPerfil(request, response, emailLector);
            } else if (pathInfo.equals("/prestamos")) {
                cargarPrestamos(request, response, emailLector);
            } else if (pathInfo.equals("/materiales")) {
                cargarMateriales(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=error_cargar_pagina");
        }
    }

    private void cargarDashboard(HttpServletRequest request, HttpServletResponse response, String emailLector)
            throws ServletException, IOException {
        
        Lector lector = controlador.obtenerLector(emailLector);
        if (lector == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=lector_no_encontrado");
            return;
        }
        
        // Obtener préstamos del lector
        java.util.List<Prestamo> prestamosLector = controlador.obtenerPrestamosPorLector(emailLector);
        
        // Estadísticas básicas
        int totalPrestamos = prestamosLector.size();
        int prestamosActivos = 0;
        int prestamosDevueltos = 0;
        
        for (Prestamo prestamo : prestamosLector) {
            if (prestamo.getEstadoP() == EstadoP.ENCURSO || prestamo.getEstadoP() == EstadoP.PENDIENTE) {
                prestamosActivos++;
            } else if (prestamo.getEstadoP() == EstadoP.DEVUELTO) {
                prestamosDevueltos++;
            }
        }
        
        request.setAttribute("lector", lector);
        request.setAttribute("prestamosLector", prestamosLector);
        request.setAttribute("totalPrestamos", totalPrestamos);
        request.setAttribute("prestamosActivos", prestamosActivos);
        request.setAttribute("prestamosDevueltos", prestamosDevueltos);
        
        request.getRequestDispatcher("/app/lector/dashboard.jsp").forward(request, response);
    }

    private void cargarPerfil(HttpServletRequest request, HttpServletResponse response, String emailLector)
            throws ServletException, IOException {
        
        Lector lector = controlador.obtenerLector(emailLector);
        if (lector == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=lector_no_encontrado");
            return;
        }
        
        request.setAttribute("lector", lector);
        request.getRequestDispatcher("/app/lector/perfil.jsp").forward(request, response);
    }

    private void cargarPrestamos(HttpServletRequest request, HttpServletResponse response, String emailLector)
            throws ServletException, IOException {
        
        // Redirigir al servlet de gestión de préstamos
        response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos");
    }

    private void cargarMateriales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Redirigir al servlet de gestión de materiales
        response.sendRedirect(request.getContextPath() + "/app/gestion-materiales");
    }

    private boolean esLector(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        return "lector".equals(tipoUsuario);
    }
}
