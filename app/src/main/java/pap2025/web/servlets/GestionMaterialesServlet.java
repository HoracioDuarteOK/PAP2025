package pap2025.web.servlets;

import pap2025.logica.Material;
import pap2025.logica.Libro;
import pap2025.logica.ArtEspeciales;
import pap2025.persistencia.PersistenciaHibernate;
import pap2025.interfaz.IControladorFachada;
import pap2025.datatypes.DTDimensiones;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para gestión de materiales (libros y artículos especiales)
 */
@WebServlet(name = "GestionMaterialesServlet", urlPatterns = {"/app/gestion-materiales"})
public class GestionMaterialesServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = pap2025.logica.ControladorFachada.getInstancia();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            // Obtener todos los materiales
            List<Material> materiales = PersistenciaHibernate.obtenerTodosLosMateriales();
            
            request.setAttribute("materiales", materiales);
            
            // Verificar tipo de usuario para mostrar diferentes vistas
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            if ("bibliotecario".equals(tipoUsuario)) {
                request.getRequestDispatcher("/app/bibliotecario/gestion-materiales.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/app/lector/consultar-materiales.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar materiales: " + e.getMessage());
            e.printStackTrace();
            String returnUrl = "bibliotecario".equals(session.getAttribute("tipoUsuario")) 
                    ? "/app/bibliotecario/dashboard" 
                    : "/app/lector/dashboard";
            response.sendRedirect(request.getContextPath() + returnUrl + "?error=error_cargar_materiales: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            String tipoMaterial = request.getParameter("tipoMaterial");
            
            if ("libro".equals(tipoMaterial)) {
                // Registrar libro
                String titulo = request.getParameter("titulo");
                int cantPaginas = Integer.parseInt(request.getParameter("cantPaginas"));
                
                controlador.registrarDonacionLibro(titulo, cantPaginas);
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?success=libro_registrado");
                
            } else if ("articulo".equals(tipoMaterial)) {
                // Registrar artículo especial
                String descripcion = request.getParameter("descripcion");
                double peso = Double.parseDouble(request.getParameter("peso"));
                double alto = Double.parseDouble(request.getParameter("alto"));
                double ancho = Double.parseDouble(request.getParameter("ancho"));
                double prof = Double.parseDouble(request.getParameter("profundidad"));
                
                DTDimensiones dimensiones = new DTDimensiones(alto, ancho, prof);
                controlador.registrarDonacionArtEspecial(descripcion, peso, dimensiones);
                
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?success=articulo_registrado");
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=tipo_material_invalido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=error_registrar_material");
        }
    }
}

