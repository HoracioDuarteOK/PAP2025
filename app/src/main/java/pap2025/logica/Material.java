package pap2025.logica;

import pap2025.datatypes.DTFecha;
import javax.persistence.*;

/**
 * Clase base Material
 */
@Entity
@Table(name = "materiales")
@Inheritance(strategy = InheritanceType.JOINED)
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dia", column = @Column(name = "fecha_ingreso_dia")),
        @AttributeOverride(name = "mes", column = @Column(name = "fecha_ingreso_mes")),
        @AttributeOverride(name = "anio", column = @Column(name = "fecha_ingreso_anio"))
    })
    protected DTFecha fechaIngreso;
    
    // Constructor sin argumentos requerido por JPA
    public Material() {
    }
    
    public Material(int id, DTFecha fechaIngreso) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public DTFecha getFechaIngreso() {
        return fechaIngreso;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setFechaIngreso(DTFecha fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}
