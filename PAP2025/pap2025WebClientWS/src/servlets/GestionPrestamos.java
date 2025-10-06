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
import publicadores.DtPrestamo;
import publicadores.DtMaterial;
import publicadores.DtFecha;

@WebServlet("/GestionPrestamos")
public class GestionPrestamos extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public GestionPrestamos() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (tipoUsuario == null) {
            request.setAttribute("error", "Debe iniciar sesión para acceder a esta funcionalidad.");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
            return;
        }
        
        try {
            if ("lector".equals(tipoUsuario)) {
                // Mostrar préstamos del lector
                String emailLector = (String) session.getAttribute("emailUsuario");
                DtPrestamo[] prestamos = obtenerPrestamosPorLector(emailLector);
                ArrayList<DtPrestamo> listaPrestamos = new ArrayList<>();
                for (DtPrestamo prestamo : prestamos) {
                    listaPrestamos.add(prestamo);
                }
                request.setAttribute("prestamos", listaPrestamos);
                request.setAttribute("vista", "lector");
            } else if ("bibliotecario".equals(tipoUsuario)) {
                // Mostrar todos los préstamos para bibliotecario
                DtPrestamo[] prestamos = obtenerPrestamosPorEstado("EN_CURSO");
                ArrayList<DtPrestamo> listaPrestamos = new ArrayList<>();
                for (DtPrestamo prestamo : prestamos) {
                    listaPrestamos.add(prestamo);
                }
                request.setAttribute("prestamos", listaPrestamos);
                request.setAttribute("vista", "bibliotecario");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar préstamos: " + e.getMessage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/gestionPrestamos.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (tipoUsuario == null) {
            request.setAttribute("error", "Debe iniciar sesión para acceder a esta funcionalidad.");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
            return;
        }
        
        String accion = request.getParameter("accion");
        
        try {
            if ("crear".equals(accion) && "bibliotecario".equals(tipoUsuario)) {
                // Crear nuevo préstamo (solo bibliotecarios)
                String emailLector = request.getParameter("emailLector");
                int idMaterial = Integer.valueOf(request.getParameter("idMaterial"));
                String emailBibliotecario = (String) session.getAttribute("emailUsuario");
                
                // Crear fecha de hoy
                DtFecha fechaHoy = new DtFecha();
                java.time.LocalDate hoy = java.time.LocalDate.now();
                fechaHoy.setDia(hoy.getDayOfMonth());
                fechaHoy.setMes(hoy.getMonthValue());
                fechaHoy.setAnio(hoy.getYear());
                
                boolean exito = crearPrestamo(emailLector, idMaterial, emailBibliotecario, fechaHoy);
                if (exito) {
                    request.setAttribute("mensaje", "Préstamo creado exitosamente");
                } else {
                    request.setAttribute("error", "Error al crear el préstamo");
                }
            } else if ("actualizar".equals(accion) && "bibliotecario".equals(tipoUsuario)) {
                // Actualizar estado de préstamo (solo bibliotecarios)
                int idPrestamo = Integer.valueOf(request.getParameter("idPrestamo"));
                String nuevoEstado = request.getParameter("nuevoEstado");
                
                actualizarEstadoPrestamo(idPrestamo, nuevoEstado);
                request.setAttribute("mensaje", "Estado del préstamo actualizado exitosamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el servidor: " + e.getMessage());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/notificacion.jsp");
        rd.forward(request, response);
    }
    
    // OPERACIONES CONSUMIDAS
    public boolean crearPrestamo(String emailLector, int idMaterial, String emailBibliotecario, DtFecha fechaPrestamo) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.crearPrestamo(emailLector, idMaterial, emailBibliotecario, fechaPrestamo);
    }
    
    public void actualizarEstadoPrestamo(int idPrestamo, String nuevoEstado) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        port.actualizarEstadoPrestamo(idPrestamo, nuevoEstado);
    }
    
    public DtPrestamo[] obtenerPrestamosPorLector(String emailLector) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.obtenerPrestamosPorLector(emailLector);
    }
    
    public DtPrestamo[] obtenerPrestamosPorEstado(String estado) throws Exception {
        ControladorPublishService cps = new ControladorPublishServiceLocator();
        ControladorPublish port = cps.getControladorPublishPort();
        return port.obtenerPrestamosPorEstado(estado);
    }
}
