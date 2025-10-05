package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet para gestión de materiales (donaciones)
 */
@WebServlet(name = "GestionMaterialesServlet", urlPatterns = {"/app/gestion-materiales"})
public class GestionMaterialesServlet extends HttpServlet {

    private IControladorFachada controlador;

    @Override
    public void init() throws ServletException {
        super.init();
        if (!ConfiguracionBD.estaConectado()) {
            ConfiguracionBD.inicializarConexion();
        }
        controlador = ControladorFachada.getInstancia();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Obtener todos los materiales
            java.util.List<Material> materiales = controlador.obtenerTodasLasDonaciones();
            java.util.List<Libro> libros = controlador.obtenerTodasLasDonacionesLibros();
            java.util.List<ArtEspeciales> artEspeciales = controlador.obtenerTodasLasDonacionesArtEspeciales();
            
            request.setAttribute("materiales", materiales);
            request.setAttribute("libros", libros);
            request.setAttribute("artEspeciales", artEspeciales);
            
            // Obtener estadísticas
            int totalMateriales = controlador.obtenerCantidadMateriales();
            int totalLibros = controlador.obtenerCantidadLibros();
            int totalArtEspeciales = controlador.obtenerCantidadArtEspeciales();
            
            request.setAttribute("totalMateriales", totalMateriales);
            request.setAttribute("totalLibros", totalLibros);
            request.setAttribute("totalArtEspeciales", totalArtEspeciales);
            
            // Determinar la vista según el tipo de usuario
            String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
            String vista = "bibliotecario".equals(tipoUsuario) ? 
                "/app/bibliotecario/gestion-materiales.jsp" : 
                "/app/lector/consultar-materiales.jsp";
            
            request.getRequestDispatcher(vista).forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
            String redirectPath = "bibliotecario".equals(tipoUsuario) ? 
                "/app/bibliotecario/dashboard.jsp" : 
                "/app/lector/dashboard.jsp";
            response.sendRedirect(request.getContextPath() + redirectPath + "?error=error_cargar_materiales");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Solo bibliotecarios pueden registrar donaciones
        if (!esBibliotecario(request)) {
            response.sendRedirect(request.getContextPath() + "/app/lector/dashboard.jsp?error=acceso_denegado");
            return;
        }

        String accion = request.getParameter("accion");
        
        try {
            switch (accion) {
                case "registrar_libro":
                    registrarDonacionLibro(request, response);
                    break;
                case "registrar_art_especial":
                    registrarDonacionArtEspecial(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=accion_invalida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=error_procesar_donacion");
        }
    }

    private void registrarDonacionLibro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String titulo = request.getParameter("titulo");
        String cantPaginasStr = request.getParameter("cantPaginas");
        
        if (titulo == null || titulo.trim().isEmpty() || 
            cantPaginasStr == null || cantPaginasStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=campos_libro_requeridos");
            return;
        }
        
        try {
            int cantPaginas = Integer.parseInt(cantPaginasStr);
            
            if (cantPaginas <= 0) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=paginas_invalidas");
                return;
            }
            
            Integer idLibro = controlador.registrarDonacionLibro(titulo, cantPaginas);
            
            if (idLibro != null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?mensaje=libro_registrado&id=" + idLibro);
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=error_registrar_libro");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=formato_paginas_invalido");
        }
    }

    private void registrarDonacionArtEspecial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String descripcion = request.getParameter("descripcion");
        String pesoStr = request.getParameter("peso");
        String anchoStr = request.getParameter("ancho");
        String altoStr = request.getParameter("alto");
        String profundoStr = request.getParameter("profundo");
        
        if (descripcion == null || descripcion.trim().isEmpty() || 
            pesoStr == null || pesoStr.trim().isEmpty() ||
            anchoStr == null || anchoStr.trim().isEmpty() ||
            altoStr == null || altoStr.trim().isEmpty() ||
            profundoStr == null || profundoStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=campos_art_especial_requeridos");
            return;
        }
        
        try {
            double peso = Double.parseDouble(pesoStr);
            double ancho = Double.parseDouble(anchoStr);
            double alto = Double.parseDouble(altoStr);
            double profundo = Double.parseDouble(profundoStr);
            
            if (peso <= 0 || ancho <= 0 || alto <= 0 || profundo <= 0) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=valores_dimensiones_invalidos");
                return;
            }
            
            DTDimensiones dimensiones = new DTDimensiones(ancho, alto, profundo);
            Integer idArtEspecial = controlador.registrarDonacionArtEspecial(descripcion, peso, dimensiones);
            
            if (idArtEspecial != null) {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?mensaje=art_especial_registrado&id=" + idArtEspecial);
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=error_registrar_art_especial");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/app/gestion-materiales?error=formato_numeros_invalido");
        }
    }

    private boolean esBibliotecario(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        return "bibliotecario".equals(tipoUsuario);
    }
}
