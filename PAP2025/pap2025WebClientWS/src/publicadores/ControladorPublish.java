/**
 * ControladorPublish.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package publicadores;

public interface ControladorPublish extends java.rmi.Remote {
    // Métodos de autenticación
    public publicadores.DtLector autenticarLector(java.lang.String email, java.lang.String password) throws java.rmi.RemoteException;
    public publicadores.DtBibliotecario autenticarBibliotecario(java.lang.String email, java.lang.String password) throws java.rmi.RemoteException;
    
    // Métodos de registro de usuarios
    public boolean registrarLector(java.lang.String nombre, java.lang.String email, java.lang.String password, 
                                   java.lang.String direccion, publicadores.DtFecha fechaRegistro, 
                                   publicadores.Zona zona) throws java.rmi.RemoteException;
    public boolean registrarBibliotecario(java.lang.String nombre, java.lang.String email, 
                                          java.lang.String password, int nroEmpleado) throws java.rmi.RemoteException;
    
    // Métodos de gestión de usuarios
    public void modificarEstadoLector(java.lang.String email, java.lang.String nuevoEstado) throws java.rmi.RemoteException;
    public void cambiarZonaLector(java.lang.String email, publicadores.Zona nuevaZona) throws java.rmi.RemoteException;
    
    // Métodos de gestión de materiales
    public boolean registrarLibro(java.lang.String titulo, int cantidadPaginas) throws java.rmi.RemoteException;
    public boolean registrarArticuloEspecial(java.lang.String descripcion, double peso, 
                                            publicadores.DtDimensiones dimensiones) throws java.rmi.RemoteException;
    public publicadores.DtMaterial[] consultarDonaciones() throws java.rmi.RemoteException;
    public publicadores.DtMaterial[] consultarDonacionesPorFecha(publicadores.DtFecha fechaInicio, 
                                                                publicadores.DtFecha fechaFin) throws java.rmi.RemoteException;
    
    // Métodos de gestión de préstamos
    public boolean crearPrestamo(java.lang.String emailLector, int idMaterial, java.lang.String emailBibliotecario, 
                                publicadores.DtFecha fechaPrestamo) throws java.rmi.RemoteException;
    public void actualizarEstadoPrestamo(int idPrestamo, java.lang.String nuevoEstado) throws java.rmi.RemoteException;
    public publicadores.DtPrestamo[] obtenerPrestamosPorLector(java.lang.String emailLector) throws java.rmi.RemoteException;
    public publicadores.DtPrestamo[] obtenerPrestamosActivosPorLector(java.lang.String emailLector) throws java.rmi.RemoteException;
    public publicadores.DtPrestamo[] obtenerPrestamosPorEstado(java.lang.String estado) throws java.rmi.RemoteException;
    public boolean editarInformacionPrestamo(int idPrestamo, publicadores.DtFecha fechaPrestamo, 
                                           publicadores.DtFecha fechaVencimiento) throws java.rmi.RemoteException;
    
    // Métodos de consultas y reportes
    public publicadores.DtPrestamo[] obtenerHistorialPrestamosBibliotecario(java.lang.String emailBibliotecario) throws java.rmi.RemoteException;
    public publicadores.DtPrestamo[] obtenerReportePrestamosPorZona(publicadores.Zona zona) throws java.rmi.RemoteException;
    public publicadores.DtMaterial[] obtenerMaterialesConPrestamosPendientes() throws java.rmi.RemoteException;
    
    // Métodos de cambio de contraseña
    public boolean cambiarPassword(java.lang.String email, java.lang.String passwordActual, 
                                  java.lang.String passwordNueva) throws java.rmi.RemoteException;
}
