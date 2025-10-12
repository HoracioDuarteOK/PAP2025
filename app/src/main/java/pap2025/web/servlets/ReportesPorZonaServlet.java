package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servlet para generar reportes de préstamos por zona
 * Funcionalidad Opcional
 */
@WebServlet(name = "ReportesPorZonaServlet", urlPatterns = {"/app/reportes-zona"})
public class ReportesPorZonaServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"bibliotecario".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }
        
        try {
            // Obtener todos los préstamos
            List<Prestamo> prestamos = PersistenciaHibernate.obtenerTodosLosPrestamos();
            
            // Agrupar préstamos por zona del lector
            Map<String, List<Prestamo>> prestamosPorZona = new HashMap<>();
            
            for (Prestamo prestamo : prestamos) {
                Zona zona = prestamo.getLector().getZona();
                String zonaStr = zona.name();
                
                if (!prestamosPorZona.containsKey(zonaStr)) {
                    prestamosPorZona.put(zonaStr, new ArrayList<>());
                }
                prestamosPorZona.get(zonaStr).add(prestamo);
            }
            
            // Calcular estadísticas por zona
            Map<String, Map<String, Integer>> estadisticasPorZona = new HashMap<>();
            
            for (Map.Entry<String, List<Prestamo>> entry : prestamosPorZona.entrySet()) {
                String zona = entry.getKey();
                List<Prestamo> prestamoZona = entry.getValue();
                
                Map<String, Integer> stats = new HashMap<>();
                stats.put("total", prestamoZona.size());
                stats.put("pendientes", (int) prestamoZona.stream().filter(p -> p.getEstadoP() == EstadoP.PENDIENTE).count());
                stats.put("enCurso", (int) prestamoZona.stream().filter(p -> p.getEstadoP() == EstadoP.ENCURSO).count());
                stats.put("devueltos", (int) prestamoZona.stream().filter(p -> p.getEstadoP() == EstadoP.DEVUELTO).count());
                stats.put("activos", stats.get("pendientes") + stats.get("enCurso"));
                
                estadisticasPorZona.put(zona, stats);
            }
            
            // Ordenar zonas por cantidad de préstamos (mayor a menor)
            List<Map.Entry<String, List<Prestamo>>> zonasOrdenadas = prestamosPorZona.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toList());
            
            request.setAttribute("prestamosPorZona", prestamosPorZona);
            request.setAttribute("estadisticasPorZona", estadisticasPorZona);
            request.setAttribute("zonasOrdenadas", zonasOrdenadas);
            request.setAttribute("totalPrestamos", prestamos.size());
            
            request.getRequestDispatcher("/app/bibliotecario/reportes-zona.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard?error=error_generar_reporte");
        }
    }
}

