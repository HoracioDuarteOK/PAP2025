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
 * Servlet para identificar materiales con muchos préstamos pendientes
 * Funcionalidad Opcional
 */
@WebServlet(name = "MaterialesPendientesServlet", urlPatterns = {"/app/materiales-pendientes"})
public class MaterialesPendientesServlet extends HttpServlet {
    
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
            List<Prestamo> todosPrestamos = PersistenciaHibernate.obtenerTodosLosPrestamos();
            
            // Filtrar préstamos pendientes o en curso (no devueltos)
            List<Prestamo> prestamosActivos = todosPrestamos.stream()
                .filter(p -> p.getEstadoP() == EstadoP.PENDIENTE || p.getEstadoP() == EstadoP.ENCURSO)
                .collect(Collectors.toList());
            
            // Contar préstamos activos por material
            Map<Integer, Integer> contadorPorMaterial = new HashMap<>();
            Map<Integer, Material> materialesMap = new HashMap<>();
            
            for (Prestamo prestamo : prestamosActivos) {
                Material material = prestamo.getMaterial();
                int idMaterial = material.getId();
                
                materialesMap.put(idMaterial, material);
                contadorPorMaterial.put(idMaterial, contadorPorMaterial.getOrDefault(idMaterial, 0) + 1);
            }
            
            // Crear lista de materiales con su contador
            List<Map<String, Object>> materialesConContador = new ArrayList<>();
            
            for (Map.Entry<Integer, Integer> entry : contadorPorMaterial.entrySet()) {
                Map<String, Object> item = new HashMap<>();
                item.put("material", materialesMap.get(entry.getKey()));
                item.put("cantidadPendientes", entry.getValue());
                materialesConContador.add(item);
            }
            
            // Ordenar por cantidad de préstamos pendientes (mayor a menor)
            materialesConContador.sort((m1, m2) -> 
                Integer.compare((Integer) m2.get("cantidadPendientes"), 
                               (Integer) m1.get("cantidadPendientes")));
            
            request.setAttribute("materialesConPendientes", materialesConContador);
            request.setAttribute("totalMaterialesConPendientes", materialesConContador.size());
            request.setAttribute("totalPrestamosActivos", prestamosActivos.size());
            
            request.getRequestDispatcher("/app/bibliotecario/materiales-pendientes.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/app/bibliotecario/dashboard?error=error_cargar_reporte");
        }
    }
}

