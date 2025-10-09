package pap2025.web.servlets;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet que expone servicios web XML para la biblioteca
 * Proporciona una API XML para consumo externo (Web Service)
 */
public class BibliotecaSOAPServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("📡 Servicio Web XML inicializado en /ws/biblioteca");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String operation = request.getParameter("operation");
        response.setContentType("application/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            if ("obtenerMateriales".equals(operation)) {
                generarXMLMateriales(out);
            } else if ("obtenerPrestamos".equals(operation)) {
                generarXMLPrestamos(out);
            } else if ("obtenerLectores".equals(operation)) {
                generarXMLLectores(out);
            } else if ("obtenerEstadisticas".equals(operation)) {
                generarXMLEstadisticas(out);
            } else {
                generarXMLDocumentacion(out, request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<error>");
            out.println("  <mensaje>Error al procesar solicitud</mensaje>");
            out.println("  <detalle>" + e.getMessage() + "</detalle>");
            out.println("</error>");
        }
    }
    
    private void generarXMLMateriales(PrintWriter out) {
        List<Material> materiales = PersistenciaHibernate.obtenerTodosLosMateriales();
        
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<materiales>");
        
        for (Material material : materiales) {
            out.println("  <material>");
            out.println("    <id>" + material.getId() + "</id>");
            
            if (material instanceof Libro) {
                Libro libro = (Libro) material;
                out.println("    <tipo>LIBRO</tipo>");
                out.println("    <titulo>" + escapeXml(libro.getTitulo()) + "</titulo>");
                out.println("    <cantPaginas>" + libro.getCantPaginas() + "</cantPaginas>");
            } else if (material instanceof ArtEspeciales) {
                ArtEspeciales art = (ArtEspeciales) material;
                out.println("    <tipo>ARTICULO_ESPECIAL</tipo>");
                out.println("    <descripcion>" + escapeXml(art.getDescripcion()) + "</descripcion>");
                out.println("    <peso>" + art.getPeso() + "</peso>");
            }
            
            out.println("  </material>");
        }
        
        out.println("</materiales>");
    }
    
    private void generarXMLPrestamos(PrintWriter out) {
        List<Prestamo> prestamos = PersistenciaHibernate.obtenerTodosLosPrestamos();
        
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<prestamos>");
        
        for (Prestamo prestamo : prestamos) {
            out.println("  <prestamo>");
            out.println("    <id>" + prestamo.getId() + "</id>");
            out.println("    <lector>");
            out.println("      <email>" + escapeXml(prestamo.getLector().getEmail()) + "</email>");
            out.println("      <nombre>" + escapeXml(prestamo.getLector().getNombre()) + "</nombre>");
            out.println("    </lector>");
            out.println("    <estado>" + prestamo.getEstadoP().name() + "</estado>");
            out.println("    <fechaSolicitada>" + prestamo.getFechaSolicitada().toString() + "</fechaSolicitada>");
            out.println("  </prestamo>");
        }
        
        out.println("</prestamos>");
    }
    
    private void generarXMLLectores(PrintWriter out) {
        List<Lector> lectores = PersistenciaHibernate.obtenerTodosLosLectores();
        
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<lectores>");
        
        for (Lector lector : lectores) {
            out.println("  <lector>");
            out.println("    <email>" + escapeXml(lector.getEmail()) + "</email>");
            out.println("    <nombre>" + escapeXml(lector.getNombre()) + "</nombre>");
            out.println("    <estado>" + lector.getEstadoL().name() + "</estado>");
            out.println("    <zona>" + lector.getZona().name() + "</zona>");
            out.println("  </lector>");
        }
        
        out.println("</lectores>");
    }
    
    private void generarXMLEstadisticas(PrintWriter out) {
        int totalMateriales = PersistenciaHibernate.obtenerTodosLosMateriales().size();
        int totalLibros = PersistenciaHibernate.obtenerTodosLosLibros().size();
        int totalArtEspeciales = PersistenciaHibernate.obtenerTodosLosArtEspeciales().size();
        int totalPrestamos = PersistenciaHibernate.obtenerTodosLosPrestamos().size();
        int totalLectores = PersistenciaHibernate.obtenerTodosLosLectores().size();
        
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<estadisticas>");
        out.println("  <totalMateriales>" + totalMateriales + "</totalMateriales>");
        out.println("  <totalLibros>" + totalLibros + "</totalLibros>");
        out.println("  <totalArtEspeciales>" + totalArtEspeciales + "</totalArtEspeciales>");
        out.println("  <totalPrestamos>" + totalPrestamos + "</totalPrestamos>");
        out.println("  <totalLectores>" + totalLectores + "</totalLectores>");
        out.println("</estadisticas>");
    }
    
    private void generarXMLDocumentacion(PrintWriter out, HttpServletRequest request) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<servicioWeb>");
        out.println("  <nombre>Biblioteca Comunitaria - Web Service XML</nombre>");
        out.println("  <descripcion>Servicio web que expone datos en formato XML</descripcion>");
        out.println("  <operaciones>");
        out.println("    <operacion>");
        out.println("      <nombre>obtenerMateriales</nombre>");
        out.println("      <url>" + request.getRequestURL() + "?operation=obtenerMateriales</url>");
        out.println("    </operacion>");
        out.println("    <operacion>");
        out.println("      <nombre>obtenerPrestamos</nombre>");
        out.println("      <url>" + request.getRequestURL() + "?operation=obtenerPrestamos</url>");
        out.println("    </operacion>");
        out.println("    <operacion>");
        out.println("      <nombre>obtenerLectores</nombre>");
        out.println("      <url>" + request.getRequestURL() + "?operation=obtenerLectores</url>");
        out.println("    </operacion>");
        out.println("    <operacion>");
        out.println("      <nombre>obtenerEstadisticas</nombre>");
        out.println("      <url>" + request.getRequestURL() + "?operation=obtenerEstadisticas</url>");
        out.println("    </operacion>");
        out.println("  </operaciones>");
        out.println("  <documentacion>" + request.getContextPath() + "/soap-info.jsp</documentacion>");
        out.println("</servicioWeb>");
    }
    
    private String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}

