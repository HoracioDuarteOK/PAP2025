package pap2025.web.servlets;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Lector;
import pap2025.logica.Zona;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class CambiarZonaLectorServlet extends HttpServlet {
    
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
        
        request.getRequestDispatcher("/app/bibliotecario/cambiar-zona-lector.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String email = request.getParameter("email");
            String nuevaZonaStr = request.getParameter("nuevaZona");
            
            // Validar parámetros
            if (email == null || email.trim().isEmpty() ||
                nuevaZonaStr == null || nuevaZonaStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/app/bibliotecario/cambiar-zona-lector.jsp").forward(request, response);
                return;
            }
            
            // Obtener lector
            Lector lector = (Lector) controlador.obtenerUsuario(email.trim());
            if (lector == null) {
                request.setAttribute("error", "Lector no encontrado");
                request.getRequestDispatcher("/app/bibliotecario/cambiar-zona-lector.jsp").forward(request, response);
                return;
            }
            
            // Convertir zona
            Zona nuevaZona;
            try {
                nuevaZona = Zona.valueOf(nuevaZonaStr);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Zona inválida");
                request.getRequestDispatcher("/app/bibliotecario/cambiar-zona-lector.jsp").forward(request, response);
                return;
            }
            
            // Cambiar zona
            boolean exito = controlador.cambiarZonaLector(email.trim(), nuevaZona);
            
            if (exito) {
                request.setAttribute("success", "Zona del lector cambiada exitosamente");
                response.sendRedirect(request.getContextPath() + "/bibliotecario/usuarios");
            } else {
                request.setAttribute("error", "Error al cambiar la zona del lector");
                request.getRequestDispatcher("/app/bibliotecario/cambiar-zona-lector.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/app/bibliotecario/cambiar-zona-lector.jsp").forward(request, response);
        }
    }
}
