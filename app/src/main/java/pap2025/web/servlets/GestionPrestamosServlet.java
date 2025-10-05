package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;
import pap2025.datatypes.DTFecha;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet para gestión de préstamos
 */
@WebServlet(name = "GestionPrestamosServlet", urlPatterns = {"/app/gestion-prestamos"})
public class GestionPrestamosServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession(false);
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        try {
            if ("bibliotecario".equals(tipoUsuario)) {
                // Vista para bibliotecarios
                cargarVistaBibliotecario(request, response);
            } else if ("lector".equals(tipoUsuario)) {
                // Vista para lectores
                cargarVistaLector(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=tipo_usuario_invalido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String redirectPath = "bibliotecario".equals(tipoUsuario) ? 
                "/app/bibliotecario/dashboard.jsp" : 
                "/app/lector/dashboard.jsp";
            response.sendRedirect(request.getContextPath() + redirectPath + "?error=error_cargar_prestamos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        try {
            switch (accion) {
                case "crear_prestamo":
                    crearPrestamo(request, response);
                    break;
                case "actualizar_estado":
                    actualizarEstadoPrestamo(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=accion_invalida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_procesar_accion");
        }
    }

    private void cargarVistaBibliotecario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener todos los préstamos
        java.util.List<Prestamo> prestamos = controlador.getListaPrestamos();
        
        // Obtener préstamos por estado
        java.util.List<Prestamo> prestamosEnCurso = controlador.obtenerPrestamosPorEstado(EstadoP.ENCURSO);
        java.util.List<Prestamo> prestamosDevueltos = controlador.obtenerPrestamosPorEstado(EstadoP.DEVUELTO);
        java.util.List<Prestamo> prestamosPendientes = controlador.obtenerPrestamosPorEstado(EstadoP.PENDIENTE);
        
        // Obtener materiales disponibles
        java.util.List<Material> materialesDisponibles = controlador.obtenerMaterialesDisponibles();
        
        // Obtener lectores activos
        java.util.List<Lector> lectoresActivos = controlador.obtenerLectoresActivos();
        
        // Obtener bibliotecarios
        java.util.List<Bibliotecario> bibliotecarios = controlador.obtenerTodosLosBibliotecarios();
        
        request.setAttribute("prestamos", prestamos);
        request.setAttribute("prestamosEnCurso", prestamosEnCurso);
        request.setAttribute("prestamosDevueltos", prestamosDevueltos);
        request.setAttribute("prestamosPendientes", prestamosPendientes);
        request.setAttribute("materialesDisponibles", materialesDisponibles);
        request.setAttribute("lectoresActivos", lectoresActivos);
        request.setAttribute("bibliotecarios", bibliotecarios);
        
        request.getRequestDispatcher("/app/bibliotecario/gestion-prestamos.jsp").forward(request, response);
    }

    private void cargarVistaLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String emailLector = (String) request.getSession().getAttribute("email");
        
        // Obtener préstamos del lector
        java.util.List<Prestamo> prestamosLector = controlador.obtenerPrestamosPorLector(emailLector);
        
        // Agrupar por estado
        java.util.List<Prestamo> prestamosEnCurso = new java.util.ArrayList<>();
        java.util.List<Prestamo> prestamosDevueltos = new java.util.ArrayList<>();
        java.util.List<Prestamo> prestamosPendientes = new java.util.ArrayList<>();
        
        for (Prestamo prestamo : prestamosLector) {
            switch (prestamo.getEstadoP()) {
                case ENCURSO:
                    prestamosEnCurso.add(prestamo);
                    break;
                case DEVUELTO:
                    prestamosDevueltos.add(prestamo);
                    break;
                case PENDIENTE:
                    prestamosPendientes.add(prestamo);
                    break;
            }
        }
        
        request.setAttribute("prestamosLector", prestamosLector);
        request.setAttribute("prestamosEnCurso", prestamosEnCurso);
        request.setAttribute("prestamosDevueltos", prestamosDevueltos);
        request.setAttribute("prestamosPendientes", prestamosPendientes);
        
        request.getRequestDispatcher("/app/lector/mis-prestamos.jsp").forward(request, response);
    }

    private void crearPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!esBibliotecario(request)) {
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=acceso_denegado");
            return;
        }
        
        String idMaterialStr = request.getParameter("idMaterial");
        String emailLector = request.getParameter("emailLector");
        String idBibliotecarioStr = request.getParameter("idBibliotecario");
        String fechaDevolucionStr = request.getParameter("fechaDevolucion");
        
        if (idMaterialStr == null || emailLector == null || idBibliotecarioStr == null || fechaDevolucionStr == null) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=parametros_requeridos");
            return;
        }
        
        try {
            int idMaterial = Integer.parseInt(idMaterialStr);
            int idBibliotecario = Integer.parseInt(idBibliotecarioStr);
            
            // Obtener los objetos necesarios
            Material material = controlador.obtenerMaterial(idMaterial);
            Lector lector = controlador.obtenerLector(emailLector);
            Bibliotecario bibliotecario = controlador.obtenerBibliotecario(emailLector); // Necesitamos obtener por ID
            
            if (material == null || lector == null || bibliotecario == null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=objetos_no_encontrados");
                return;
            }
            
            // Parsear fecha de devolución (formato: YYYY-MM-DD)
            String[] fechaParts = fechaDevolucionStr.split("-");
            DTFecha fechaDevolucion = new DTFecha(
                Integer.parseInt(fechaParts[2]), // día
                Integer.parseInt(fechaParts[1]), // mes
                Integer.parseInt(fechaParts[0])  // año
            );
            
            Integer idPrestamo = controlador.crearPrestamo(material, lector, bibliotecario, fechaDevolucion);
            
            if (idPrestamo != null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?mensaje=prestamo_creado&id=" + idPrestamo);
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_crear_prestamo");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=formato_id_invalido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_procesar_prestamo");
        }
    }

    private void actualizarEstadoPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!esBibliotecario(request)) {
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=acceso_denegado");
            return;
        }
        
        String idPrestamoStr = request.getParameter("idPrestamo");
        String nuevoEstado = request.getParameter("nuevoEstado");
        
        if (idPrestamoStr == null || nuevoEstado == null) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=parametros_requeridos");
            return;
        }
        
        try {
            int idPrestamo = Integer.parseInt(idPrestamoStr);
            EstadoP estado = EstadoP.valueOf(nuevoEstado);
            
            boolean exito = controlador.actualizarEstadoPrestamo(idPrestamo, estado);
            
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?mensaje=estado_actualizado");
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_actualizar_estado");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=formato_id_invalido");
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=estado_invalido");
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
