package pap2025.logica;

import javax.persistence.*;

/**
 * Clase padre Usuario
 */
@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    protected String nombre;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    protected String email;
    
    // Constructor sin argumentos requerido por JPA
    public Usuario() {
    }
    
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    // Getters
    public Integer getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
