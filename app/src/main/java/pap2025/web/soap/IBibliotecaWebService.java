package pap2025.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Interfaz del Servicio SOAP (SEI - Service Endpoint Interface)
 * Esta interfaz define el contrato del servicio web
 */
@WebService(
    name = "BibliotecaWebService",
    targetNamespace = "http://soap.web.pap2025/"
)
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IBibliotecaWebService {
    
    @WebMethod(operationName = "obtenerTotalMateriales")
    int obtenerTotalMateriales();
    
    @WebMethod(operationName = "obtenerTotalLibros")
    int obtenerTotalLibros();
    
    @WebMethod(operationName = "obtenerTotalPrestamos")
    int obtenerTotalPrestamos();
    
    @WebMethod(operationName = "obtenerTotalLectores")
    int obtenerTotalLectores();
    
    @WebMethod(operationName = "obtenerTitulosLibros")
    String[] obtenerTitulosLibros();
    
    @WebMethod(operationName = "existeUsuario")
    boolean existeUsuario(@WebParam(name = "email") String email);
    
    @WebMethod(operationName = "obtenerNombreUsuario")
    String obtenerNombreUsuario(@WebParam(name = "email") String email);
    
    @WebMethod(operationName = "obtenerEstadisticasTexto")
    String obtenerEstadisticasTexto();
}
