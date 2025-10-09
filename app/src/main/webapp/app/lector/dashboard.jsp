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
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
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
        .prestamo-card {
            background: white;
            border-radius: 15px;
            padding: 1rem;
            margin-bottom: 1rem;
            box-shadow: 0 3px 10px rgba(0,0,0,0.05);
            transition: transform 0.2s ease;
        }
        .prestamo-card:hover {
            transform: translateY(-2px);
        }
        .badge-estado {
            padding: 0.5rem 1rem;
            border-radius: 20px;
            font-size: 0.8rem;
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
                            <i class="fas fa-user fa-2x mb-2"></i>
                            <h5>Lector</h5>
                            <small>Panel Personal</small>
                        </div>
                        
                        <nav class="nav flex-column">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/lector/dashboard.jsp">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-prestamos">
                                <i class="fas fa-book-open"></i> Mis Préstamos
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-materiales">
                                <i class="fas fa-search"></i> Consultar Materiales
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
                                <p class="text-muted mb-0">Lector - Zona: ${sessionScope.zonaLector}</p>
                            </div>
                            <div class="text-end">
                                <c:choose>
                                    <c:when test="${sessionScope.estadoLector == 'ACTIVO'}">
                                        <span class="badge bg-success fs-6">
                                            <i class="fas fa-check-circle"></i> Activo
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger fs-6">
                                            <i class="fas fa-ban"></i> Suspendido
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i>
                            <c:choose>
                                <c:when test="${param.error == 'acceso_denegado'}">
                                    No tienes permisos para acceder a esta página.
                                </c:when>
                                <c:when test="${param.error == 'lector_no_encontrado'}">
                                    Lector no encontrado en el sistema.
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
                        <div class="col-md-4 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-primary">
                                        <i class="fas fa-book-open"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${totalPrestamos}</div>
                                        <div class="stat-label">Total Préstamos</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-4 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-warning">
                                        <i class="fas fa-clock"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${prestamosActivos}</div>
                                        <div class="stat-label">Préstamos Activos</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-4 mb-3">
                            <div class="stat-card">
                                <div class="d-flex align-items-center">
                                    <div class="stat-icon bg-success">
                                        <i class="fas fa-check"></i>
                                    </div>
                                    <div class="ms-3">
                                        <div class="stat-number">${prestamosDevueltos}</div>
                                        <div class="stat-label">Préstamos Devueltos</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Información del Lector -->
                    <div class="row mb-4">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header bg-primary text-white">
                                    <h5 class="mb-0"><i class="fas fa-user"></i> Información Personal</h5>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <p><strong>Nombre:</strong> ${lector.nombre}</p>
                                            <p><strong>Email:</strong> ${lector.email}</p>
                                            <p><strong>Dirección:</strong> ${lector.direccion}</p>
                                        </div>
                                        <div class="col-md-6">
                                            <p><strong>Zona:</strong> 
                                                <span class="badge bg-info">${lector.zona}</span>
                                            </p>
                                            <p><strong>Estado:</strong> 
                                                <c:choose>
                                                    <c:when test="${lector.estadoL == 'ACTIVO'}">
                                                        <span class="badge-estado bg-success text-white">Activo</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge-estado bg-danger text-white">Suspendido</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                                            <p><strong>Fecha Registro:</strong> 
                                                                ${lector.fechaRegistro}
                                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-header bg-warning text-white">
                                    <h5 class="mb-0"><i class="fas fa-info-circle"></i> Estado de Cuenta</h5>
                                </div>
                                <div class="card-body text-center">
                                    <c:choose>
                                        <c:when test="${lector.estadoL == 'ACTIVO'}">
                                            <i class="fas fa-check-circle fa-3x text-success mb-3"></i>
                                            <h5 class="text-success">Cuenta Activa</h5>
                                            <p class="text-muted">Puedes realizar nuevos préstamos</p>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-ban fa-3x text-danger mb-3"></i>
                                            <h5 class="text-danger">Cuenta Suspendida</h5>
                                            <p class="text-muted">No puedes realizar nuevos préstamos</p>
                                            <small class="text-muted">Contacta al bibliotecario para más información</small>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Préstamos Recientes -->
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header bg-light">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="fas fa-history"></i> Mis Préstamos Recientes</h5>
                                        <a href="${pageContext.request.contextPath}/app/gestion-prestamos" class="btn btn-primary btn-sm">
                                            Ver Todos
                                        </a>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${not empty prestamosLector}">
                                            <c:forEach var="prestamo" items="${prestamosLector}" begin="0" end="4">
                                                <div class="prestamo-card">
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <div class="flex-grow-1">
                                                            <h6 class="mb-1">
                                                                <c:choose>
                                                                    <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                        <i class="fas fa-book text-primary"></i>
                                                                        ${prestamo.material.titulo}
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <i class="fas fa-cube text-info"></i>
                                                                        ${prestamo.material.descripcion}
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </h6>
                                                            <small class="text-muted">
                                                                Solicitado: ${prestamo.fechaSolicitada}
                                                                | Devolución: ${prestamo.fechaDevolucion}
                                                            </small>
                                                        </div>
                                                        <div class="text-end">
                                                            <c:choose>
                                                                <c:when test="${prestamo.estadoP == 'ENCURSO'}">
                                                                    <span class="badge-estado bg-info text-white">En Curso</span>
                                                                </c:when>
                                                                <c:when test="${prestamo.estadoP == 'DEVUELTO'}">
                                                                    <span class="badge-estado bg-success text-white">Devuelto</span>
                                                                </c:when>
                                                                <c:when test="${prestamo.estadoP == 'PENDIENTE'}">
                                                                    <span class="badge-estado bg-warning text-white">Pendiente</span>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center text-muted py-4">
                                                <i class="fas fa-book-open fa-3x mb-3"></i>
                                                <h5>No tienes préstamos registrados</h5>
                                                <p>Ve a la sección de materiales para solicitar un préstamo</p>
                                                <a href="${pageContext.request.contextPath}/app/gestion-materiales" class="btn btn-primary">
                                                    <i class="fas fa-search"></i> Consultar Materiales
                                                </a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Acciones Rápidas -->
                    <div class="row mt-4">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header bg-light">
                                    <h5 class="mb-0"><i class="fas fa-bolt"></i> Acciones Rápidas</h5>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-4 mb-3">
                                            <a href="${pageContext.request.contextPath}/app/gestion-prestamos" class="btn btn-outline-primary w-100">
                                                <i class="fas fa-book-open"></i><br>
                                                <small>Ver Mis Préstamos</small>
                                            </a>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <a href="${pageContext.request.contextPath}/app/gestion-materiales" class="btn btn-outline-success w-100">
                                                <i class="fas fa-search"></i><br>
                                                <small>Consultar Materiales</small>
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
