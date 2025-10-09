package pap2025.web.servlets;

import pap2025.logica.Prestamo;
import pap2025.logica.EstadoP;
import pap2025.persistencia.PersistenciaHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet para cambiar el estado de un préstamo
 */
@WebServlet(name = "CambiarEstadoPrestamoServlet", urlPatterns = {"/app/cambiar-estado-prestamo"})
public class CambiarEstadoPrestamoServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            String nuevoEstado = request.getParameter("estado");
            
            if (nuevoEstado == null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=parametros_invalidos");
                return;
            }
            
            // Buscar el préstamo
            Prestamo prestamo = PersistenciaHibernate.obtenerPrestamoPorId(idPrestamo);
            
            if (prestamo == null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=prestamo_no_encontrado");
                return;
            }
            
            // Cambiar el estado
            EstadoP estadoP;
            try {
                estadoP = EstadoP.valueOf(nuevoEstado);
            } catch (IllegalArgumentException e) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=estado_invalido");
                return;
            }
            
            prestamo.setEstadoP(estadoP);
            
            // Actualizar en la base de datos
            PersistenciaHibernate.actualizarPrestamo(prestamo);
            
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?success=estado_prestamo_actualizado");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_actualizar_estado_prestamo");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos");
    }
}

