package pap2025.web.servlets;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Lector;
import pap2025.logica.EstadoL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class ModificarEstadoLectorServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = Fabrica.getInstancia().getIControladorFachada();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        if (email != null && !email.trim().isEmpty()) {
            Lector lector = (Lector) controlador.obtenerUsuario(email.trim());
            if (lector != null) {
                request.setAttribute("lector", lector);
            } else {
                request.setAttribute("error", "Lector no encontrado");
            }
        }
        
        request.getRequestDispatcher("/app/bibliotecario/modificar-estado-lector.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String email = request.getParameter("email");
            String nuevoEstadoStr = request.getParameter("nuevoEstado");
            
            // Validar parámetros
            if (email == null || email.trim().isEmpty() ||
                nuevoEstadoStr == null || nuevoEstadoStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/app/bibliotecario/modificar-estado-lector.jsp").forward(request, response);
                return;
            }
            
            // Obtener lector
            Lector lector = (Lector) controlador.obtenerUsuario(email.trim());
            if (lector == null) {
                request.setAttribute("error", "Lector no encontrado");
                request.getRequestDispatcher("/app/bibliotecario/modificar-estado-lector.jsp").forward(request, response);
                return;
            }
            
            // Convertir estado
            EstadoL nuevoEstado;
            try {
                nuevoEstado = EstadoL.valueOf(nuevoEstadoStr);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Estado inválido");
                request.getRequestDispatcher("/app/bibliotecario/modificar-estado-lector.jsp").forward(request, response);
                return;
            }
            
            // Modificar estado
            boolean exito = controlador.modificarEstadoLector(email.trim(), nuevoEstado);
            
            if (exito) {
                request.setAttribute("success", "Estado del lector modificado exitosamente");
                response.sendRedirect(request.getContextPath() + "/bibliotecario/usuarios");
            } else {
                request.setAttribute("error", "Error al modificar el estado del lector");
                request.getRequestDispatcher("/app/bibliotecario/modificar-estado-lector.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/app/bibliotecario/modificar-estado-lector.jsp").forward(request, response);
        }
    }
}
