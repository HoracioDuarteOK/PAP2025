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
- **Servlets 4.0**
- **JSP 2.3**
- **JSTL 1.2**
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

### 2. Crear las Tablas Iniciales

```bash
# Desde el directorio del proyecto
./gradlew crearTablas
```

### 3. Ejecutar la Aplicación Web

```bash
# Iniciar el servidor web
./gradlew webServer
```

La aplicación estará disponible en: `http://localhost:8080`

## Uso de la Aplicación

### Acceso al Sistema

1. Abre tu navegador y ve a `http://localhost:8080`
2. Inicia sesión con las credenciales de un usuario registrado:
   - **Bibliotecario**: Email y contraseña del bibliotecario
   - **Lector**: Email y contraseña del lector

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

## Estructura del Proyecto

```
src/main/
├── java/pap2025/
│   ├── web/
│   │   ├── WebServer.java          # Servidor web embebido
│   │   ├── servlets/               # Servlets de la aplicación
│   │   └── filtros/                # Filtros de autenticación
│   ├── logica/                     # Lógica de negocio existente
│   ├── persistencia/               # Capa de persistencia existente
│   └── interfaz/                   # Interfaces existentes
└── webapp/
    ├── WEB-INF/
    │   └── web.xml                 # Configuración de la aplicación web
    ├── app/
    │   ├── bibliotecario/          # Páginas para bibliotecarios
    │   └── lector/                 # Páginas para lectores
    ├── error/                      # Páginas de error
    └── index.jsp                   # Página de login
```

## Configuración Avanzada

### Cambiar Puerto del Servidor

```bash
./gradlew webServer --args="9090"
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

1. **Crear Servlet**: En `src/main/java/pap2025/web/servlets/`
2. **Crear JSP**: En `src/main/webapp/app/` según el tipo de usuario
3. **Actualizar web.xml**: Agregar mapeo del servlet
4. **Actualizar navegación**: Modificar sidebar en las páginas correspondientes

### Estructura de Servlets

```java
@WebServlet(name = "MiServlet", urlPatterns = {"/app/mi-funcionalidad"})
public class MiServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Lógica para GET
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Lógica para POST
    }
}
```

## Licencia

Este proyecto es parte del trabajo académico PAP2025 - Universidad de la República.

## Contacto

Para soporte técnico o consultas sobre el sistema, contacta al equipo de desarrollo.
