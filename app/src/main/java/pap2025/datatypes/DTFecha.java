package pap2025.datatypes;

import javax.persistence.Embeddable;
import javax.persistence.Column;

/**
 * Tipo de dato para representar fechas
 */
@Embeddable
public class DTFecha {
    @Column(name = "dia")
    private int dia;
    
    @Column(name = "mes")
    private int mes;
    
    @Column(name = "anio")
    private int anio;
    
    // Constructor sin argumentos requerido por JPA
    public DTFecha() {
    }
    
    public DTFecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }
    
    // Getters
    public int getDia() {
        return dia;
    }
    
    public int getMes() {
        return mes;
    }
    
    public int getAnio() {
        return anio;
    }
    
    // Setters
    public void setDia(int dia) {
        this.dia = dia;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    @Override
    public String toString() {
        return dia + "/" + mes + "/" + anio;
    }
}
