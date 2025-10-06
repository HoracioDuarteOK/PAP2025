package pap2025.datatypes;

import pap2025.logica.EstadoP;

/**
 * Data Transfer Object para Préstamo
 * Representa los datos de un préstamo para transferencia entre capas
 */
public class DTPrestamo {
    private int id;
    private String emailLector;
    private String nombreLector;
    private int idMaterial;
    private String tituloMaterial;
    private String emailBibliotecario;
    private String nombreBibliotecario;
    private DTFecha fechaPrestamo;
    private DTFecha fechaVencimiento;
    private EstadoP estadoP;
    
    // Constructores
    public DTPrestamo() {
    }
    
    public DTPrestamo(int id, String emailLector, String nombreLector, int idMaterial, 
                     String tituloMaterial, String emailBibliotecario, String nombreBibliotecario,
                     DTFecha fechaPrestamo, DTFecha fechaVencimiento, EstadoP estadoP) {
        this.id = id;
        this.emailLector = emailLector;
        this.nombreLector = nombreLector;
        this.idMaterial = idMaterial;
        this.tituloMaterial = tituloMaterial;
        this.emailBibliotecario = emailBibliotecario;
        this.nombreBibliotecario = nombreBibliotecario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoP = estadoP;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmailLector() {
        return emailLector;
    }
    
    public void setEmailLector(String emailLector) {
        this.emailLector = emailLector;
    }
    
    public String getNombreLector() {
        return nombreLector;
    }
    
    public void setNombreLector(String nombreLector) {
        this.nombreLector = nombreLector;
    }
    
    public int getIdMaterial() {
        return idMaterial;
    }
    
    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }
    
    public String getTituloMaterial() {
        return tituloMaterial;
    }
    
    public void setTituloMaterial(String tituloMaterial) {
        this.tituloMaterial = tituloMaterial;
    }
    
    public String getEmailBibliotecario() {
        return emailBibliotecario;
    }
    
    public void setEmailBibliotecario(String emailBibliotecario) {
        this.emailBibliotecario = emailBibliotecario;
    }
    
    public String getNombreBibliotecario() {
        return nombreBibliotecario;
    }
    
    public void setNombreBibliotecario(String nombreBibliotecario) {
        this.nombreBibliotecario = nombreBibliotecario;
    }
    
    public DTFecha getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(DTFecha fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public DTFecha getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(DTFecha fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public EstadoP getEstadoP() {
        return estadoP;
    }
    
    public void setEstadoP(EstadoP estadoP) {
        this.estadoP = estadoP;
    }
    
    // Métodos de utilidad
    public boolean estaActivo() {
        return EstadoP.EN_CURSO.equals(estadoP);
    }
    
    public boolean estaVencido() {
        return EstadoP.VENCIDO.equals(estadoP);
    }
    
    public boolean estaDevuelto() {
        return EstadoP.DEVUELTO.equals(estadoP);
    }
    
    @Override
    public String toString() {
        return "DTPrestamo{" +
                "id=" + id +
                ", emailLector='" + emailLector + '\'' +
                ", nombreLector='" + nombreLector + '\'' +
                ", idMaterial=" + idMaterial +
                ", tituloMaterial='" + tituloMaterial + '\'' +
                ", emailBibliotecario='" + emailBibliotecario + '\'' +
                ", nombreBibliotecario='" + nombreBibliotecario + '\'' +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaVencimiento=" + fechaVencimiento +
                ", estadoP=" + estadoP +
                '}';
    }
}
