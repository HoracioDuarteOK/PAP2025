package servlets;

import java.io.IOException;
import java.util.ArrayList;

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
import publicadores.DtMaterial;
import publicadores.DtDimensiones;

@WebServlet("/GestionMateriales")
public class GestionMateriales extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public GestionMateriales() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificar que el usuario esté logueado como bibliotecario
        HttpSession session = request.getSession();
        if (!"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            request.setAttribute("error", "Acceso denegado. Solo bibliotecarios pueden gestionar materiales.");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
            return;
        }
        
        try {
            // Obtener lista de materiales
            DtMaterial[] materiales = consultarDonaciones();
            ArrayList<DtMaterial> listaMateriales = new ArrayList<>();
            for (DtMaterial material : materiales) {
                listaMateriales.add(material);
            }
            request.setAttribute("materiales", listaMateriales);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar materiales: " + e.getMessage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/gestionMateriales.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificar que el usuario esté logueado como bibliotecario
        HttpSession session = request.getSession();
        if (!"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            request.setAttribute("error", "Acceso denegado. Solo bibliotecarios pueden gestionar materiales.");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
            return;
        }
        
        String tipoMaterial = request.getParameter("tipoMaterial");
        
        try {
            if ("libro".equals(tipoMaterial)) {
                // Registrar libro
                String titulo = request.getParameter("titulo");
                int cantidadPaginas = Integer.valueOf(request.getParameter("cantidadPaginas"));
                
                boolean exito = registrarLibro(titulo, cantidadPaginas);
                if (exito) {
                    request.setAttribute("mensaje", "Se ha registrado correctamente el libro: " + titulo);
                } else {
                    request.setAttribute("error", "Error al registrar el libro");
                }
            } else if ("articulo".equals(tipoMaterial)) {
                // Registrar artículo especial
                String descripcion = request.getParameter("descripcion");
                double peso = Double.valueOf(request.getParameter("peso"));
                
                DtDimensiones dimensiones = new DtDimensiones();
                dimensiones.setLargo(Double.valueOf(request.getParameter("largo")));
                dimensiones.setAncho(Double.valueOf(request.getParameter("ancho")));
                dimensiones.setAlto(Double.valueOf(request.getParameter("alto")));
                
                boolean exito = registrarArticuloEspecial(descripcion, peso, dimensiones);
                if (exito) {
                    request.setAttribute("mensaje", "Se ha registrado correctamente el artículo: " + descripcion);
                } else {
                    request.setAttribute("error", "Error al registrar el artículo especial");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el servidor: " + e.getMessage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/notificacion.jsp");
        rd.forward(request, response);
    }
    
    // OPERACIONES CONSUMIDAS
    public boolean registrarLibro(String titulo, int cantidadPaginas) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.registrarLibro(titulo, cantidadPaginas);
    }
    
    public boolean registrarArticuloEspecial(String descripcion, double peso, DtDimensiones dimensiones) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.registrarArticuloEspecial(descripcion, peso, dimensiones);
    }
    
    public DtMaterial[] consultarDonaciones() throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.consultarDonaciones();
    }
}
