package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;
import pap2025.interfaz.IControladorFachada;
import pap2025.datatypes.DTFecha;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet para gestión de préstamos
 */
@WebServlet(name = "GestionPrestamosServlet", urlPatterns = {"/app/gestion-prestamos"})
public class GestionPrestamosServlet extends HttpServlet {
    
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
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        try {
            if ("bibliotecario".equals(tipoUsuario)) {
                // Vista de bibliotecario - todos los préstamos
                List<Prestamo> prestamos = PersistenciaHibernate.obtenerTodosLosPrestamos();
                List<Lector> lectores = PersistenciaHibernate.obtenerTodosLosLectores();
                List<Material> materiales = PersistenciaHibernate.obtenerTodosLosMateriales();
                List<Bibliotecario> bibliotecarios = PersistenciaHibernate.obtenerTodosLosBibliotecarios();
                
                request.setAttribute("prestamos", prestamos);
                request.setAttribute("lectores", lectores);
                request.setAttribute("materiales", materiales);
                request.setAttribute("bibliotecarios", bibliotecarios);
                
                request.getRequestDispatcher("/app/bibliotecario/gestion-prestamos.jsp").forward(request, response);
                
            } else if ("lector".equals(tipoUsuario)) {
                // Vista de lector - solo sus préstamos
                String email = (String) session.getAttribute("email");
                Usuario usuario = PersistenciaHibernate.obtenerUsuarioPorEmail(email);
                
                if (usuario instanceof Lector) {
                    Lector lector = (Lector) usuario;
                    List<Prestamo> prestamos = PersistenciaHibernate.obtenerPrestamosPorLector(lector);
                    
                    System.out.println("📊 DEBUG: Lector " + lector.getNombre() + " tiene " + prestamos.size() + " préstamos");
                    
                    // Agrupar préstamos por estado usando Strings como claves para el JSP
                    Map<String, List<Prestamo>> prestamosPorEstado = new HashMap<>();
                    List<Prestamo> pendientes = prestamos.stream().filter(p -> p.getEstadoP() == EstadoP.PENDIENTE).collect(Collectors.toList());
                    List<Prestamo> enCurso = prestamos.stream().filter(p -> p.getEstadoP() == EstadoP.ENCURSO).collect(Collectors.toList());
                    List<Prestamo> devueltos = prestamos.stream().filter(p -> p.getEstadoP() == EstadoP.DEVUELTO).collect(Collectors.toList());
                    
                    System.out.println("   PENDIENTE: " + pendientes.size());
                    System.out.println("   ENCURSO: " + enCurso.size());
                    System.out.println("   DEVUELTO: " + devueltos.size());
                    
                    prestamosPorEstado.put("PENDIENTE", pendientes);
                    prestamosPorEstado.put("ENCURSO", enCurso);
                    prestamosPorEstado.put("DEVUELTO", devueltos);
                    
                    request.setAttribute("prestamosPorEstado", prestamosPorEstado);
                    request.setAttribute("lector", lector);
                    
                    request.getRequestDispatcher("/app/lector/mis-prestamos.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar préstamos: " + e.getMessage());
            e.printStackTrace();
            String returnUrl = "bibliotecario".equals(tipoUsuario) 
                    ? "/app/bibliotecario/dashboard" 
                    : "/app/lector/dashboard";
            response.sendRedirect(request.getContextPath() + returnUrl + "?error=error_cargar_prestamos");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        try {
            String action = request.getParameter("action");
            
            if ("crear".equals(action) && "lector".equals(tipoUsuario)) {
                // Crear nuevo préstamo (lector)
                String emailLector = (String) session.getAttribute("email");
                int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));
                String emailBibliotecario = request.getParameter("emailBibliotecario");
                
                // Obtener objetos necesarios
                Usuario usuarioLector = PersistenciaHibernate.obtenerUsuarioPorEmail(emailLector);
                Material material = PersistenciaHibernate.obtenerMaterialPorId(idMaterial);
                Usuario usuarioBibliotecario = PersistenciaHibernate.obtenerUsuarioPorEmail(emailBibliotecario);
                
                if (!(usuarioLector instanceof Lector) || material == null || !(usuarioBibliotecario instanceof Bibliotecario)) {
                    response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=datos_invalidos");
                    return;
                }
                
                Lector lector = (Lector) usuarioLector;
                Bibliotecario bibliotecario = (Bibliotecario) usuarioBibliotecario;
                
                // Obtener fecha de devolución
                LocalDate hoy = LocalDate.now();
                DTFecha fechaDevolucion = new DTFecha(
                    hoy.plusDays(15).getDayOfMonth(), 
                    hoy.plusDays(15).getMonthValue(), 
                    hoy.plusDays(15).getYear()
                );
                
                controlador.crearPrestamo(material, lector, bibliotecario, fechaDevolucion);
                
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?success=prestamo_creado");
                
            } else if ("crear".equals(action) && "bibliotecario".equals(tipoUsuario)) {
                // Crear préstamo desde bibliotecario
                String emailLector = request.getParameter("emailLector");
                int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));
                String emailBibliotecario = (String) session.getAttribute("email");
                
                // Obtener objetos necesarios
                Usuario usuarioLector = PersistenciaHibernate.obtenerUsuarioPorEmail(emailLector);
                Material material = PersistenciaHibernate.obtenerMaterialPorId(idMaterial);
                Usuario usuarioBibliotecario = PersistenciaHibernate.obtenerUsuarioPorEmail(emailBibliotecario);
                
                if (!(usuarioLector instanceof Lector) || material == null || !(usuarioBibliotecario instanceof Bibliotecario)) {
                    response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=datos_invalidos");
                    return;
                }
                
                Lector lector = (Lector) usuarioLector;
                Bibliotecario bibliotecario = (Bibliotecario) usuarioBibliotecario;
                
                LocalDate hoy = LocalDate.now();
                DTFecha fechaDevolucion = new DTFecha(
                    hoy.plusDays(15).getDayOfMonth(), 
                    hoy.plusDays(15).getMonthValue(), 
                    hoy.plusDays(15).getYear()
                );
                
                controlador.crearPrestamo(material, lector, bibliotecario, fechaDevolucion);
                
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?success=prestamo_creado");
                
            } else {
                response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=accion_invalida");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/gestion-prestamos?error=error_crear_prestamo");
        }
    }
}

