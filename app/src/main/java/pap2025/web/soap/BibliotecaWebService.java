package pap2025.web.soap;

import pap2025.logica.*;
import pap2025.persistencia.PersistenciaHibernate;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Implementación del Servicio SOAP de la Biblioteca Comunitaria
 * Genera WSDL automáticamente para consumo desde clientes externos
 */
@WebService(
    endpointInterface = "pap2025.web.soap.IBibliotecaWebService",
    serviceName = "BibliotecaService",
    targetNamespace = "http://soap.web.pap2025/",
    portName = "BibliotecaPort"
)
public class BibliotecaWebService implements IBibliotecaWebService {
    
    @Override
    public int obtenerTotalMateriales() {
        try {
            return PersistenciaHibernate.obtenerTodosLosMateriales().size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    @Override
    public int obtenerTotalLibros() {
        try {
            return PersistenciaHibernate.obtenerTodosLosLibros().size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    @Override
    public int obtenerTotalPrestamos() {
        try {
            return PersistenciaHibernate.obtenerTodosLosPrestamos().size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    @Override
    public int obtenerTotalLectores() {
        try {
            return PersistenciaHibernate.obtenerTodosLosLectores().size();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    @Override
    public String[] obtenerTitulosLibros() {
        try {
            List<Libro> libros = PersistenciaHibernate.obtenerTodosLosLibros();
            return libros.stream()
                    .map(Libro::getTitulo)
                    .toArray(String[]::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error al obtener libros"};
        }
    }
    
    @Override
    public boolean existeUsuario(String email) {
        try {
            Usuario usuario = PersistenciaHibernate.obtenerUsuarioPorEmail(email);
            return usuario != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String obtenerNombreUsuario(String email) {
        try {
            Usuario usuario = PersistenciaHibernate.obtenerUsuarioPorEmail(email);
            return usuario != null ? usuario.getNombre() : "Usuario no encontrado";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
    @Override
    public String obtenerEstadisticasTexto() {
        try {
            int materiales = PersistenciaHibernate.obtenerTodosLosMateriales().size();
            int libros = PersistenciaHibernate.obtenerTodosLosLibros().size();
            int prestamos = PersistenciaHibernate.obtenerTodosLosPrestamos().size();
            int lectores = PersistenciaHibernate.obtenerTodosLosLectores().size();
            
            return String.format("Biblioteca Comunitaria - Estadísticas: " +
                    "Materiales=%d, Libros=%d, Préstamos=%d, Lectores=%d",
                    materiales, libros, prestamos, lectores);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener estadísticas";
        }
    }
}

