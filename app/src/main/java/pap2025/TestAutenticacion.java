package pap2025;

import pap2025.logica.ControladorFachada;
import pap2025.interfaz.IControladorFachada;
import pap2025.logica.Usuario;
import pap2025.logica.Lector;
import pap2025.logica.Bibliotecario;
import pap2025.persistencia.ConfiguracionBD;
import pap2025.datatypes.DTFecha;

/**
 * Programa de prueba para verificar el sistema de autenticación
 */
public class TestAutenticacion {
    
    public static void main(String[] args) {
        System.out.println("🔐 Iniciando prueba del sistema de autenticación...");
        
        // Inicializar conexión a la base de datos
        if (!ConfiguracionBD.inicializarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos.");
            System.exit(1);
        }
        
        // Obtener instancia del controlador
        IControladorFachada controlador = ControladorFachada.getInstancia();
        
        // Crear un lector de prueba
        System.out.println("\n📝 Creando lector de prueba...");
        DTFecha fechaRegistro = new DTFecha(6, 10, 2025);
        boolean lectorCreado = controlador.registrarLector(
            "Usuario Prueba", 
            "prueba@test.com", 
            "test123", 
            "Calle Test 123", 
            fechaRegistro, 
            pap2025.logica.Zona.BIBLOTECA_CENTRAL
        );
        
        if (lectorCreado) {
            System.out.println("✅ Lector creado exitosamente");
        } else {
            System.out.println("❌ Error al crear lector");
        }
        
        // Crear un bibliotecario de prueba
        System.out.println("\n📝 Creando bibliotecario de prueba...");
        boolean bibliotecarioCreado = controlador.registrarBibliotecario(
            "Admin Prueba", 
            "admin@test.com", 
            "admin123", 
            9999
        );
        
        if (bibliotecarioCreado) {
            System.out.println("✅ Bibliotecario creado exitosamente");
        } else {
            System.out.println("❌ Error al crear bibliotecario");
        }
        
        // Probar autenticación de lector
        System.out.println("\n🔍 Probando autenticación de lector...");
        Usuario usuarioLector = controlador.autenticarUsuario("prueba@test.com", "test123");
        
        if (usuarioLector != null) {
            System.out.println("✅ Lector autenticado correctamente: " + usuarioLector.getNombre());
            System.out.println("   ¿Es lector? " + controlador.esLector(usuarioLector));
            System.out.println("   ¿Es bibliotecario? " + controlador.esBibliotecario(usuarioLector));
        } else {
            System.out.println("❌ Error en autenticación de lector");
        }
        
        // Probar autenticación de bibliotecario
        System.out.println("\n🔍 Probando autenticación de bibliotecario...");
        Usuario usuarioBibliotecario = controlador.autenticarUsuario("admin@test.com", "admin123");
        
        if (usuarioBibliotecario != null) {
            System.out.println("✅ Bibliotecario autenticado correctamente: " + usuarioBibliotecario.getNombre());
            System.out.println("   ¿Es lector? " + controlador.esLector(usuarioBibliotecario));
            System.out.println("   ¿Es bibliotecario? " + controlador.esBibliotecario(usuarioBibliotecario));
        } else {
            System.out.println("❌ Error en autenticación de bibliotecario");
        }
        
        // Probar credenciales incorrectas
        System.out.println("\n🔍 Probando credenciales incorrectas...");
        Usuario usuarioIncorrecto = controlador.autenticarUsuario("prueba@test.com", "password_incorrecta");
        
        if (usuarioIncorrecto == null) {
            System.out.println("✅ Correctamente rechazó credenciales incorrectas");
        } else {
            System.out.println("❌ Error: Debería haber rechazado credenciales incorrectas");
        }
        
        // Probar email inexistente
        System.out.println("\n🔍 Probando email inexistente...");
        Usuario usuarioInexistente = controlador.autenticarUsuario("inexistente@test.com", "cualquier_password");
        
        if (usuarioInexistente == null) {
            System.out.println("✅ Correctamente rechazó email inexistente");
        } else {
            System.out.println("❌ Error: Debería haber rechazado email inexistente");
        }
        
        System.out.println("\n🎉 Prueba de autenticación completada!");
        
        // Cerrar conexión
        ConfiguracionBD.cerrarConexion();
    }
}
