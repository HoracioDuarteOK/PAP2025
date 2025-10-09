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

/**
 * Servlet para cambiar el estado de un lector (ACTIVO/SUSPENDIDO)
 */
@WebServlet(name = "CambiarEstadoLectorServlet", urlPatterns = {"/app/cambiar-estado-lector"})
public class CambiarEstadoLectorServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            String email = request.getParameter("email");
            String nuevoEstado = request.getParameter("estado");
            
            if (email == null || nuevoEstado == null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=parametros_invalidos");
                return;
            }
            
            // Buscar el lector
            Usuario usuario = PersistenciaHibernate.obtenerUsuarioPorEmail(email);
            
            if (!(usuario instanceof Lector)) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=lector_no_encontrado");
                return;
            }
            
            Lector lector = (Lector) usuario;
            
            // Cambiar el estado
            if ("ACTIVO".equals(nuevoEstado)) {
                lector.setEstadoL(EstadoL.ACTIVO);
            } else if ("SUSPENDIDO".equals(nuevoEstado)) {
                lector.setEstadoL(EstadoL.SUSPENDIDO);
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=estado_invalido");
                return;
            }
            
            // Actualizar en la base de datos
            PersistenciaHibernate.actualizarUsuario(lector);
            
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?success=estado_actualizado");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=error_actualizar_estado");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios");
    }
}

