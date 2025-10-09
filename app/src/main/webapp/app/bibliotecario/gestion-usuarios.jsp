<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            border-radius: 10px;
            margin: 0.2rem 0;
            transition: all 0.3s ease;
        }
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            color: white;
            background: rgba(255,255,255,0.1);
            transform: translateX(5px);
        }
        .main-content {
            background: #f8f9fa;
            min-height: 100vh;
        }
        .header {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .badge-activo { background-color: #28a745; }
        .badge-suspendido { background-color: #dc3545; }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 px-0">
                <div class="sidebar">
                    <div class="p-3">
                        <div class="text-center text-white mb-4">
                            <i class="fas fa-book-open fa-2x mb-2"></i>
                            <h5>Biblioteca</h5>
                            <small>Panel de Control</small>
                        </div>
                        
                        <nav class="nav flex-column">
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/bibliotecario/dashboard">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-usuarios">
                                <i class="fas fa-users"></i> Gestión de Usuarios
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-materiales">
                                <i class="fas fa-book"></i> Gestión de Materiales
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-prestamos">
                                <i class="fas fa-exchange-alt"></i> Gestión de Préstamos
                            </a>
                            <hr class="text-white-50">
                            <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
                            </a>
                        </nav>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-9 col-lg-10">
                <div class="main-content p-4">
                    <!-- Header -->
                    <div class="header">
                        <h2><i class="fas fa-users"></i> Gestión de Usuarios</h2>
                        <p class="text-muted mb-0">Administra lectores y bibliotecarios del sistema</p>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.success != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle"></i>
                            <c:choose>
                                <c:when test="${param.success == 'estado_actualizado'}">
                                    Estado del lector actualizado correctamente.
                                </c:when>
                                <c:when test="${param.success == 'zona_actualizada'}">
                                    Zona del lector actualizada correctamente.
                                </c:when>
                                <c:otherwise>
                                    Operación realizada exitosamente.
                                </c:otherwise>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>
                    
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i> Error: ${param.error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Tabla de Lectores -->
                    <div class="card mb-4">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="fas fa-user"></i> Lectores</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Email</th>
                                            <th>Dirección</th>
                                            <th>Zona</th>
                                            <th>Estado</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="lector" items="${lectores}">
                                            <tr>
                                                <td>${lector.nombre}</td>
                                                <td>${lector.email}</td>
                                                <td>${lector.direccion}</td>
                                                <td><span class="badge bg-info">${lector.zona}</span></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${lector.estadoL == 'ACTIVO'}">
                                                            <span class="badge badge-activo">Activo</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-suspendido">Suspendido</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <!-- Botón Cambiar Estado -->
                                                    <form action="${pageContext.request.contextPath}/app/cambiar-estado-lector" method="post" style="display:inline;">
                                                        <input type="hidden" name="email" value="${lector.email}">
                                                        <c:choose>
                                                            <c:when test="${lector.estadoL == 'ACTIVO'}">
                                                                <input type="hidden" name="estado" value="SUSPENDIDO">
                                                                <button type="submit" class="btn btn-warning btn-sm">
                                                                    <i class="fas fa-ban"></i> Suspender
                                                                </button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="hidden" name="estado" value="ACTIVO">
                                                                <button type="submit" class="btn btn-success btn-sm">
                                                                    <i class="fas fa-check"></i> Activar
                                                                </button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </form>
                                                    
                                                    <!-- Botón Cambiar Zona -->
                                                    <button type="button" class="btn btn-info btn-sm" data-bs-toggle="modal" data-bs-target="#cambiarZonaModal${lector.email.hashCode()}">
                                                        <i class="fas fa-map-marker-alt"></i> Cambiar Zona
                                                    </button>
                                                    
                                                    <!-- Modal Cambiar Zona -->
                                                    <div class="modal fade" id="cambiarZonaModal${lector.email.hashCode()}" tabindex="-1">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title">Cambiar Zona de ${lector.nombre}</h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                                </div>
                                                                <form action="${pageContext.request.contextPath}/app/cambiar-zona-lector" method="post">
                                                                    <div class="modal-body">
                                                                        <input type="hidden" name="email" value="${lector.email}">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Nueva Zona:</label>
                                                                            <select name="zona" class="form-select" required>
                                                                                <option value="BIBLOTECA_CENTRAL">Biblioteca Central</option>
                                                                                <option value="SUCURSAL_ESTE">Sucursal Este</option>
                                                                                <option value="SUCURSAL_OESTE">Sucursal Oeste</option>
                                                                                <option value="BIBLOTECA_INFANTIL">Biblioteca Infantil</option>
                                                                                <option value="ARCHIVO_GENERAL">Archivo General</option>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                                                        <button type="submit" class="btn btn-primary">Guardar</button>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Tabla de Bibliotecarios -->
                    <div class="card">
                        <div class="card-header bg-success text-white">
                            <h5 class="mb-0"><i class="fas fa-user-shield"></i> Bibliotecarios</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Email</th>
                                            <th>Nro. Empleado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="bibliotecario" items="${bibliotecarios}">
                                            <tr>
                                                <td>${bibliotecario.nombre}</td>
                                                <td>${bibliotecario.email}</td>
                                                <td><span class="badge bg-primary">${bibliotecario.nroEmpleado}</span></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
