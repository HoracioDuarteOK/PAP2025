package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;
import pap2025.interfaz.IControladorFachada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para gestión de usuarios (lectores y bibliotecarios)
 */
@WebServlet(name = "GestionUsuariosServlet", urlPatterns = {"/app/gestion-usuarios"})
public class GestionUsuariosServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = pap2025.logica.ControladorFachada.getInstancia();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            // Obtener todos los usuarios
            List<Lector> lectores = PersistenciaHibernate.obtenerTodosLosLectores();
            List<Bibliotecario> bibliotecarios = PersistenciaHibernate.obtenerTodosLosBibliotecarios();
            
            request.setAttribute("lectores", lectores);
            request.setAttribute("bibliotecarios", bibliotecarios);
            
            // Forward a la página JSP
            request.getRequestDispatcher("/app/bibliotecario/gestion-usuarios.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard?error=error_cargar_usuarios");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

