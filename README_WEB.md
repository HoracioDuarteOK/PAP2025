# Sistema de Biblioteca Comunitaria - Aplicación Web

## Descripción

Esta es la aplicación web del Sistema de Biblioteca Comunitaria desarrollada con Java (Servlets y JSP) que complementa la interfaz de escritorio. La aplicación permite a lectores y bibliotecarios interactuar de manera eficiente con el sistema a través de una interfaz web responsive.

## Características Principales

### ✅ Funcionalidades Implementadas

#### 1. Gestión de Usuarios
- **Login de usuarios**: Autenticación para bibliotecarios y lectores
- **Suspender lectores**: Los bibliotecarios pueden suspender lectores que incumplan las normas
- **Cambiar zona de lectores**: Actualizar la ubicación de los lectores en el sistema

#### 2. Gestión de Materiales
- **Registrar donaciones de libros**: Incorporar libros al inventario con título y cantidad de páginas
- **Registrar donaciones de artículos especiales**: Incorporar artículos con descripción, peso y dimensiones
- **Consultar donaciones**: Visualizar todas las donaciones registradas
- **Consulta por rango de fechas**: Filtrar donaciones por período específico (funcionalidad opcional)

#### 3. Gestión de Préstamos
- **Crear préstamos**: Asociar material, lector y bibliotecario
- **Actualizar estado de préstamos**: Cambiar a "EN CURSO" o "DEVUELTO"
- **Consultar préstamos por lector**: Los lectores pueden ver sus préstamos agrupados por estado
- **Actualizar información de préstamos**: Modificar cualquier dato del préstamo (funcionalidad opcional)
- **Listar préstamos activos por lector**: Verificar historial y control de devoluciones (funcionalidad opcional)

#### 4. Control y Seguimiento
- **Historial de préstamos por bibliotecario**: Ver préstamos gestionados (funcionalidad opcional)
- **Reporte de préstamos por zona**: Analizar uso del servicio por barrios (funcionalidad opcional)
- **Materiales con préstamos pendientes**: Identificar materiales prioritarios (funcionalidad opcional)

### 🎨 Diseño Responsive
- Interfaz adaptativa para dispositivos móviles
- Diseño moderno con Bootstrap 5
- Iconos de Font Awesome para mejor experiencia visual
- Colores diferenciados para bibliotecarios (azul/púrpura) y lectores (verde)

## Tecnologías Utilizadas

- **Java 8+**
- **Servlets 4.0** (11 servlets implementados)
- **JSP 2.3** (10 JSPs con JSTL)
- **JSTL 1.2**
- **Web Services XML** (Servicios web con respuestas XML)
- **Bootstrap 5.1.3**
- **Font Awesome 6.0**
- **Tomcat Embebido 9.0.65**
- **Hibernate 5.6.15**
- **PostgreSQL**
- **Gradle**

## Requisitos Previos

1. **Java 8 o superior**
2. **PostgreSQL** instalado y configurado
3. **Base de datos** `pap2025` creada con usuario `pap2025_user` y contraseña `1234`

## Instalación y Configuración

### 1. Configurar la Base de Datos

```bash
# Crear la base de datos
createdb pap2025

# Crear el usuario
createuser -s pap2025_user

# Establecer contraseña
psql -c "ALTER USER pap2025_user PASSWORD '1234';"
```

### 2. Insertar Datos de Prueba

```bash
# Desde el directorio del proyecto
./gradlew app:insertarDatos
```

Esto creará:
- 3 Bibliotecarios
- 5 Lectores  
- 10 Libros
- 5 Artículos especiales

### 2b. Agregar Contraseñas (IMPORTANTE)

**Si ya insertaste los datos antes, necesitas agregar las contraseñas:**

1. Abre **pgAdmin**
2. Conéctate a la base de datos `pap2025`
3. Abre **Query Tool** (botón derecho en pap2025 → Query Tool)
4. Abre el archivo `agregar_passwords.sql` del proyecto
5. Copia y pega el contenido en Query Tool
6. Ejecuta (F5 o botón ▶)
7. ✅ Verás la lista de usuarios con sus contraseñas actualizadas

### 3. Ejecutar la Aplicación Web

```bash
# Iniciar el servidor web + servicio SOAP
./gradlew app:completeWebServer
```

