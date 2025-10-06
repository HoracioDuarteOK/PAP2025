# PAP2025 Web Client - Biblioteca Comunitaria

## Descripción
Cliente web para el sistema de gestión de biblioteca comunitaria desarrollado en Java con Servlets, JSP y servicios web SOAP.

## Funcionalidades Implementadas

### ✅ Sistema de Autenticación
- Login diferenciado para Lectores y Bibliotecarios
- Gestión de sesiones HTTP
- Logout seguro
- Validación de acceso por roles

### ✅ Gestión de Usuarios
- Registro de Lectores (con zona y dirección)
- Registro de Bibliotecarios (con número de empleado)
- Dashboards diferenciados por tipo de usuario

### ✅ Gestión de Materiales
- Registro de libros (título y cantidad de páginas)
- Registro de artículos especiales (descripción, peso y dimensiones)
- Consulta del inventario de materiales
- Control de disponibilidad

### ✅ Gestión de Préstamos
- Creación de préstamos (solo bibliotecarios)
- Actualización de estados de préstamos
- Consulta de préstamos por lector
- Control de devoluciones

### ✅ Interfaz Web Responsive
- Bootstrap 4 para diseño responsive
- Navegación intuitiva
- Formularios modernos y accesibles
- Mensajes de error y éxito

## Arquitectura

```
pap2025WebClientWS/
├── src/
│   ├── publicadores/          # Data Transfer Objects (DTOs)
│   │   ├── ControladorPublish.java
│   │   ├── DtLector.java
│   │   ├── DtBibliotecario.java
│   │   ├── DtMaterial.java
│   │   ├── DtPrestamo.java
│   │   ├── DtFecha.java
│   │   ├── DtDimensiones.java
│   │   └── Zona.java
│   └── servlets/              # Lógica de presentación
│       ├── LoginServlet.java
│       ├── LogoutServlet.java
│       ├── RegistrarLector.java
│       ├── RegistrarBibliotecario.java
│       ├── GestionMateriales.java
│       └── GestionPrestamos.java
└── WebContent/
    ├── WEB-INF/
    │   ├── web.xml            # Configuración web
    │   └── lib/               # Librerías necesarias
    ├── css/                   # Bootstrap CSS
    ├── js/                    # Bootstrap JavaScript
    ├── index.jsp              # Página principal
    ├── login.jsp              # Formulario de login
    ├── dashboardLector.jsp    # Dashboard para lectores
    ├── dashboardBibliotecario.jsp # Dashboard para bibliotecarios
    ├── gestionMateriales.jsp  # Gestión de materiales
    ├── gestionPrestamos.jsp   # Gestión de préstamos
    ├── registrarLector.jsp    # Registro de lectores
    ├── registrarBibliotecario.jsp # Registro de bibliotecarios
    ├── consultas.jsp          # Página de consultas
    └── notificacion.jsp       # Página de notificaciones
```

## Requisitos del Sistema

### Software Necesario
- Java 8 o superior
- Apache Tomcat 8.5 o superior
- Servidor de base de datos PostgreSQL
- Servidor de servicios web (PAP2025 backend)

### Dependencias
- Servlet API 3.1
- JSP API 2.3
- Apache Axis 1.4 (para servicios web SOAP)
- Bootstrap 4.0.0 (incluido en el proyecto)

## Instalación y Despliegue

### 1. Configurar el Servidor Web
```bash
# Copiar el proyecto al directorio webapps de Tomcat
cp -r pap2025WebClientWS /path/to/tomcat/webapps/bibliotecaWeb
```

### 2. Configurar la Base de Datos
- Asegúrate de que el servidor PAP2025 esté ejecutándose
- Verifica la conexión a la base de datos PostgreSQL
- Ejecuta las migraciones necesarias

### 3. Configurar Servicios Web
- El proyecto está configurado para consumir servicios de PAP2025
- Verifica que los endpoints de servicios web estén disponibles
- Actualiza las URLs de servicios web si es necesario

### 4. Iniciar el Servidor
```bash
# Iniciar Tomcat
/path/to/tomcat/bin/startup.sh

# Acceder a la aplicación
http://localhost:8080/bibliotecaWeb
```

## Uso del Sistema

### Para Lectores
1. **Registro**: Acceder a "Registrar Lector" desde la página principal
2. **Login**: Iniciar sesión con email y contraseña
3. **Dashboard**: Ver información personal y opciones disponibles
4. **Préstamos**: Consultar préstamos activos y historial

### Para Bibliotecarios
1. **Registro**: Acceder a "Registrar Bibliotecario" desde la página principal
2. **Login**: Iniciar sesión con email y contraseña
3. **Dashboard**: Acceso completo a todas las funcionalidades
4. **Gestión**: Registrar materiales, gestionar préstamos, administrar usuarios

## Funcionalidades por Implementar

### Historial de Usuario (MVP)
- [x] Login de bibliotecarios y lectores
- [x] Registro de usuarios
- [x] Gestión básica de materiales
- [x] Gestión básica de préstamos

### Funcionalidades Adicionales (Opcionales)
- [ ] Consultas avanzadas con filtros por fecha
- [ ] Reportes por zona
- [ ] Historial de préstamos por bibliotecario
- [ ] Identificación de materiales con préstamos pendientes
- [ ] Modificación de información de préstamos
- [ ] Gestión de estados de usuarios (suspensión)
- [ ] Cambio de zona de lectores

## Integración con PAP2025

El cliente web consume los siguientes servicios del backend PAP2025:

### Autenticación
- `autenticarLector(email, password)`
- `autenticarBibliotecario(email, password)`

### Gestión de Usuarios
- `registrarLector(...)`
- `registrarBibliotecario(...)`
- `modificarEstadoLector(...)`
- `cambiarZonaLector(...)`

### Gestión de Materiales
- `registrarLibro(...)`
- `registrarArticuloEspecial(...)`
- `consultarDonaciones()`

### Gestión de Préstamos
- `crearPrestamo(...)`
- `actualizarEstadoPrestamo(...)`
- `obtenerPrestamosPorLector(...)`
- `obtenerPrestamosPorEstado(...)`

## Troubleshooting

### Problemas Comunes

1. **Error de conexión a servicios web**
   - Verificar que PAP2025 esté ejecutándose
   - Comprobar URLs de servicios web en el código

2. **Error de base de datos**
   - Verificar conexión a PostgreSQL
   - Comprobar credenciales y configuración

3. **Problemas de sesión**
   - Verificar configuración de Tomcat
   - Comprobar cookies del navegador

### Logs
- Logs de Tomcat: `/path/to/tomcat/logs/`
- Logs de aplicación: Verificar configuración de logging

## Desarrollo

### Estructura de Código
- **Servlets**: Manejan la lógica de presentación y comunicación con servicios web
- **JSPs**: Generan la interfaz de usuario
- **DTOs**: Transferencia de datos entre cliente y servicios web

### Patrones Utilizados
- **MVC**: Separación de lógica, vista y controlador
- **DTO**: Transferencia de datos
- **Session Management**: Gestión de sesiones HTTP

## Contacto
Proyecto desarrollado para PAP2025 - Programación Avanzada y Persistencia
