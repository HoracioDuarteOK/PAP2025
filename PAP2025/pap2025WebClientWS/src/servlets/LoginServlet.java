package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.ControladorPublishServiceLocator;
import publicadores.DtLector;
import publicadores.DtBibliotecario;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir al formulario de login
        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String tipoUsuario = request.getParameter("tipoUsuario");
        
        try {
            if ("lector".equals(tipoUsuario)) {
                // Intentar login como lector
                DtLector lector = autenticarLector(email, password);
                if (lector != null) {
                    // Login exitoso
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", lector);
                    session.setAttribute("tipoUsuario", "lector");
                    session.setAttribute("nombreUsuario", lector.getNombre());
                    
                    request.setAttribute("mensaje", "Bienvenido " + lector.getNombre() + " (Lector)");
                    request.setAttribute("usuario", lector);
                    RequestDispatcher rd = request.getRequestDispatcher("/dashboardLector.jsp");
                    rd.forward(request, response);
                } else {
                    // Login fallido
                    request.setAttribute("error", "Credenciales incorrectas para lector");
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                }
            } else if ("bibliotecario".equals(tipoUsuario)) {
                // Intentar login como bibliotecario
                DtBibliotecario bibliotecario = autenticarBibliotecario(email, password);
                if (bibliotecario != null) {
                    // Login exitoso
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", bibliotecario);
                    session.setAttribute("tipoUsuario", "bibliotecario");
                    session.setAttribute("nombreUsuario", bibliotecario.getNombre());
                    
                    request.setAttribute("mensaje", "Bienvenido " + bibliotecario.getNombre() + " (Bibliotecario)");
                    request.setAttribute("usuario", bibliotecario);
                    RequestDispatcher rd = request.getRequestDispatcher("/dashboardBibliotecario.jsp");
                    rd.forward(request, response);
                } else {
                    // Login fallido
                    request.setAttribute("error", "Credenciales incorrectas para bibliotecario");
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                }
            } else {
                request.setAttribute("error", "Tipo de usuario no válido");
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el servidor: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
    }
    
    // OPERACIONES CONSUMIDAS
    public DtLector autenticarLector(String email, String password) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.autenticarLector(email, password);
    }
    
    public DtBibliotecario autenticarBibliotecario(String email, String password) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.autenticarBibliotecario(email, password);
    }
}
