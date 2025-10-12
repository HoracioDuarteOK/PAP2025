package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;
import pap2025.datatypes.DTFecha;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet para actualizar información completa de un préstamo
 */
@WebServlet(name = "ActualizarPrestamoServlet", urlPatterns = {"/app/actualizar-prestamo"})
public class ActualizarPrestamoServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            // Obtener ID del préstamo
            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            
            // Obtener el préstamo actual
            Prestamo prestamo = PersistenciaHibernate.obtenerPrestamoPorId(idPrestamo);
            
            if (prestamo == null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=prestamo_no_encontrado");
                return;
            }
            
            // Actualizar material si se especifica
            String idMaterialStr = request.getParameter("idMaterial");
            if (idMaterialStr != null && !idMaterialStr.isEmpty()) {
                int idMaterial = Integer.parseInt(idMaterialStr);
                Material nuevoMaterial = PersistenciaHibernate.obtenerMaterialPorId(idMaterial);
                if (nuevoMaterial != null) {
                    prestamo.setMaterial(nuevoMaterial);
                }
            }
            
            // Actualizar lector si se especifica
            String emailLector = request.getParameter("emailLector");
            if (emailLector != null && !emailLector.isEmpty()) {
                Usuario usuarioLector = PersistenciaHibernate.obtenerUsuarioPorEmail(emailLector);
                if (usuarioLector instanceof Lector) {
                    prestamo.setLector((Lector) usuarioLector);
                }
            }
            
            // Actualizar bibliotecario si se especifica
            String emailBibliotecario = request.getParameter("emailBibliotecario");
            if (emailBibliotecario != null && !emailBibliotecario.isEmpty()) {
                Usuario usuarioBibliotecario = PersistenciaHibernate.obtenerUsuarioPorEmail(emailBibliotecario);
                if (usuarioBibliotecario instanceof Bibliotecario) {
                    prestamo.setBibliotecario((Bibliotecario) usuarioBibliotecario);
                }
            }
            
            // Actualizar fecha de devolución si se especifica
            String fechaDevolucion = request.getParameter("fechaDevolucion");
            if (fechaDevolucion != null && !fechaDevolucion.isEmpty()) {
                String[] parts = fechaDevolucion.split("-");
                int anio = Integer.parseInt(parts[0]);
                int mes = Integer.parseInt(parts[1]);
                int dia = Integer.parseInt(parts[2]);
                prestamo.setFechaDevolucion(new DTFecha(dia, mes, anio));
            }
            
            // Actualizar estado si se especifica
            String estado = request.getParameter("estado");
            if (estado != null && !estado.isEmpty()) {
                prestamo.setEstadoP(EstadoP.valueOf(estado));
            }
            
            // Guardar cambios
            PersistenciaHibernate.actualizarPrestamo(prestamo);
            
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?success=prestamo_actualizado");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_actualizar_prestamo");
        }
    }
}

