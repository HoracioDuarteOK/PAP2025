package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet específico para funcionalidades de bibliotecarios
 */
@WebServlet(name = "BibliotecarioServlet", urlPatterns = {"/app/bibliotecario/*"})
public class BibliotecarioServlet extends HttpServlet {

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

        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
                cargarDashboard(request, response);
            } else if (pathInfo.equals("/perfil")) {
                cargarPerfil(request, response);
            } else if (pathInfo.equals("/usuarios")) {
                cargarUsuarios(request, response);
            } else if (pathInfo.equals("/materiales")) {
                cargarMateriales(request, response);
            } else if (pathInfo.equals("/prestamos")) {
                cargarPrestamos(request, response);
            } else if (pathInfo.equals("/reportes")) {
                cargarReportes(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard.jsp?error=error_cargar_pagina");
        }
    }

    private void cargarDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String emailBibliotecario = (String) request.getSession().getAttribute("email");
        Bibliotecario bibliotecario = controlador.obtenerBibliotecario(emailBibliotecario);
        
        if (bibliotecario == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=bibliotecario_no_encontrado");
            return;
        }
        
        // Estadísticas generales
        int totalUsuarios = controlador.obtenerCantidadUsuarios();
        int totalLectores = controlador.obtenerCantidadLectores();
        int totalBibliotecarios = controlador.obtenerCantidadBibliotecarios();
        int totalMateriales = controlador.obtenerCantidadMateriales();
        int totalLibros = controlador.obtenerCantidadLibros();
        int totalArtEspeciales = controlador.obtenerCantidadArtEspeciales();
        int totalPrestamos = controlador.obtenerCantidadPrestamos();
        
        // Préstamos por estado
        java.util.List<Prestamo> prestamosEnCurso = controlador.obtenerPrestamosPorEstado(EstadoP.ENCURSO);
        java.util.List<Prestamo> prestamosDevueltos = controlador.obtenerPrestamosPorEstado(EstadoP.DEVUELTO);
        java.util.List<Prestamo> prestamosPendientes = controlador.obtenerPrestamosPorEstado(EstadoP.PENDIENTE);
        
        // Lectores por estado
        int lectoresActivos = controlador.obtenerCantidadLectoresPorEstado(EstadoL.ACTIVO);
        int lectoresSuspendidos = controlador.obtenerCantidadLectoresPorEstado(EstadoL.SUSPENDIDO);
        
        request.setAttribute("bibliotecario", bibliotecario);
        request.setAttribute("totalUsuarios", totalUsuarios);
        request.setAttribute("totalLectores", totalLectores);
        request.setAttribute("totalBibliotecarios", totalBibliotecarios);
        request.setAttribute("totalMateriales", totalMateriales);
        request.setAttribute("totalLibros", totalLibros);
        request.setAttribute("totalArtEspeciales", totalArtEspeciales);
        request.setAttribute("totalPrestamos", totalPrestamos);
        request.setAttribute("prestamosEnCurso", prestamosEnCurso.size());
        request.setAttribute("prestamosDevueltos", prestamosDevueltos.size());
        request.setAttribute("prestamosPendientes", prestamosPendientes.size());
        request.setAttribute("lectoresActivos", lectoresActivos);
        request.setAttribute("lectoresSuspendidos", lectoresSuspendidos);
        
        request.getRequestDispatcher("/app/bibliotecario/dashboard.jsp").forward(request, response);
    }

    private void cargarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String emailBibliotecario = (String) request.getSession().getAttribute("email");
        Bibliotecario bibliotecario = controlador.obtenerBibliotecario(emailBibliotecario);
        
        if (bibliotecario == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=bibliotecario_no_encontrado");
            return;
        }
        
        // Obtener historial de préstamos gestionados por este bibliotecario
        java.util.List<Prestamo> historialPrestamos = controlador.obtenerHistorialPrestamosPorBibliotecario(emailBibliotecario);
        
        request.setAttribute("bibliotecario", bibliotecario);
        request.setAttribute("historialPrestamos", historialPrestamos);
        
        request.getRequestDispatcher("/app/bibliotecario/perfil.jsp").forward(request, response);
    }

    private void cargarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Redirigir al servlet de gestión de usuarios
        response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios");
    }

    private void cargarMateriales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Redirigir al servlet de gestión de materiales
        response.sendRedirect(request.getContextPath() + "/app/gestion-materiales");
    }

    private void cargarPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Redirigir al servlet de gestión de préstamos
        response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos");
    }

    private void cargarReportes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Obtener reportes
            java.util.List<Object[]> reportePorZona = controlador.obtenerReportePrestamosPorZona();
            java.util.List<Object[]> materialesPendientes = controlador.obtenerMaterialesConPrestamosPendientes();
            
            request.setAttribute("reportePorZona", reportePorZona);
            request.setAttribute("materialesPendientes", materialesPendientes);
            
            request.getRequestDispatcher("/app/bibliotecario/reportes.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard.jsp?error=error_cargar_reportes");
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
