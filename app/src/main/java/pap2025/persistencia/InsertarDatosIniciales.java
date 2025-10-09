package pap2025.persistencia;

import pap2025.logica.*;
import pap2025.datatypes.DTFecha;
import pap2025.datatypes.DTDimensiones;
//  // Eliminado
import pap2025.interfaz.IControladorFachada;
import pap2025.persistencia.ConfiguracionBD;

import java.util.List;

public class InsertarDatosIniciales {
    public static void main(String[] args) {
        System.out.println("🔧 Insertando datos iniciales...");
        
        try {
            // Inicializar conexión a la base de datos
            if (!ConfiguracionBD.inicializarConexion()) {
                System.err.println("❌ No se pudo conectar a la base de datos");
                return;
            }
            
            // Obtener controladores
            IControladorFachada controlador = pap2025.logica.ControladorFachada.getInstancia();
            
            // Crear bibliotecarios
            System.out.println("📚 Creando bibliotecarios...");
            controlador.registrarBibliotecario("Administrador", "admin@biblioteca.com", 1001);
            controlador.registrarBibliotecario("María González", "maria@biblioteca.com", 1002);
            controlador.registrarBibliotecario("Carlos López", "carlos@biblioteca.com", 1003);
            
            // Asignar contraseñas a bibliotecarios
            Usuario admin = PersistenciaHibernate.obtenerUsuarioPorEmail("admin@biblioteca.com");
            admin.setPassword("admin123");
            PersistenciaHibernate.actualizarUsuario(admin);
            
            Usuario maria = PersistenciaHibernate.obtenerUsuarioPorEmail("maria@biblioteca.com");
            maria.setPassword("maria123");
            PersistenciaHibernate.actualizarUsuario(maria);
            
            Usuario carlos = PersistenciaHibernate.obtenerUsuarioPorEmail("carlos@biblioteca.com");
            carlos.setPassword("carlos123");
            PersistenciaHibernate.actualizarUsuario(carlos);
            
            // Crear lectores
            System.out.println("👥 Creando lectores...");
            DTFecha fechaHoy = new DTFecha(2, 10, 2025); // 2 de octubre de 2025
            controlador.registrarLector("Juan Pérez", "juan@email.com", "Av. 18 de Julio 1234", fechaHoy, Zona.BIBLOTECA_CENTRAL);
            controlador.registrarLector("Ana Rodríguez", "ana@email.com", "Bvar. Artigas 5678", fechaHoy, Zona.SUCURSAL_ESTE);
            controlador.registrarLector("Pedro Silva", "pedro@email.com", "Av. Italia 9012", fechaHoy, Zona.SUCURSAL_OESTE);
            controlador.registrarLector("Lucía Fernández", "lucia@email.com", "Av. Rivera 3456", fechaHoy, Zona.BIBLOTECA_CENTRAL);
            controlador.registrarLector("Miguel Torres", "miguel@email.com", "Bvar. España 7890", fechaHoy, Zona.SUCURSAL_ESTE);
            
            // Asignar contraseñas a lectores
            Usuario juan = PersistenciaHibernate.obtenerUsuarioPorEmail("juan@email.com");
            juan.setPassword("juan123");
            PersistenciaHibernate.actualizarUsuario(juan);
            
            Usuario ana = PersistenciaHibernate.obtenerUsuarioPorEmail("ana@email.com");
            ana.setPassword("ana123");
            PersistenciaHibernate.actualizarUsuario(ana);
            
            Usuario pedro = PersistenciaHibernate.obtenerUsuarioPorEmail("pedro@email.com");
            pedro.setPassword("pedro123");
            PersistenciaHibernate.actualizarUsuario(pedro);
            
            Usuario lucia = PersistenciaHibernate.obtenerUsuarioPorEmail("lucia@email.com");
            lucia.setPassword("lucia123");
            PersistenciaHibernate.actualizarUsuario(lucia);
            
            Usuario miguel = PersistenciaHibernate.obtenerUsuarioPorEmail("miguel@email.com");
            miguel.setPassword("miguel123");
            PersistenciaHibernate.actualizarUsuario(miguel);
            
            // Crear materiales - Libros
            System.out.println("📖 Creando libros...");
            controlador.registrarDonacionLibro("El Quijote", 863);
            controlador.registrarDonacionLibro("Cien años de soledad", 471);
            controlador.registrarDonacionLibro("1984", 328);
            controlador.registrarDonacionLibro("El Principito", 96);
            controlador.registrarDonacionLibro("Don Juan Tenorio", 156);
            controlador.registrarDonacionLibro("La Divina Comedia", 704);
            controlador.registrarDonacionLibro("Hamlet", 289);
            controlador.registrarDonacionLibro("Crimen y Castigo", 671);
            controlador.registrarDonacionLibro("Los Miserables", 1463);
            controlador.registrarDonacionLibro("El Señor de los Anillos", 1216);
            
            // Crear materiales - Artículos especiales
            System.out.println("🎨 Creando artículos especiales...");
            DTDimensiones dim1 = new DTDimensiones(30.0, 40.0, 5.0);
            controlador.registrarDonacionArtEspecial("Pintura al óleo - Paisaje", 2.5, dim1);
            
            DTDimensiones dim2 = new DTDimensiones(50.0, 70.0, 3.0);
            controlador.registrarDonacionArtEspecial("Fotografía enmarcada - Retrato", 1.8, dim2);
            
            DTDimensiones dim3 = new DTDimensiones(25.0, 35.0, 2.0);
            controlador.registrarDonacionArtEspecial("Dibujo a lápiz - Naturaleza muerta", 0.5, dim3);
            
            DTDimensiones dim4 = new DTDimensiones(60.0, 80.0, 4.0);
            controlador.registrarDonacionArtEspecial("Escultura en madera - Figura abstracta", 3.2, dim4);
            
            DTDimensiones dim5 = new DTDimensiones(40.0, 50.0, 1.5);
            controlador.registrarDonacionArtEspecial("Grabado - Vista de la ciudad", 0.8, dim5);
            
            System.out.println("✅ Datos iniciales insertados exitosamente!");
            System.out.println("📊 Resumen:");
            System.out.println("   • 3 Bibliotecarios");
            System.out.println("   • 5 Lectores");
            System.out.println("   • 10 Libros");
            System.out.println("   • 5 Artículos especiales");
            System.out.println("\n🔐 Credenciales de acceso:");
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
            System.out.println("\n🌐 Ahora puedes ejecutar el servidor web con: ./gradlew app:completeWebServer");
            
        } catch (Exception e) {
            System.err.println("❌ Error al insertar datos iniciales: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
