# Servicio Web - Biblioteca Comunitaria PAP2025

## Descripción
Servicio web SOAP que expone la funcionalidad del sistema de biblioteca comunitaria desarrollado en PAP2025. Este servicio permite que el cliente web (`pap2025WebClientWS`) consuma las operaciones del backend.

## Estructura del Proyecto

```
PAP2025/PAP2025/app/src/main/java/pap2025/Publicadores/
├── ControladorPublish.java          # Servicio web principal con todos los métodos
├── PublicadorServicioWeb.java       # Clase principal para ejecutar el servicio
└── WebServiceConfiguracion.java     # Configuración del servicio web

PAP2025/PAP2025/app/src/main/resources/
└── webservice.properties           # Archivo de configuración

PAP2025/PAP2025/
├── ejecutarServicioWeb.sh          # Script de ejecución (Linux/Mac)
└── ejecutarServicioWeb.bat         # Script de ejecución (Windows)
```

## Funcionalidades Expuestas

### 🔐 Autenticación
- `autenticarLector(email, password)` - Login de lectores
- `autenticarBibliotecario(email, password)` - Login de bibliotecarios

### 👥 Gestión de Usuarios
- `registrarLector(...)` - Registro de nuevos lectores
- `registrarBibliotecario(...)` - Registro de nuevos bibliotecarios
- `modificarEstadoLector(email, estado)` - Cambiar estado del lector
- `cambiarZonaLector(email, zona)` - Cambiar zona del lector
- `cambiarPassword(email, actual, nueva)` - Cambio de contraseña

### 📚 Gestión de Materiales
- `registrarLibro(titulo, paginas)` - Registrar libros
- `registrarArticuloEspecial(descripcion, peso, dimensiones)` - Registrar artículos
- `consultarDonaciones()` - Listar todos los materiales
- `consultarDonacionesPorFecha(inicio, fin)` - Materiales por rango de fechas

### 📋 Gestión de Préstamos
- `crearPrestamo(lector, material, bibliotecario, fecha)` - Crear préstamo
- `actualizarEstadoPrestamo(id, estado)` - Actualizar estado
- `obtenerPrestamosPorLector(email)` - Préstamos de un lector
- `obtenerPrestamosActivosPorLector(email)` - Préstamos activos
- `obtenerPrestamosPorEstado(estado)` - Préstamos por estado
- `editarInformacionPrestamo(id, fechaPrestamo, fechaVencimiento)` - Editar préstamo

### 📊 Consultas y Reportes
- `obtenerHistorialPrestamosBibliotecario(email)` - Historial por bibliotecario
- `obtenerReportePrestamosPorZona(zona)` - Reporte por zona
- `obtenerMaterialesConPrestamosPendientes()` - Materiales con préstamos pendientes

### 🔧 Utilidades
- `existeUsuario(email)` - Verificar existencia de usuario
- `obtenerLector(email)` - Obtener datos del lector
- `obtenerBibliotecario(email)` - Obtener datos del bibliotecario
- `obtenerMaterial(id)` - Obtener datos del material
- `obtenerPrestamo(id)` - Obtener datos del préstamo

## Configuración

### Archivo de Configuración (`webservice.properties`)
```properties
# Servidor Web
WS_IP=localhost
WS_PORT=8080
WS_CONTEXT=bibliotecaWS

# Base de Datos
DB_HOST=pgsql-ti.esi.edu.uy
DB_PORT=5432
DB_NAME=postgres
DB_USER=postgres
DB_PASSWORD=pGsQl5432

# Configuraciones del Sistema
PRESTAMO_DIAS_DEFAULT=15
MAX_PRESTAMOS_POR_LECTOR=5
MAX_PAGINAS_LIBRO=2000
MAX_PESO_ARTICULO=50.0
```

## Instalación y Ejecución

### 1. Compilar el Proyecto
```bash
cd PAP2025/PAP2025
./gradlew build
```

### 2. Ejecutar el Servicio Web

#### En Linux/Mac:
```bash
./ejecutarServicioWeb.sh
```

#### En Windows:
```cmd
ejecutarServicioWeb.bat
```

#### Manualmente:
```bash
java -cp "app/build/classes/java/main:app/build/resources/main" pap2025.Publicadores.PublicadorServicioWeb
```

### 3. Verificar el Servicio
- El servicio estará disponible en: `http://localhost:8080/bibliotecaWS`
- WSDL disponible en: `http://localhost:8080/bibliotecaWS?wsdl`

## Integración con Cliente Web

El cliente web `pap2025WebClientWS` consume este servicio web a través de:

1. **Servlets** que hacen llamadas a los métodos del servicio
2. **DTOs** para transferencia de datos
3. **Configuración** de endpoints en los servlets

### Ejemplo de Uso en Servlet:
```java
ControladorPublishService cps = new ControladorPublishServiceLocator();
ControladorPublish port = cps.getControladorPublishPort();
DTLector lector = port.autenticarLector(email, password);
```

## Requisitos del Sistema

- **Java 8+**
- **Base de datos PostgreSQL** configurada
- **PAP2025 backend** completamente funcional
- **Puerto 8080** disponible

## Troubleshooting

### Problemas Comunes

1. **Error de conexión a BD**:
   - Verificar configuración en `ConfiguracionBD`
   - Comprobar credenciales en `webservice.properties`

2. **Puerto ocupado**:
   - Cambiar `WS_PORT` en `webservice.properties`
   - Verificar que no haya otro servicio usando el puerto

3. **Error de compilación**:
   - Verificar que todas las dependencias estén instaladas
   - Ejecutar `./gradlew clean build`

### Logs
- Los logs del servicio se muestran en la consola
- Para debugging, verificar la configuración de logging

## Desarrollo

### Agregar Nuevos Métodos
1. Agregar método en `IControladorFachada`
2. Implementar en `ControladorFachada`
3. Exponer en `ControladorPublish` con `@WebMethod`
4. Actualizar cliente web si es necesario

### Modificar Configuración
1. Editar `webservice.properties`
2. Reiniciar el servicio web
3. Verificar que los cambios se apliquen

## Contacto
Proyecto desarrollado para PAP2025 - Programación Avanzada y Persistencia
