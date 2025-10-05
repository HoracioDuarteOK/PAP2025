package pap2025.web.servlets;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class RegistrarBibliotecarioServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = Fabrica.getInstancia().getIControladorFachada();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/app/bibliotecario/registrar-bibliotecario.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String nroEmpleadoStr = request.getParameter("nroEmpleado");
            
            // Validar parámetros
            if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                nroEmpleadoStr == null || nroEmpleadoStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/app/bibliotecario/registrar-bibliotecario.jsp").forward(request, response);
                return;
            }
            
            // Convertir número de empleado
            int nroEmpleado;
            try {
                nroEmpleado = Integer.parseInt(nroEmpleadoStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Número de empleado debe ser un número válido");
                request.getRequestDispatcher("/app/bibliotecario/registrar-bibliotecario.jsp").forward(request, response);
                return;
            }
            
            // Registrar bibliotecario
            boolean exito = controlador.registrarBibliotecario(nombre.trim(), email.trim(), nroEmpleado);
            
            if (exito) {
                request.setAttribute("success", "Bibliotecario registrado exitosamente");
                response.sendRedirect(request.getContextPath() + "/bibliotecario/usuarios");
            } else {
                request.setAttribute("error", "Error al registrar el bibliotecario. Verifique que el email o número de empleado no estén en uso.");
                request.getRequestDispatcher("/app/bibliotecario/registrar-bibliotecario.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/app/bibliotecario/registrar-bibliotecario.jsp").forward(request, response);
        }
    }
}
