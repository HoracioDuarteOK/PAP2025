package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet para manejar el login de usuarios
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private IControladorFachada controlador;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar la conexión a la base de datos
        if (!ConfiguracionBD.estaConectado()) {
            ConfiguracionBD.inicializarConexion();
        }
        controlador = ControladorFachada.getInstancia();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al formulario de login
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validación básica
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=campos_vacios");
            return;
        }
        
        try {
            // Buscar el usuario en el sistema
            Usuario usuario = controlador.obtenerUsuario(email);
            
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=usuario_no_encontrado");
                return;
            }
            
            // Verificar el tipo de usuario y redirigir según corresponda
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            session.setAttribute("email", email);
            session.setAttribute("nombre", usuario.getNombre());
            
            if (usuario instanceof Bibliotecario) {
                session.setAttribute("tipoUsuario", "bibliotecario");
                session.setAttribute("nroEmpleado", ((Bibliotecario) usuario).getNroEmpleado());
                response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard.jsp");
            } else if (usuario instanceof Lector) {
                session.setAttribute("tipoUsuario", "lector");
                session.setAttribute("estadoLector", ((Lector) usuario).getEstadoL());
                session.setAttribute("zonaLector", ((Lector) usuario).getZona());
                response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=tipo_usuario_invalido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=error_sistema");
        }
    }
}