**Esto inicia DOS servicios:**
- 🌐 Aplicación Web: `http://localhost:8080`
- 📡 Servicio SOAP: `http://localhost:9090/soap/biblioteca`
- 📄 WSDL: `http://localhost:9090/soap/biblioteca?wsdl`

**Nota:** El comando correcto es `app:completeWebServer` debido a la estructura multi-proyecto de Gradle.

### 4. Ejecutar Cliente SOAP (Demostración)

**En otra terminal** (con el servidor corriendo):

```bash
./gradlew app:clienteSOAP
```

Este cliente se conecta al servicio SOAP y demuestra que funciona correctamente llamando a todas las operaciones.

## Uso de la Aplicación

### Acceso al Sistema

1. Abre tu navegador y ve a `http://localhost:8080`
2. Inicia sesión con las credenciales de un usuario registrado:

**Bibliotecarios:**
- Email: `admin@biblioteca.com` / Contraseña: `admin123`
- Email: `maria@biblioteca.com` / Contraseña: `maria123`
- Email: `carlos@biblioteca.com` / Contraseña: `carlos123`

**Lectores:**
- Email: `juan@email.com` / Contraseña: `juan123`
- Email: `ana@email.com` / Contraseña: `ana123`
- Email: `pedro@email.com` / Contraseña: `pedro123`
- Email: `lucia@email.com` / Contraseña: `lucia123`
- Email: `miguel@email.com` / Contraseña: `miguel123`

### Funcionalidades por Tipo de Usuario

#### 📚 Bibliotecarios
- **Dashboard**: Vista general del sistema con estadísticas
- **Gestión de Usuarios**: Suspender/activar lectores, cambiar zonas
- **Gestión de Materiales**: Registrar donaciones de libros y artículos especiales
- **Gestión de Préstamos**: Crear préstamos, actualizar estados, ver historial
- **Reportes**: Estadísticas por zona y materiales pendientes

#### 👤 Lectores
- **Dashboard**: Vista personal con información de cuenta
- **Mis Préstamos**: Ver préstamos agrupados por estado (pendientes, en curso, devueltos)
- **Consultar Materiales**: Explorar catálogo de libros y artículos disponibles
- **Mi Perfil**: Información personal y estado de cuenta

### Navegación

- **Sidebar**: Navegación principal con acceso a todas las funcionalidades
- **Breadcrumbs**: Indicadores de ubicación en la aplicación
- **Responsive**: Adaptación automática a dispositivos móviles

### Web Services SOAP

El sistema expone servicios web SOAP con WSDL para integración con otros sistemas:

**TAREA 1: Servidor SOAP**
- **Endpoint SOAP**: `http://localhost:9090/soap/biblioteca`
- **WSDL**: `http://localhost:9090/soap/biblioteca?wsdl`
- **8 operaciones** disponibles para consumo externo

**TAREA 2: Cliente SOAP**
- Ejecutar: `./gradlew app:clienteSOAP`
- Demuestra consumo del servicio SOAP
- Llama a todas las operaciones y muestra resultados

**Operaciones SOAP disponibles:**
1. `obtenerTotalMateriales()` - Cantidad de materiales
2. `obtenerTotalLibros()` - Cantidad de libros
3. `obtenerTotalPrestamos()` - Cantidad de préstamos
4. `obtenerTotalLectores()` - Cantidad de lectores
5. `obtenerTitulosLibros()` - Array con títulos
6. `existeUsuario(email)` - Verificar si usuario existe
7. `obtenerNombreUsuario(email)` - Obtener nombre de usuario
8. `obtenerEstadisticasTexto()` - Estadísticas en texto

**Ejemplo de uso con cliente Java:**
```bash
# Terminal 1: Servidor
./gradlew app:completeWebServer

# Terminal 2: Cliente
./gradlew app:clienteSOAP
```

**Servicio XML Adicional** (para consumo REST):
- `http://localhost:8080/ws/biblioteca?operation=obtenerMateriales`
- Documentación: `http://localhost:8080/soap-info.jsp`

## Estructura del Proyecto

