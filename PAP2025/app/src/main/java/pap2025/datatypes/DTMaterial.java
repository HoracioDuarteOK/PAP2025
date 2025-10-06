package pap2025.datatypes;

import pap2025.logica.Material;
import pap2025.logica.Libro;
import pap2025.logica.ArtEspeciales;

/**
 * Data Transfer Object para Material
 * Representa los datos de un material para transferencia entre capas
 */
public class DTMaterial {
    private int id;
    private String titulo;
    private DTFecha fechaDonacion;
    private String tipoMaterial; // "LIBRO" o "ARTICULO_ESPECIAL"
    
    // Campos específicos para Libro
    private Integer cantidadPaginas;
    
    // Campos específicos para Artículo Especial
    private String descripcion;
    private Double peso;
    private DTDimensiones dimensiones;
    
    // Constructores
    public DTMaterial() {
    }
    
    public DTMaterial(int id, String titulo, DTFecha fechaDonacion, String tipoMaterial) {
        this.id = id;
        this.titulo = titulo;
        this.fechaDonacion = fechaDonacion;
        this.tipoMaterial = tipoMaterial;
    }
    
    // Constructor para Libro
    public DTMaterial(int id, String titulo, DTFecha fechaDonacion, int cantidadPaginas) {
        this.id = id;
        this.titulo = titulo;
        this.fechaDonacion = fechaDonacion;
        this.tipoMaterial = "LIBRO";
        this.cantidadPaginas = cantidadPaginas;
    }
    
    // Constructor para Artículo Especial
    public DTMaterial(int id, String descripcion, DTFecha fechaDonacion, double peso, DTDimensiones dimensiones) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaDonacion = fechaDonacion;
        this.tipoMaterial = "ARTICULO_ESPECIAL";
        this.peso = peso;
        this.dimensiones = dimensiones;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public DTFecha getFechaDonacion() {
        return fechaDonacion;
    }
    
    public void setFechaDonacion(DTFecha fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }
    
    public String getTipoMaterial() {
        return tipoMaterial;
    }
    
    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }
    
    public Integer getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public void setCantidadPaginas(Integer cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Double getPeso() {
        return peso;
    }
    
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
    public DTDimensiones getDimensiones() {
        return dimensiones;
    }
    
    public void setDimensiones(DTDimensiones dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    // Métodos de utilidad
    public boolean esLibro() {
        return "LIBRO".equals(tipoMaterial);
    }
    
    public boolean esArticuloEspecial() {
        return "ARTICULO_ESPECIAL".equals(tipoMaterial);
    }
    
    @Override
    public String toString() {
        if (esLibro()) {
            return "DTMaterial{LIBRO{" +
                    "id=" + id +
                    ", titulo='" + titulo + '\'' +
                    ", fechaDonacion=" + fechaDonacion +
                    ", cantidadPaginas=" + cantidadPaginas +
                    '}';
        } else {
            return "DTMaterial{ARTICULO_ESPECIAL{" +
                    "id=" + id +
                    ", descripcion='" + descripcion + '\'' +
                    ", fechaDonacion=" + fechaDonacion +
                    ", peso=" + peso +
                    ", dimensiones=" + dimensiones +
                    '}';
        }
    }
}
