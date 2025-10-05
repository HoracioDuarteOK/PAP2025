package pap2025.web.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet para manejar el logout de usuarios
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Limpiar todos los atributos de la sesión
            session.invalidate();
        }
        
        // Redirigir al login con mensaje de logout exitoso
        response.sendRedirect(request.getContextPath() + "/index.jsp?mensaje=logout_exitoso");
    }
}
