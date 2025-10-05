package pap2025.web.servlets;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Lector;
import pap2025.logica.Zona;
import pap2025.logica.EstadoL;
import pap2025.datatypes.DTFecha;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
public class RegistrarLectorServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = Fabrica.getInstancia().getIControladorFachada();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/app/bibliotecario/registrar-lector.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String direccion = request.getParameter("direccion");
            String zonaStr = request.getParameter("zona");
            
            // Validar parámetros
            if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                direccion == null || direccion.trim().isEmpty() ||
                zonaStr == null || zonaStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/app/bibliotecario/registrar-lector.jsp").forward(request, response);
                return;
            }
            
            // Convertir zona
            Zona zona;
            try {
                zona = Zona.valueOf(zonaStr);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Zona inválida");
                request.getRequestDispatcher("/app/bibliotecario/registrar-lector.jsp").forward(request, response);
                return;
            }
            
            // Crear fecha de hoy
            LocalDate hoy = LocalDate.now();
            DTFecha fechaRegistro = new DTFecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
            
            // Registrar lector
            boolean exito = controlador.registrarLector(nombre.trim(), email.trim(), direccion.trim(), fechaRegistro, zona);
            
            if (exito) {
                request.setAttribute("success", "Lector registrado exitosamente");
                response.sendRedirect(request.getContextPath() + "/bibliotecario/usuarios");
            } else {
                request.setAttribute("error", "Error al registrar el lector. Verifique que el email no esté en uso.");
                request.getRequestDispatcher("/app/bibliotecario/registrar-lector.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/app/bibliotecario/registrar-lector.jsp").forward(request, response);
        }
    }
}
