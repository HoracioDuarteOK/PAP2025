<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Materiales Pendientes - Biblioteca Comunitaria</title>
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
        .prioridad-alta {
            background: #fff3cd;
            border-left: 5px solid #ffc107;
        }
        .prioridad-media {
            background: #cfe2ff;
            border-left: 5px solid #0d6efd;
        }
        .prioridad-baja {
            background: #d1e7dd;
            border-left: 5px solid #198754;
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
                            <i class="fas fa-user-shield fa-2x mb-2"></i>
                            <h5>${sessionScope.nombre}</h5>
                            <small>Bibliotecario</small>
                        </div>
                        
                        <nav class="nav flex-column">
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/bibliotecario/dashboard">
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/reportes-zona">
                                <i class="fas fa-map-marked-alt"></i> Reporte por Zona
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/materiales-pendientes">
                                <i class="fas fa-exclamation-triangle"></i> Materiales Pendientes
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
                        <h2><i class="fas fa-exclamation-triangle"></i> Materiales con Préstamos Pendientes</h2>
                        <p class="text-muted mb-0">Identifica materiales para priorizar su devolución o reposición</p>
                    </div>

                    <!-- Resumen -->
                    <div class="row mb-4">
                        <div class="col-md-4">
                            <div class="card border-danger">
                                <div class="card-body text-center">
                                    <h2 class="text-danger mb-0">${totalPrestamosActivos}</h2>
                                    <p class="text-muted mb-0">Préstamos Activos Totales</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card border-warning">
                                <div class="card-body text-center">
                                    <h2 class="text-warning mb-0">${totalMaterialesConPendientes}</h2>
                                    <p class="text-muted mb-0">Materiales con Préstamos Activos</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card border-info">
                                <div class="card-body text-center">
                                    <h2 class="text-info mb-0">
                                        <c:if test="${not empty materialesConPendientes}">
                                            ${materialesConPendientes[0]['cantidadPendientes']}
                                        </c:if>
                                        <c:if test="${empty materialesConPendientes}">0</c:if>
                                    </h2>
                                    <p class="text-muted mb-0">Máximo Préstamos por Material</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Lista de Materiales -->
                    <c:choose>
                        <c:when test="${not empty materialesConPendientes}">
                            <div class="card">
                                <div class="card-header bg-warning text-dark">
                                    <h5 class="mb-0"><i class="fas fa-list-ol"></i> Ranking de Materiales por Préstamos Activos</h5>
                                </div>
                                <div class="card-body">
                                    <c:forEach var="item" items="${materialesConPendientes}" varStatus="status">
                                        <c:set var="material" value="${item['material']}"/>
                                        <c:set var="cantidad" value="${item['cantidadPendientes']}"/>
                                        
                                        <c:choose>
                                            <c:when test="${cantidad >= 3}">
                                                <c:set var="prioridadClass" value="prioridad-alta"/>
                                                <c:set var="prioridadTexto" value="ALTA PRIORIDAD"/>
                                                <c:set var="prioridadColor" value="danger"/>
                                            </c:when>
                                            <c:when test="${cantidad == 2}">
                                                <c:set var="prioridadClass" value="prioridad-media"/>
                                                <c:set var="prioridadTexto" value="Prioridad Media"/>
                                                <c:set var="prioridadColor" value="warning"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="prioridadClass" value="prioridad-baja"/>
                                                <c:set var="prioridadTexto" value="Prioridad Baja"/>
                                                <c:set var="prioridadColor" value="success"/>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                        <div class="card mb-3 ${prioridadClass}">
                                            <div class="card-body">
                                                <div class="row align-items-center">
                                                    <div class="col-md-1 text-center">
                                                        <h2 class="text-muted mb-0">#${status.index + 1}</h2>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <h5 class="mb-1">
                                                            <c:choose>
                                                                <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                                    <i class="fas fa-book text-primary"></i> ${material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="fas fa-cube text-info"></i> ${material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </h5>
                                                        <small class="text-muted">
                                                            Material ID: ${material.id} | 
                                                            <c:choose>
                                                                <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                                    ${material.cantPaginas} páginas
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${material.peso} kg
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </small>
                                                    </div>
                                                    <div class="col-md-2 text-center">
                                                        <h3 class="text-${prioridadColor} mb-0">${cantidad}</h3>
                                                        <small class="text-muted">Préstamos Activos</small>
                                                    </div>
                                                    <div class="col-md-3 text-end">
                                                        <span class="badge bg-${prioridadColor} fs-6">
                                                            <i class="fas fa-flag"></i> ${prioridadTexto}
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-success">
                                <h4><i class="fas fa-check-circle"></i> ¡Excelente!</h4>
                                <p class="mb-0">No hay materiales con préstamos pendientes. Todos los materiales están disponibles.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <!-- Botón Volver -->
                    <div class="mt-4">
                        <a href="${pageContext.request.contextPath}/app/gestion-prestamos" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Volver a Gestión de Préstamos
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

