package pap2025.datatypes;

import javax.persistence.Embeddable;
import javax.persistence.Column;

/**
 * Tipo de dato para representar dimensiones
 */
@Embeddable
public class DTDimensiones {
    @Column(name = "alto")
    private double alto;
    
    @Column(name = "ancho")
    private double ancho;
    
    @Column(name = "profundidad")
    private double profundidad;
    
    // Constructor sin argumentos requerido por JPA
    public DTDimensiones() {
    }
    
    public DTDimensiones(double alto, double ancho, double profundidad) {
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
    }
    
    // Getters
    public double getAlto() {
        return alto;
    }
    
    public double getAncho() {
        return ancho;
    }
    
    public double getProfundidad() {
        return profundidad;
    }
    
    // Setters
    public void setAlto(double alto) {
        this.alto = alto;
    }
    
    public void setAncho(double ancho) {
        this.ancho = ancho;
    }
    
    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }
    
    @Override
    public String toString() {
        return alto + " x " + ancho + " x " + profundidad;
    }
}
