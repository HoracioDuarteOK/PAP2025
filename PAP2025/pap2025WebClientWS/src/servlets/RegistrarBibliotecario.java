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

@WebServlet("/RegistrarBibliotecario")
public class RegistrarBibliotecario extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public RegistrarBibliotecario() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/registrarBibliotecario.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int nroEmpleado = Integer.valueOf(request.getParameter("nroEmpleado"));
        
        try {
            // Registrar el bibliotecario
            boolean exito = registrarBibliotecario(nombre, email, password, nroEmpleado);
            
            if (exito) {
                request.setAttribute("mensaje", "Se ha registrado correctamente al bibliotecario " + nombre + " (" + email + ") en el sistema.");
            } else {
                request.setAttribute("error", "Error al registrar el bibliotecario. Verifique los datos ingresados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el servidor: " + e.getMessage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/notificacion.jsp");
        rd.forward(request, response);
    }
    
    // OPERACIÓN CONSUMIDA
    public boolean registrarBibliotecario(String nombre, String email, String password, int nroEmpleado) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.registrarBibliotecario(nombre, email, password, nroEmpleado);
    }
}
