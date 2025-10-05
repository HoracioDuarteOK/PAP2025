<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Préstamos - Biblioteca Comunitaria</title>
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
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        .prestamo-card {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
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
        .nav-tabs .nav-link {
            border-radius: 10px 10px 0 0;
            border: none;
            margin-right: 0.2rem;
        }
        .nav-tabs .nav-link.active {
            background: #28a745;
            color: white;
        }
        .material-icon {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.2rem;
            color: white;
        }
        .empty-state {
            text-align: center;
            padding: 3rem 1rem;
            color: #6c757d;
        }
        .empty-state i {
            font-size: 4rem;
            margin-bottom: 1rem;
            opacity: 0.5;
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/lector/dashboard.jsp">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-prestamos">
                                <i class="fas fa-book-open"></i> Mis Préstamos
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-materiales">
                                <i class="fas fa-search"></i> Consultar Materiales
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/lector/perfil">
                                <i class="fas fa-user"></i> Mi Perfil
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
                                <h2 class="mb-1">Mis Préstamos</h2>
                                <p class="text-muted mb-0">Historial y estado de tus préstamos</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-primary fs-6">
                                    <i class="fas fa-book-open"></i> ${prestamosLector.size()} Préstamos
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Estadísticas -->
                    <div class="row mb-4">
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-clock fa-2x text-warning mb-2"></i>
                                    <h4>${prestamosPendientes.size()}</h4>
                                    <p class="text-muted mb-0">Pendientes</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-play fa-2x text-info mb-2"></i>
                                    <h4>${prestamosEnCurso.size()}</h4>
                                    <p class="text-muted mb-0">En Curso</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-check fa-2x text-success mb-2"></i>
                                    <h4>${prestamosDevueltos.size()}</h4>
                                    <p class="text-muted mb-0">Devueltos</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Navegación por pestañas -->
                    <ul class="nav nav-tabs" id="prestamosTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="todos-tab" data-bs-toggle="tab" 
                                    data-bs-target="#todos" type="button" role="tab">
                                <i class="fas fa-list"></i> Todos (${prestamosLector.size()})
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="pendientes-tab" data-bs-toggle="tab" 
                                    data-bs-target="#pendientes" type="button" role="tab">
                                <i class="fas fa-clock"></i> Pendientes (${prestamosPendientes.size()})
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="curso-tab" data-bs-toggle="tab" 
                                    data-bs-target="#curso" type="button" role="tab">
                                <i class="fas fa-play"></i> En Curso (${prestamosEnCurso.size()})
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="devueltos-tab" data-bs-toggle="tab" 
                                    data-bs-target="#devueltos" type="button" role="tab">
                                <i class="fas fa-check"></i> Devueltos (${prestamosDevueltos.size()})
                            </button>
                        </li>
                    </ul>

                    <div class="tab-content" id="prestamosTabsContent">
                        <!-- Tab: Todos los préstamos -->
                        <div class="tab-pane fade show active" id="todos" role="tabpanel">
                            <div class="mt-4">
                                <c:choose>
                                    <c:when test="${not empty prestamosLector}">
                                        <c:forEach var="prestamo" items="${prestamosLector}">
                                            <div class="prestamo-card">
                                                <div class="row align-items-center">
                                                    <div class="col-md-1">
                                                        <c:choose>
                                                            <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                <div class="material-icon bg-primary">
                                                                    <i class="fas fa-book"></i>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="material-icon bg-info">
                                                                    <i class="fas fa-cube"></i>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <h6 class="mb-1">
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </h6>
                                                        <small class="text-muted">
                                                            ID: #${prestamo.id} | 
                                                            Solicitado: <fmt:formatDate value="${prestamo.fechaSolicitada}" pattern="dd/MM/yyyy"/>
                                                        </small>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="text-center">
                                                            <small class="text-muted d-block">Fecha Devolución</small>
                                                            <strong><fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd/MM/yyyy"/></strong>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2 text-end">
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
                                        <div class="empty-state">
                                            <i class="fas fa-book-open"></i>
                                            <h5>No tienes préstamos registrados</h5>
                                            <p>Ve a la sección de materiales para solicitar un préstamo</p>
                                            <a href="${pageContext.request.contextPath}/app/gestion-materiales" class="btn btn-success">
                                                <i class="fas fa-search"></i> Consultar Materiales
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Tab: Préstamos Pendientes -->
                        <div class="tab-pane fade" id="pendientes" role="tabpanel">
                            <div class="mt-4">
                                <c:choose>
                                    <c:when test="${not empty prestamosPendientes}">
                                        <c:forEach var="prestamo" items="${prestamosPendientes}">
                                            <div class="prestamo-card">
                                                <div class="row align-items-center">
                                                    <div class="col-md-1">
                                                        <c:choose>
                                                            <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                <div class="material-icon bg-primary">
                                                                    <i class="fas fa-book"></i>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="material-icon bg-info">
                                                                    <i class="fas fa-cube"></i>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <h6 class="mb-1">
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </h6>
                                                        <small class="text-muted">
                                                            Solicitado: <fmt:formatDate value="${prestamo.fechaSolicitada}" pattern="dd/MM/yyyy"/>
                                                            | Devolución prevista: <fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd/MM/yyyy"/>
                                                        </small>
                                                    </div>
                                                    <div class="col-md-3 text-end">
                                                        <span class="badge-estado bg-warning text-white">
                                                            <i class="fas fa-clock"></i> Pendiente de Aprobación
                                                        </span>
                                                        <div class="mt-2">
                                                            <small class="text-muted">
                                                                Esperando aprobación del bibliotecario
                                                            </small>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state">
                                            <i class="fas fa-clock"></i>
                                            <h5>No tienes préstamos pendientes</h5>
                                            <p>No hay préstamos esperando aprobación</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Tab: Préstamos En Curso -->
                        <div class="tab-pane fade" id="curso" role="tabpanel">
                            <div class="mt-4">
                                <c:choose>
                                    <c:when test="${not empty prestamosEnCurso}">
                                        <c:forEach var="prestamo" items="${prestamosEnCurso}">
                                            <div class="prestamo-card">
                                                <div class="row align-items-center">
                                                    <div class="col-md-1">
                                                        <c:choose>
                                                            <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                <div class="material-icon bg-primary">
                                                                    <i class="fas fa-book"></i>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="material-icon bg-info">
                                                                    <i class="fas fa-cube"></i>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <h6 class="mb-1">
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </h6>
                                                        <small class="text-muted">
                                                            Fecha límite de devolución: <strong><fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd/MM/yyyy"/></strong>
                                                        </small>
                                                    </div>
                                                    <div class="col-md-3 text-end">
                                                        <span class="badge-estado bg-info text-white">
                                                            <i class="fas fa-play"></i> En Curso
                                                        </span>
                                                        <div class="mt-2">
                                                            <small class="text-muted">
                                                                Material en tu posesión
                                                            </small>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state">
                                            <i class="fas fa-play"></i>
                                            <h5>No tienes préstamos en curso</h5>
                                            <p>No tienes materiales prestados actualmente</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Tab: Préstamos Devueltos -->
                        <div class="tab-pane fade" id="devueltos" role="tabpanel">
                            <div class="mt-4">
                                <c:choose>
                                    <c:when test="${not empty prestamosDevueltos}">
                                        <c:forEach var="prestamo" items="${prestamosDevueltos}">
                                            <div class="prestamo-card">
                                                <div class="row align-items-center">
                                                    <div class="col-md-1">
                                                        <c:choose>
                                                            <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                <div class="material-icon bg-primary">
                                                                    <i class="fas fa-book"></i>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="material-icon bg-info">
                                                                    <i class="fas fa-cube"></i>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <h6 class="mb-1">
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </h6>
                                                        <small class="text-muted">
                                                            Devuelto el: <fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd/MM/yyyy"/>
                                                        </small>
                                                    </div>
                                                    <div class="col-md-3 text-end">
                                                        <span class="badge-estado bg-success text-white">
                                                            <i class="fas fa-check"></i> Devuelto
                                                        </span>
                                                        <div class="mt-2">
                                                            <small class="text-muted">
                                                                Préstamo completado
                                                            </small>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state">
                                            <i class="fas fa-check"></i>
                                            <h5>No tienes préstamos devueltos</h5>
                                            <p>Aún no has devuelto ningún material</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
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
