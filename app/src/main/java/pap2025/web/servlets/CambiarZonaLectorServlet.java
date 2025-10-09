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
 * Servlet para cambiar la zona de un lector
 */
@WebServlet(name = "CambiarZonaLectorServlet", urlPatterns = {"/app/cambiar-zona-lector"})
public class CambiarZonaLectorServlet extends HttpServlet {
    
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
            String nuevaZona = request.getParameter("zona");
            
            if (email == null || nuevaZona == null) {
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
            
            // Cambiar la zona
            try {
                Zona zona = Zona.valueOf(nuevaZona);
                lector.setZona(zona);
                
                // Actualizar en la base de datos
                PersistenciaHibernate.actualizarUsuario(lector);
                
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?success=zona_actualizada");
            } catch (IllegalArgumentException e) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=zona_invalida");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios?error=error_actualizar_zona");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/app/gestion-usuarios");
    }
}

