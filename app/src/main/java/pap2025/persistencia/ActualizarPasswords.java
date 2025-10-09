package pap2025.persistencia;

import pap2025.logica.Usuario;
import java.util.List;

/**
 * Script para actualizar las contraseñas de usuarios existentes
 */
public class ActualizarPasswords {
    public static void main(String[] args) {
        System.out.println("🔐 Actualizando contraseñas de usuarios...");
        
        try {
            // Inicializar conexión a la base de datos
            if (!ConfiguracionBD.inicializarConexion()) {
                System.err.println("❌ No se pudo conectar a la base de datos");
                return;
            }
            
            System.out.println("✅ Conexión establecida");
            System.out.println();
            
            // Obtener todos los usuarios
            List<Usuario> usuarios = PersistenciaHibernate.obtenerTodosLosUsuarios();
            
            System.out.println("📊 Usuarios encontrados: " + usuarios.size());
            System.out.println();
            
            // Actualizar contraseñas
            for (Usuario usuario : usuarios) {
                String email = usuario.getEmail();
                String password = null;
                
                // Asignar contraseña según email
                if (email.equals("admin@biblioteca.com")) {
                    password = "admin123";
                } else if (email.equals("maria@biblioteca.com")) {
                    password = "maria123";
                } else if (email.equals("carlos@biblioteca.com")) {
                    password = "carlos123";
                } else if (email.equals("juan@email.com")) {
                    password = "juan123";
                } else if (email.equals("ana@email.com")) {
                    password = "ana123";
                } else if (email.equals("pedro@email.com")) {
                    password = "pedro123";
                } else if (email.equals("lucia@email.com")) {
                    password = "lucia123";
                } else if (email.equals("miguel@email.com")) {
                    password = "miguel123";
                } else {
                    password = "1234"; // Password por defecto para usuarios no listados
                }
                
                usuario.setPassword(password);
                PersistenciaHibernate.actualizarUsuario(usuario);
                
                System.out.println("✅ " + email + " → password: " + password);
            }
            
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║  ✅ Contraseñas actualizadas exitosamente              ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("🔐 Credenciales de acceso:");
            System.out.println("   Bibliotecarios:");
            System.out.println("     • admin@biblioteca.com / admin123");
            System.out.println("     • maria@biblioteca.com / maria123");
            System.out.println("     • carlos@biblioteca.com / carlos123");
            System.out.println("   Lectores:");
            System.out.println("     • juan@email.com / juan123");
            System.out.println("     • ana@email.com / ana123");
            System.out.println("     • pedro@email.com / pedro123");
            System.out.println("     • lucia@email.com / lucia123");
            System.out.println("     • miguel@email.com / miguel123");
            
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar contraseñas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

