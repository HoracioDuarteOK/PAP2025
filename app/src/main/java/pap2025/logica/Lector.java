package pap2025.logica;

import pap2025.datatypes.DTFecha;
import javax.persistence.*;

/**
 * Clase Lector que hereda de Usuario
 */
@Entity
@Table(name = "lectores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Lector extends Usuario {
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dia", column = @Column(name = "fecha_registro_dia")),
        @AttributeOverride(name = "mes", column = @Column(name = "fecha_registro_mes")),
        @AttributeOverride(name = "anio", column = @Column(name = "fecha_registro_anio"))
    })
    private DTFecha fechaRegistro;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoL estadoL;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "zona", nullable = false)
    private Zona zona;
    
    // Constructor sin argumentos requerido por JPA
    public Lector() {
        super();
    }
    
    public Lector(String nombre, String email, String direccion, DTFecha fechaRegistro, EstadoL estadoL, Zona zona) {
        super(nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estadoL = estadoL;
        this.zona = zona;
    }
    
    // Getters
    public String getDireccion() {
        return direccion;
    }
    
    public DTFecha getFechaRegistro() {
        return fechaRegistro;
    }
    
    public EstadoL getEstadoL() {
        return estadoL;
    }
    
    public Zona getZona() {
        return zona;
    }
    
    // Setters
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setFechaRegistro(DTFecha fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public void setEstadoL(EstadoL estadoL) {
        this.estadoL = estadoL;
    }   
    public void setZona(Zona zona) { 
        this.zona = zona;
    }
    
    @Override
    public String toString() {
        return "Lector{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estadoL=" + estadoL +
                '}';
    }
}
