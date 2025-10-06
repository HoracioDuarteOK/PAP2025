package pap2025.logica;

import pap2025.datatypes.DTFecha;
import javax.persistence.*;

/**
 * Clase Libro que hereda de Material
 */
@Entity
@Table(name = "libros")
@PrimaryKeyJoinColumn(name = "material_id")
public class Libro extends Material {
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    
    @Column(name = "cant_paginas", nullable = false)
    private int cantPaginas;
    
    // Constructor sin argumentos requerido por JPA
    public Libro() {
        super();
    }
    
    public Libro(int id, DTFecha fechaIngreso, String titulo, int cantPaginas) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.cantPaginas = cantPaginas;
    }
    
    // Getters
    public String getTitulo() {
        return titulo;
    }
    
    public int getCantPaginas() {
        return cantPaginas;
    }
    
    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setCantPaginas(int cantPaginas) {
        this.cantPaginas = cantPaginas;
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + getId() +
                ", fechaIngreso=" + getFechaIngreso() +
                ", titulo='" + titulo + '\'' +
                ", cantPaginas=" + cantPaginas +
                '}';
    }
}
