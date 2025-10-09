package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para el dashboard del lector
 */
@WebServlet(name = "LectorDashboardServlet", urlPatterns = {"/app/lector/dashboard"})
public class LectorDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"lector".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            // Obtener email del usuario
            String email = (String) session.getAttribute("email");
            Usuario usuario = PersistenciaHibernate.obtenerUsuarioPorEmail(email);
            
            if (!(usuario instanceof Lector)) {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=lector_no_encontrado");
                return;
            }
            
            Lector lector = (Lector) usuario;
            
            // Obtener préstamos del lector
            List<Prestamo> prestamosLector = PersistenciaHibernate.obtenerPrestamosPorLector(lector);
            
            // Contar préstamos por estado
            long totalPrestamos = prestamosLector.size();
            long prestamosActivos = prestamosLector.stream()
                    .filter(p -> p.getEstadoP() == EstadoP.PENDIENTE || p.getEstadoP() == EstadoP.ENCURSO)
                    .count();
            long prestamosDevueltos = prestamosLector.stream()
                    .filter(p -> p.getEstadoP() == EstadoP.DEVUELTO)
                    .count();
            
            // Pasar datos al JSP
            request.setAttribute("lector", lector);
            request.setAttribute("prestamosLector", prestamosLector);
            request.setAttribute("totalPrestamos", totalPrestamos);
            request.setAttribute("prestamosActivos", prestamosActivos);
            request.setAttribute("prestamosDevueltos", prestamosDevueltos);
            
            // Forward a la página JSP
            request.getRequestDispatcher("/app/lector/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=error_cargar_dashboard");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

