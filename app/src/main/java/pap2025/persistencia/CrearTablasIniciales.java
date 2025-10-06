package pap2025.persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase para crear las tablas iniciales de la base de datos
 * Se ejecuta solo una vez para inicializar la base de datos
 */
public class CrearTablasIniciales {
    
    public static void main(String[] args) {
        System.out.println("🔧 Creando tablas iniciales de la base de datos...");
        
        try {
            // Configuración temporal para crear tablas
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Cambiar temporalmente a create para generar las tablas
            configuration.setProperty("hibernate.hbm2ddl.auto", "create");
            
            // Construir SessionFactory (esto creará las tablas)
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            
            System.out.println("✅ Tablas creadas exitosamente!");
            System.out.println("📋 Tablas creadas:");
            System.out.println("   • Usuario");
            System.out.println("   • Lector");
            System.out.println("   • Bibliotecario");
            System.out.println("   • Material");
            System.out.println("   • Libro");
            System.out.println("   • ArtEspeciales");
            System.out.println("   • Prestamo");
            System.out.println("   • DTDimensiones");
            
            // Cerrar la conexión
            sessionFactory.close();
            
            System.out.println("🔧 IMPORTANTE: Ahora cambia hibernate.hbm2ddl.auto a 'validate' en hibernate.cfg.xml");
            
        } catch (Exception e) {
            System.err.println("❌ Error al crear las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
