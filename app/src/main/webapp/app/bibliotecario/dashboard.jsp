<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Biblioteca Comunitaria</title>
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
        .stat-card {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: transform 0.3s ease;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
            color: white;
        }
        .stat-number {
            font-size: 2rem;
            font-weight: 700;
            color: #333;
        }
        .stat-label {
            color: #6c757d;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/bibliotecario/dashboard.jsp">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-usuarios">
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
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h2 class="mb-1">Bienvenido, ${sessionScope.nombre}</h2>
                                <p class="text-muted mb-0">Bibliotecario - Nro. Empleado: ${sessionScope.nroEmpleado}</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-success fs-6">
                                    <i class="fas fa-user-shield"></i> Bibliotecario
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i>
                            <c:choose>
                                <c:when test="${param.error == 'error_cargar_usuarios'}">
                                    Error al cargar los usuarios del sistema.
                                </c:when>
                                <c:when test="${param.error == 'acceso_denegado'}">
                                    No tienes permisos para acceder a esta página.
                                </c:when>
                                <c:otherwise>
                                    Error: ${param.error}
                                </c:otherwise>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Estadísticas Principales -->
                    <div class="row mb-4">
                        <div class="col-md-3 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-primary">
                                        <i class="fas fa-users"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${totalUsuarios}</div>
                                        <div class="stat-label">Total Usuarios</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-3 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-success">
                                        <i class="fas fa-book"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${totalMateriales}</div>
                                        <div class="stat-label">Total Materiales</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-3 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-warning">
                                        <i class="fas fa-exchange-alt"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${totalPrestamos}</div>
                                        <div class="stat-label">Total Préstamos</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-3 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-info">
                                        <i class="fas fa-user-check"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${lectoresActivos}</div>
                                        <div class="stat-label">Lectores Activos</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Estadísticas Detalladas -->
                    <div class="row">
                        <!-- Préstamos por Estado -->
                        <div class="col-md-6 mb-4">
                            <div class="card">
                                <div class="card-header bg-primary text-white">
                                    <h5 class="mb-0"><i class="fas fa-chart-pie"></i> Préstamos por Estado</h5>
                                </div>
                                <div class="card-body">
                                    <div class="row text-center">
                                        <div class="col-4">
                                            <div class="text-warning">
                                                <i class="fas fa-clock fa-2x mb-2"></i>
                                                <div class="fw-bold fs-4">${prestamosPendientes}</div>
                                                <small>Pendientes</small>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <div class="text-info">
                                                <i class="fas fa-play fa-2x mb-2"></i>
                                                <div class="fw-bold fs-4">${prestamosEnCurso}</div>
                                                <small>En Curso</small>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <div class="text-success">
                                                <i class="fas fa-check fa-2x mb-2"></i>
                                                <div class="fw-bold fs-4">${prestamosDevueltos}</div>
                                                <small>Devueltos</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Materiales por Tipo -->
                        <div class="col-md-6 mb-4">
                            <div class="card">
                                <div class="card-header bg-success text-white">
                                    <h5 class="mb-0"><i class="fas fa-book-open"></i> Materiales por Tipo</h5>
                                </div>
                                <div class="card-body">
                                    <div class="row text-center">
                                        <div class="col-6">
                                            <div class="text-primary">
                                                <i class="fas fa-book fa-2x mb-2"></i>
                                                <div class="fw-bold fs-4">${totalLibros}</div>
                                                <small>Libros</small>
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="text-info">
                                                <i class="fas fa-cube fa-2x mb-2"></i>
                                                <div class="fw-bold fs-4">${totalArtEspeciales}</div>
                                                <small>Artículos Especiales</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Acciones Rápidas -->
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header bg-light">
                                    <h5 class="mb-0"><i class="fas fa-bolt"></i> Acciones Rápidas</h5>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-3 mb-3">
                                            <a href="${pageContext.request.contextPath}/app/gestion-usuarios" class="btn btn-outline-primary w-100">
                                                <i class="fas fa-users"></i><br>
                                                <small>Gestionar Usuarios</small>
                                            </a>
                                        </div>
                                        <div class="col-md-3 mb-3">
                                            <a href="${pageContext.request.contextPath}/app/gestion-materiales" class="btn btn-outline-success w-100">
                                                <i class="fas fa-plus-circle"></i><br>
                                                <small>Registrar Donación</small>
                                            </a>
                                        </div>
                                        <div class="col-md-4 mb-3">
                                            <a href="${pageContext.request.contextPath}/app/gestion-prestamos" class="btn btn-outline-warning w-100">
                                                <i class="fas fa-exchange-alt"></i><br>
                                                <small>Gestionar Préstamos</small>
                                            </a>
                                        </div>
                                    </div>
                                </div>
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
