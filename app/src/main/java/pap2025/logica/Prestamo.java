package pap2025.logica;

import pap2025.datatypes.DTFecha;
import javax.persistence.*;

/**
 * Clase Prestamo que representa un préstamo de material
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliotecario_id", nullable = false)
    private Bibliotecario bibliotecario;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dia", column = @Column(name = "fecha_solicitada_dia")),
        @AttributeOverride(name = "mes", column = @Column(name = "fecha_solicitada_mes")),
        @AttributeOverride(name = "anio", column = @Column(name = "fecha_solicitada_anio"))
    })
    private DTFecha fechaSolicitada;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dia", column = @Column(name = "fecha_devolucion_dia")),
        @AttributeOverride(name = "mes", column = @Column(name = "fecha_devolucion_mes")),
        @AttributeOverride(name = "anio", column = @Column(name = "fecha_devolucion_anio"))
    })
    private DTFecha fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoP estadoP;
    
    // Constructor sin argumentos requerido por JPA
    public Prestamo() {
    }
    
    public Prestamo(int id, Material material, Lector lector, Bibliotecario bibliotecario, 
                   DTFecha fechaSolicitada, DTFecha fechaDevolucion, EstadoP estadoP) {
        this.id = id;
        this.material = material;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.fechaSolicitada = fechaSolicitada;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoP = estadoP;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public Lector getLector() {
        return lector;
    }
    
    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }
    
    public DTFecha getFechaSolicitada() {
        return fechaSolicitada;
    }
    
    public DTFecha getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public EstadoP getEstadoP() {
        return estadoP;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    
    public void setLector(Lector lector) {
        this.lector = lector;
    }
    
    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
    
    public void setFechaSolicitada(DTFecha fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }
    
    public void setFechaDevolucion(DTFecha fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public void setEstadoP(EstadoP estadoP) {
        this.estadoP = estadoP;
    }
    
    @Override
    public String toString() {
        try {
            return "Prestamo{" +
                    "id=" + id +
                    ", material=" + (material instanceof Libro ? ((Libro) material).getTitulo() : 
                                     material instanceof ArtEspeciales ? ((ArtEspeciales) material).getDescripcion() : "Material") +
                    ", lector=" + (lector != null ? "Lector ID: " + lector.getId() : "null") +
                    ", bibliotecario=" + (bibliotecario != null ? "Bibliotecario ID: " + bibliotecario.getId() : "null") +
                    ", fechaSolicitada=" + fechaSolicitada +
                    ", fechaDevolucion=" + fechaDevolucion +
                    ", estadoP=" + estadoP +
                    '}';
        } catch (Exception e) {
            // Si hay error de lazy loading, mostrar solo información básica
            return "Prestamo{id=" + id + ", estadoP=" + estadoP + "}";
        }
    }
}
