package pap2025.datatypes;

import pap2025.logica.Zona;
import pap2025.logica.EstadoL;

/**
 * Data Transfer Object para Lector
 * Representa los datos de un lector para transferencia entre capas
 */
public class DTLector {
    private int id;
    private String nombre;
    private String email;
    private String direccion;
    private DTFecha fechaRegistro;
    private Zona zona;
    private EstadoL estadoL;
    
    // Constructores
    public DTLector() {
    }
    
    public DTLector(int id, String nombre, String email, String direccion, 
                   DTFecha fechaRegistro, Zona zona, EstadoL estadoL) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.zona = zona;
        this.estadoL = estadoL;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public DTFecha getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(DTFecha fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Zona getZona() {
        return zona;
    }
    
    public void setZona(Zona zona) {
        this.zona = zona;
    }
    
    public EstadoL getEstadoL() {
        return estadoL;
    }
    
    public void setEstadoL(EstadoL estadoL) {
        this.estadoL = estadoL;
    }
    
    @Override
    public String toString() {
        return "DTLector{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", zona=" + zona +
                ", estadoL=" + estadoL +
                '}';
    }
}
