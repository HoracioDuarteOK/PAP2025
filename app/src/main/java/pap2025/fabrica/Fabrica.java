package pap2025.fabrica;

import pap2025.interfaz.IControladorFachada;
import pap2025.logica.ControladorFachada;

public class Fabrica {
    
    private static Fabrica instancia;
    
    private Fabrica() {}
    
    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }
    
    public IControladorFachada getIControladorFachada() {
        return ControladorFachada.getInstancia();
    }
}
