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
 * Servlet para el dashboard del bibliotecario
 */
@WebServlet(name = "BibliotecarioDashboardServlet", urlPatterns = {"/app/bibliotecario/dashboard"})
public class BibliotecarioDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            // Obtener estadísticas
            List<Usuario> usuarios = PersistenciaHibernate.obtenerTodosLosUsuarios();
            List<Material> materiales = PersistenciaHibernate.obtenerTodosLosMateriales();
            List<Prestamo> prestamos = PersistenciaHibernate.obtenerTodosLosPrestamos();
            List<Lector> lectores = PersistenciaHibernate.obtenerTodosLosLectores();
            
            // Contar total de usuarios
            request.setAttribute("totalUsuarios", usuarios.size());
            
            // Contar total de materiales
            request.setAttribute("totalMateriales", materiales.size());
            
            // Contar total de préstamos
            request.setAttribute("totalPrestamos", prestamos.size());
            
            // Contar lectores activos
            long lectoresActivos = lectores.stream()
                    .filter(l -> l.getEstadoL() == EstadoL.ACTIVO)
                    .count();
            request.setAttribute("lectoresActivos", lectoresActivos);
            
            // Contar préstamos por estado
            long prestamosPendientes = prestamos.stream()
                    .filter(p -> p.getEstadoP() == EstadoP.PENDIENTE)
                    .count();
            long prestamosEnCurso = prestamos.stream()
                    .filter(p -> p.getEstadoP() == EstadoP.ENCURSO)
                    .count();
            long prestamosDevueltos = prestamos.stream()
                    .filter(p -> p.getEstadoP() == EstadoP.DEVUELTO)
                    .count();
            
            request.setAttribute("prestamosPendientes", prestamosPendientes);
            request.setAttribute("prestamosEnCurso", prestamosEnCurso);
            request.setAttribute("prestamosDevueltos", prestamosDevueltos);
            
            // Contar materiales por tipo
            long totalLibros = materiales.stream()
                    .filter(m -> m instanceof Libro)
                    .count();
            long totalArtEspeciales = materiales.stream()
                    .filter(m -> m instanceof ArtEspeciales)
                    .count();
            
            request.setAttribute("totalLibros", totalLibros);
            request.setAttribute("totalArtEspeciales", totalArtEspeciales);
            
            // Forward a la página JSP
            request.getRequestDispatcher("/app/bibliotecario/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard.jsp?error=error_cargar_dashboard");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

