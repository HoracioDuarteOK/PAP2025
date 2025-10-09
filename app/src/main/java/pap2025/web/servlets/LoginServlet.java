package pap2025.web.servlets;

import pap2025.logica.Usuario;
import pap2025.logica.Bibliotecario;
import pap2025.logica.Lector;
import pap2025.persistencia.PersistenciaHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet para el manejo del login de usuarios
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward a la página de login
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=Debe ingresar email y contraseña");
            return;
        }
        
        try {
            // Buscar usuario por email
            Usuario usuario = PersistenciaHibernate.obtenerUsuarioPorEmail(email);
            
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=Email o contraseña incorrectos");
                return;
            }
            
            // Validar contraseña
            if (!usuario.getPassword().equals(password)) {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=Email o contraseña incorrectos");
                return;
            }
            
            // Crear sesión
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            session.setAttribute("email", usuario.getEmail());
            session.setAttribute("nombre", usuario.getNombre());
            
            // Verificar si es bibliotecario o lector y redirigir
            if (usuario instanceof Bibliotecario) {
                Bibliotecario biblio = (Bibliotecario) usuario;
                session.setAttribute("tipoUsuario", "bibliotecario");
                session.setAttribute("nroEmpleado", biblio.getNroEmpleado());
                response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard");
            } else if (usuario instanceof Lector) {
                Lector lector = (Lector) usuario;
                session.setAttribute("tipoUsuario", "lector");
                session.setAttribute("estadoLector", lector.getEstadoL().name());
                session.setAttribute("zonaLector", lector.getZona().name());
                response.sendRedirect(request.getContextPath() + "/app/lector/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=invalid");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=Error en el sistema");
        }
    }
}