```
app/src/main/
├── java/pap2025/
│   ├── datatypes/                  # Data Types
│   │   ├── DTDimensiones.java
│   │   └── DTFecha.java
│   │
│   ├── interfaz/                   # Interfaces
│   │   └── IControladorFachada.java
│   │
│   ├── logica/                     # Lógica de Negocio (13 clases)
│   │   ├── ControladorFachada.java
│   │   ├── Manejadores (Usuario, Material, Prestamo)
│   │   └── Entidades (Usuario, Lector, Bibliotecario, Material, Libro, etc.)
│   │
│   ├── persistencia/               # Capa de Persistencia (3 clases)
│   │   ├── ConfiguracionBD.java
│   │   ├── PersistenciaHibernate.java
│   │   └── InsertarDatosIniciales.java
│   │
│   └── web/                        # Capa Web ⭐
│       ├── TomcatEmbeddedServer.java     # Servidor Tomcat Embebido
│       │
│       ├── filtros/                # Filtros (1)
│       │   └── AuthFilter.java
│       │
│       └── servlets/               # Servlets (11) ⭐
│           ├── LoginServlet.java
│           ├── LogoutServlet.java
│           ├── BibliotecarioDashboardServlet.java
│           ├── LectorDashboardServlet.java
│           ├── GestionUsuariosServlet.java
│           ├── CambiarEstadoLectorServlet.java
│           ├── CambiarZonaLectorServlet.java
│           ├── GestionMaterialesServlet.java
│           ├── GestionPrestamosServlet.java
│           ├── CambiarEstadoPrestamoServlet.java
│           └── BibliotecaSOAPServlet.java
│
├── resources/
│   └── hibernate.cfg.xml           # Configuración Hibernate
│
└── webapp/                         # Aplicación Web ⭐
    ├── WEB-INF/
    │   └── web.xml                 # Configuración Servlets
    │
    ├── app/
    │   ├── bibliotecario/          # JSPs Bibliotecario (4)
    │   │   ├── dashboard.jsp
    │   │   ├── gestion-usuarios.jsp
    │   │   ├── gestion-materiales.jsp
    │   │   └── gestion-prestamos.jsp
    │   │
    │   └── lector/                 # JSPs Lector (3)
    │       ├── dashboard.jsp
    │       ├── mis-prestamos.jsp
    │       └── consultar-materiales.jsp
    │
    ├── error/                      # Páginas de Error (2)
    │   ├── 404.jsp
    │   └── 500.jsp
    │
    ├── index.jsp                   # ⭐ Página de Login
    └── soap-info.jsp               # Documentación Web Services
```

## Configuración Avanzada

### Cambiar Puerto del Servidor

```bash
./gradlew app:completeWebServer --args="9090"
```

### Configuración de Base de Datos

Edita el archivo `src/main/resources/hibernate.cfg.xml` para cambiar:
- URL de conexión
- Usuario y contraseña
- Configuraciones de Hibernate

## Solución de Problemas

### Error de Conexión a Base de Datos
- Verifica que PostgreSQL esté ejecutándose
- Confirma que la base de datos `pap2025` existe
- Verifica credenciales en `hibernate.cfg.xml`

### Error 404 en Páginas
- Asegúrate de que el servidor esté ejecutándose en el puerto correcto
- Verifica que las rutas en `web.xml` sean correctas

### Problemas de Permisos
- Verifica que el usuario tenga permisos para acceder a las páginas
- Confirma que el filtro de autenticación esté funcionando

## Desarrollo

### Agregar Nuevas Funcionalidades

1. **Crear Servlet**: En `app/src/main/java/pap2025/web/servlets/`
2. **Registrar en web.xml**: Agregar `<servlet>` y `<servlet-mapping>`
3. **Crear JSP**: En `app/src/main/webapp/app/` según el tipo de usuario
4. **Actualizar navegación**: Modificar sidebar en las páginas correspondientes

### Estructura de Servlets

```java
package pap2025.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward a JSP
        request.getRequestDispatcher("/app/mi-pagina.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Procesar datos
        String dato = request.getParameter("dato");
        
        // Guardar en base de datos
        // ...
        
        // Redirigir
        response.sendRedirect(request.getContextPath() + "/app/mi-pagina?success=true");
    }
}
```

**Luego agregar en web.xml:**
```xml
<servlet>
    <servlet-name>MiServlet</servlet-name>
    <servlet-class>pap2025.web.servlets.MiServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>MiServlet</servlet-name>
    <url-pattern>/app/mi-funcionalidad</url-pattern>
</servlet-mapping>
```

## Licencia

Este proyecto es parte del trabajo académico PAP2025 - Universidad de la República.

## Contacto

Para soporte técnico o consultas sobre el sistema, contacta al equipo de desarrollo.
