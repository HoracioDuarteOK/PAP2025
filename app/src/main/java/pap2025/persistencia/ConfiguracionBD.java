package pap2025.persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase para manejar la configuración de la base de datos
 */
public class ConfiguracionBD {
    
    private static SessionFactory sessionFactory;
    
    /**
     * Inicializa la conexión a la base de datos
     * @return true si se conectó exitosamente, false en caso contrario
     */
    public static boolean inicializarConexion() {
        try {
            System.out.println("🔌 Iniciando conexión a la base de datos PostgreSQL...");
            
            // Crear configuración de Hibernate
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Construir SessionFactory
            sessionFactory = configuration.buildSessionFactory();
            
            System.out.println("✅ Conexión a la base de datos establecida exitosamente");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene la SessionFactory
     * @return SessionFactory configurada
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void cerrarConexion() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("🔌 Conexión a la base de datos cerrada");
        }
    }
    
    /**
     * Verifica si la conexión está activa
     * @return true si está conectado, false en caso contrario
     */
    public static boolean estaConectado() {
        return sessionFactory != null && !sessionFactory.isClosed();
    }
}
