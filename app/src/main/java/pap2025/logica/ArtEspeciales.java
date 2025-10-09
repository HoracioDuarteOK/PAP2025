package pap2025.logica;

import pap2025.datatypes.DTDimensiones;
import pap2025.datatypes.DTFecha;
import javax.persistence.*;

/**
 * Clase ArtEspeciales que hereda de Material
 */
@Entity
@Table(name = "articulos_especiales")
@PrimaryKeyJoinColumn(name = "material_id")
public class ArtEspeciales extends Material {
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    
    @Column(name = "peso", nullable = false)
    private double peso;
    
    @Embedded
    private DTDimensiones dimensiones;
    
    // Constructor sin argumentos requerido por JPA
    public ArtEspeciales() {
        super();
    }
    
    public ArtEspeciales(int id, DTFecha fechaIngreso, String descripcion, double peso, DTDimensiones dimensiones) {
        super(id, fechaIngreso);
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensiones = dimensiones;
    }
    
    // Getters
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public DTDimensiones getDimensiones() {
        return dimensiones;
    }
    
    // Setters
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public void setDimensiones(DTDimensiones dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    @Override
    public String toString() {
        return "ArtEspeciales{" +
                "id=" + getId() +
                ", fechaIngreso=" + getFechaIngreso() +
                ", descripcion='" + descripcion + '\'' +
                ", peso=" + peso +
                ", dimensiones=" + dimensiones +
                '}';
    }
}
