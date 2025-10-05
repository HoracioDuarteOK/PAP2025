package pap2025.web.servlets;

import pap2025.fabrica.Fabrica;
import pap2025.interfaz.IControladorFachada;
import pap2025.logica.*;
import pap2025.datatypes.DTFecha;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
public class GestionarPrestamosServlet extends HttpServlet {
    
    private IControladorFachada controlador;
    
    @Override
    public void init() throws ServletException {
        controlador = Fabrica.getInstancia().getIControladorFachada();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Cargar datos para los combos
        List<Material> materialesDisponibles = controlador.obtenerMaterialesDisponibles();
        List<Lector> lectoresActivos = controlador.obtenerLectoresPorEstado(EstadoL.ACTIVO);
        List<Bibliotecario> bibliotecarios = controlador.getListaBibliotecarios();
        List<Prestamo> prestamos = controlador.getListaPrestamos();
        
        request.setAttribute("materialesDisponibles", materialesDisponibles);
        request.setAttribute("lectoresActivos", lectoresActivos);
        request.setAttribute("bibliotecarios", bibliotecarios);
        request.setAttribute("prestamos", prestamos);
        
        request.getRequestDispatcher("/app/bibliotecario/gestionar-prestamos.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String accion = request.getParameter("accion");
            
            if ("crear".equals(accion)) {
                crearPrestamo(request, response);
            } else if ("actualizar".equals(accion)) {
                actualizarPrestamo(request, response);
            } else {
                request.setAttribute("error", "Acción no válida");
                doGet(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error interno: " + e.getMessage());
            doGet(request, response);
        }
    }
    
    private void crearPrestamo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String materialIdStr = request.getParameter("materialId");
        String lectorEmail = request.getParameter("lectorEmail");
        String bibliotecarioEmail = request.getParameter("bibliotecarioEmail");
        String fechaDevolucionStr = request.getParameter("fechaDevolucion");
        
        // Validar parámetros
        if (materialIdStr == null || materialIdStr.trim().isEmpty() ||
            lectorEmail == null || lectorEmail.trim().isEmpty() ||
            bibliotecarioEmail == null || bibliotecarioEmail.trim().isEmpty() ||
            fechaDevolucionStr == null || fechaDevolucionStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Todos los campos son obligatorios");
            doGet(request, response);
            return;
        }
        
        // Convertir ID del material
        int materialId;
        try {
            materialId = Integer.parseInt(materialIdStr.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de material inválido");
            doGet(request, response);
            return;
        }
        
        // Obtener objetos
        Material material = controlador.obtenerMaterial(materialId);
        Lector lector = (Lector) controlador.obtenerUsuario(lectorEmail.trim());
        Bibliotecario bibliotecario = controlador.obtenerBibliotecario(bibliotecarioEmail.trim());
        
        if (material == null || lector == null || bibliotecario == null) {
            request.setAttribute("error", "Material, lector o bibliotecario no encontrado");
            doGet(request, response);
            return;
        }
        
        // Crear fecha de devolución
        LocalDate fechaDevolucion = LocalDate.parse(fechaDevolucionStr);
        DTFecha dtFechaDevolucion = new DTFecha(fechaDevolucion.getDayOfMonth(), 
                                               fechaDevolucion.getMonthValue(), 
                                               fechaDevolucion.getYear());
        
        // Crear préstamo
        Integer idPrestamo = controlador.crearPrestamo(material, lector, bibliotecario, dtFechaDevolucion);
        
        if (idPrestamo != null) {
            request.setAttribute("success", "Préstamo creado exitosamente con ID: " + idPrestamo);
        } else {
            request.setAttribute("error", "Error al crear el préstamo. Verifique que el material esté disponible y el lector esté activo.");
        }
        
        doGet(request, response);
    }
    
    private void actualizarPrestamo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String prestamoIdStr = request.getParameter("prestamoId");
        String nuevoEstadoStr = request.getParameter("nuevoEstado");
        
        // Validar parámetros
        if (prestamoIdStr == null || prestamoIdStr.trim().isEmpty() ||
            nuevoEstadoStr == null || nuevoEstadoStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Todos los campos son obligatorios");
            doGet(request, response);
            return;
        }
        
        // Convertir ID del préstamo
        int prestamoId;
        try {
            prestamoId = Integer.parseInt(prestamoIdStr.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de préstamo inválido");
            doGet(request, response);
            return;
        }
        
        // Convertir estado
        EstadoP nuevoEstado;
        try {
            nuevoEstado = EstadoP.valueOf(nuevoEstadoStr);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Estado inválido");
            doGet(request, response);
            return;
        }
        
        // Obtener préstamo
        Prestamo prestamo = controlador.obtenerPrestamo(prestamoId);
        if (prestamo == null) {
            request.setAttribute("error", "Préstamo no encontrado");
            doGet(request, response);
            return;
        }
        
        // Actualizar estado
        boolean exito = controlador.actualizarEstadoPrestamo(prestamoId, nuevoEstado);
        
        if (exito) {
            request.setAttribute("success", "Estado del préstamo actualizado exitosamente");
        } else {
            request.setAttribute("error", "Error al actualizar el estado del préstamo");
        }
        
        doGet(request, response);
    }
}
