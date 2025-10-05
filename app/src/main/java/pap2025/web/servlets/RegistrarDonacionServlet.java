package pap2025.web.servlets;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
public class RegistrarDonacionServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = Fabrica.getInstancia().getIControladorFachada();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String tipo = request.getParameter("tipo");
            
            if ("libro".equals(tipo)) {
                registrarLibro(request, response);
            } else if ("artespecial".equals(tipo)) {
                registrarArtEspecial(request, response);
            } else {
                request.setAttribute("error", "Tipo de material inválido");
                request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
        }
    }
    
    private void registrarLibro(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String titulo = request.getParameter("titulo");
        String cantPaginasStr = request.getParameter("cantPaginas");
        
        // Validar parámetros
        if (titulo == null || titulo.trim().isEmpty() ||
            cantPaginasStr == null || cantPaginasStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Todos los campos son obligatorios");
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
            return;
        }
        
        // Convertir cantidad de páginas
        int cantPaginas;
        try {
            cantPaginas = Integer.parseInt(cantPaginasStr.trim());
            if (cantPaginas <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "La cantidad de páginas debe ser un número positivo");
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
            return;
        }
        
        // Registrar libro
        Integer idMaterial = controlador.registrarDonacionLibro(titulo.trim(), cantPaginas);
        
        if (idMaterial != null) {
            request.setAttribute("success", "Libro registrado exitosamente con ID: " + idMaterial);
            response.sendRedirect(request.getContextPath() + "/bibliotecario/materiales");
        } else {
            request.setAttribute("error", "Error al registrar el libro");
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
        }
    }
    
    private void registrarArtEspecial(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String descripcion = request.getParameter("descripcion");
        String pesoStr = request.getParameter("peso");
        String anchoStr = request.getParameter("ancho");
        String altoStr = request.getParameter("alto");
        String profundidadStr = request.getParameter("profundidad");
        
        // Validar parámetros
        if (descripcion == null || descripcion.trim().isEmpty() ||
            pesoStr == null || pesoStr.trim().isEmpty() ||
            anchoStr == null || anchoStr.trim().isEmpty() ||
            altoStr == null || altoStr.trim().isEmpty() ||
            profundidadStr == null || profundidadStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Todos los campos son obligatorios");
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
            return;
        }
        
        // Convertir valores numéricos
        double peso, ancho, alto, profundidad;
        try {
            peso = Double.parseDouble(pesoStr.trim());
            ancho = Double.parseDouble(anchoStr.trim());
            alto = Double.parseDouble(altoStr.trim());
            profundidad = Double.parseDouble(profundidadStr.trim());
            
            if (peso <= 0 || ancho <= 0 || alto <= 0 || profundidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Los valores numéricos deben ser positivos");
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
            return;
        }
        
        // Crear dimensiones
        DTDimensiones dimensiones = new DTDimensiones(ancho, alto, profundidad);
        
        // Registrar artículo especial
        Integer idMaterial = controlador.registrarDonacionArtEspecial(descripcion.trim(), peso, dimensiones);
        
        if (idMaterial != null) {
            request.setAttribute("success", "Artículo especial registrado exitosamente con ID: " + idMaterial);
            response.sendRedirect(request.getContextPath() + "/bibliotecario/materiales");
        } else {
            request.setAttribute("error", "Error al registrar el artículo especial");
            request.getRequestDispatcher("/app/bibliotecario/registrar-donacion.jsp").forward(request, response);
        }
    }
}
