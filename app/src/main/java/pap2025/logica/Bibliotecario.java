package pap2025.logica;

import javax.persistence.*;

/**
 * Clase Bibliotecario que hereda de Usuario
 */
@Entity
@Table(name = "bibliotecarios")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Bibliotecario extends Usuario {
    @Column(name = "nro_empleado", nullable = false, unique = true)
    private int nroEmpleado;
    
    // Constructor sin argumentos requerido por JPA
    public Bibliotecario() {
        super();
    }
    
    public Bibliotecario(String nombre, String email, int nroEmpleado) {
        super(nombre, email);
        this.nroEmpleado = nroEmpleado;
    }
    
    // Getter
    public int getNroEmpleado() {
        return nroEmpleado;
    }
    
    // Setter
    public void setNroEmpleado(int nroEmpleado) {
        this.nroEmpleado = nroEmpleado;
    }
    
    @Override
    public String toString() {
        return "Bibliotecario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", nroEmpleado=" + nroEmpleado +
                '}';
    }
}
