package pap2025.Publicadores;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de configuración para el servicio web
 * Maneja la carga de propiedades desde el archivo de configuración
 */
public class WebServiceConfiguracion {
    
    private Properties propiedades;
    
    public WebServiceConfiguracion() throws Exception {
        propiedades = new Properties();
        cargarConfiguracion();
    }
    
    /**
     * Carga la configuración desde el archivo properties
     */
    private void cargarConfiguracion() throws Exception {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("webservice.properties")) {
            if (input == null) {
                // Si no existe el archivo, usar configuración por defecto
                System.out.println("⚠️ Archivo webservice.properties no encontrado, usando configuración por defecto");
                cargarConfiguracionPorDefecto();
            } else {
                propiedades.load(input);
                System.out.println("✅ Configuración cargada desde webservice.properties");
            }
        } catch (IOException e) {
            System.err.println("❌ Error al cargar configuración: " + e.getMessage());
            cargarConfiguracionPorDefecto();
        }
    }
    
    /**
     * Carga configuración por defecto si no existe el archivo
     */
    private void cargarConfiguracionPorDefecto() {
        propiedades.setProperty("WS_IP", "localhost");
        propiedades.setProperty("WS_PORT", "8080");
        propiedades.setProperty("WS_CONTEXT", "bibliotecaWS");
        propiedades.setProperty("DB_HOST", "pgsql-ti.esi.edu.uy");
        propiedades.setProperty("DB_PORT", "5432");
        propiedades.setProperty("DB_NAME", "postgres");
        propiedades.setProperty("DB_USER", "postgres");
        propiedades.setProperty("DB_PASSWORD", "pGsQl5432");
        propiedades.setProperty("LOG_LEVEL", "INFO");
        propiedades.setProperty("SESSION_TIMEOUT", "30");
        propiedades.setProperty("MAX_CONCURRENT_SESSIONS", "100");
        propiedades.setProperty("PRESTAMO_DIAS_DEFAULT", "15");
        propiedades.setProperty("MAX_PRESTAMOS_POR_LECTOR", "5");
        propiedades.setProperty("MULTA_POR_DIA_VENCIDO", "10");
        propiedades.setProperty("MAX_PAGINAS_LIBRO", "2000");
        propiedades.setProperty("MAX_PESO_ARTICULO", "50.0");
        propiedades.setProperty("MAX_DIMENSIONES_ARTICULO", "100.0");
    }
    
    /**
     * Obtiene una propiedad de configuración
     * @param clave La clave de la propiedad
     * @return El valor de la propiedad
     */
    public String getConfigOf(String clave) {
        return propiedades.getProperty(clave, "");
    }
    
    /**
     * Obtiene una propiedad de configuración con valor por defecto
     * @param clave La clave de la propiedad
     * @param valorPorDefecto Valor por defecto si no existe la clave
     * @return El valor de la propiedad o el valor por defecto
     */
    public String getConfigOf(String clave, String valorPorDefecto) {
        return propiedades.getProperty(clave, valorPorDefecto);
    }
    
    /**
     * Obtiene una propiedad entera
     * @param clave La clave de la propiedad
     * @return El valor entero de la propiedad
     */
    public int getIntConfigOf(String clave) {
        try {
            return Integer.parseInt(propiedades.getProperty(clave, "0"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Obtiene una propiedad de tipo double
     * @param clave La clave de la propiedad
     * @return El valor double de la propiedad
     */
    public double getDoubleConfigOf(String clave) {
        try {
            return Double.parseDouble(propiedades.getProperty(clave, "0.0"));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    /**
     * Verifica si una propiedad existe
     * @param clave La clave de la propiedad
     * @return true si existe, false en caso contrario
     */
    public boolean hasConfig(String clave) {
        return propiedades.containsKey(clave);
    }
    
    /**
     * Imprime toda la configuración (sin passwords)
     */
    public void imprimirConfiguracion() {
        System.out.println("📋 Configuración del Servicio Web:");
        System.out.println("   🌐 IP: " + getConfigOf("WS_IP"));
        System.out.println("   🔌 Puerto: " + getConfigOf("WS_PORT"));
        System.out.println("   📁 Contexto: " + getConfigOf("WS_CONTEXT"));
        System.out.println("   🗄️ Host BD: " + getConfigOf("DB_HOST"));
        System.out.println("   🔌 Puerto BD: " + getConfigOf("DB_PORT"));
        System.out.println("   📊 Base de Datos: " + getConfigOf("DB_NAME"));
        System.out.println("   👤 Usuario BD: " + getConfigOf("DB_USER"));
        System.out.println("   📝 Log Level: " + getConfigOf("LOG_LEVEL"));
        System.out.println("   ⏱️ Timeout Sesión: " + getConfigOf("SESSION_TIMEOUT") + " minutos");
        System.out.println("   📚 Días Préstamo: " + getConfigOf("PRESTAMO_DIAS_DEFAULT"));
        System.out.println("   📖 Max Préstamos/Lector: " + getConfigOf("MAX_PRESTAMOS_POR_LECTOR"));
    }
}
