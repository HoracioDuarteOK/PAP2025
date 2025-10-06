package pap2025.datatypes;

/**
 * Data Transfer Object para Bibliotecario
 * Representa los datos de un bibliotecario para transferencia entre capas
 */
public class DTBibliotecario {
    private int id;
    private String nombre;
    private String email;
    private int nroEmpleado;
    
    // Constructores
    public DTBibliotecario() {
    }
    
    public DTBibliotecario(int id, String nombre, String email, int nroEmpleado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.nroEmpleado = nroEmpleado;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getNroEmpleado() {
        return nroEmpleado;
    }
    
    public void setNroEmpleado(int nroEmpleado) {
        this.nroEmpleado = nroEmpleado;
    }
    
    @Override
    public String toString() {
        return "DTBibliotecario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", nroEmpleado=" + nroEmpleado +
                '}';
    }
}
