package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.ControladorPublishServiceLocator;
import publicadores.DtFecha;
import publicadores.Zona;

@WebServlet("/RegistrarLector")
public class RegistrarLector extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public RegistrarLector() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/registrarLector.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String direccion = request.getParameter("direccion");
        String zonaStr = request.getParameter("zona");
        
        try {
            // Crear fecha de hoy
            DtFecha fechaHoy = new DtFecha();
            java.time.LocalDate hoy = java.time.LocalDate.now();
            fechaHoy.setDia(hoy.getDayOfMonth());
            fechaHoy.setMes(hoy.getMonthValue());
            fechaHoy.setAnio(hoy.getYear());
            
            // Convertir zona
            Zona zona = Zona.valueOf(zonaStr);
            
            // Registrar el lector
            boolean exito = registrarLector(nombre, email, password, direccion, fechaHoy, zona);
            
            if (exito) {
                request.setAttribute("mensaje", "Se ha registrado correctamente al lector " + nombre + " (" + email + ") en el sistema.");
            } else {
                request.setAttribute("error", "Error al registrar el lector. Verifique los datos ingresados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el servidor: " + e.getMessage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/notificacion.jsp");
        rd.forward(request, response);
    }
    
    // OPERACIÓN CONSUMIDA
    public boolean registrarLector(String nombre, String email, String password, String direccion, DtFecha fechaRegistro, Zona zona) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.registrarLector(nombre, email, password, direccion, fechaRegistro, zona);
    }
}
