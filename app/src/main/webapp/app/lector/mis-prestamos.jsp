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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/lector/dashboard">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-prestamos">
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
                        <h2><i class="fas fa-book-open"></i> Mis Préstamos</h2>
                        <p class="text-muted mb-0">Consulta el estado de todos tus préstamos</p>
                    </div>

                    <!-- Préstamos Pendientes -->
                    <div class="card mb-4">
                        <div class="card-header bg-warning text-white">
                            <h5 class="mb-0"><i class="fas fa-clock"></i> Préstamos Pendientes</h5>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty prestamosPorEstado['PENDIENTE']}">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Material</th>
                                                    <th>Fecha Solicitada</th>
                                                    <th>Fecha Devolución</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="prestamo" items="${prestamosPorEstado['PENDIENTE']}">
                                                    <tr>
                                                        <td>
                                                            <i class="fas fa-book text-primary"></i> 
                                                            Material ID: ${prestamo.material.id}
                                                        </td>
                                                        <td>${prestamo.fechaSolicitada}</td>
                                                        <td>${prestamo.fechaDevolucion}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center text-muted py-3">
                                        <i class="fas fa-inbox fa-2x mb-2"></i>
                                        <p>No tienes préstamos pendientes</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Préstamos En Curso -->
                    <div class="card mb-4">
                        <div class="card-header bg-info text-white">
                            <h5 class="mb-0"><i class="fas fa-play"></i> Préstamos En Curso</h5>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty prestamosPorEstado['ENCURSO']}">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Material</th>
                                                    <th>Fecha Solicitada</th>
                                                    <th>Fecha Devolución</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="prestamo" items="${prestamosPorEstado['ENCURSO']}">
                                                    <tr>
                                                        <td>
                                                            <i class="fas fa-book text-primary"></i> 
                                                            Material ID: ${prestamo.material.id}
                                                        </td>
                                                        <td>${prestamo.fechaSolicitada}</td>
                                                        <td>${prestamo.fechaDevolucion}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center text-muted py-3">
                                        <i class="fas fa-inbox fa-2x mb-2"></i>
                                        <p>No tienes préstamos en curso</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Préstamos Devueltos -->
                    <div class="card">
                        <div class="card-header bg-success text-white">
                            <h5 class="mb-0"><i class="fas fa-check"></i> Préstamos Devueltos</h5>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty prestamosPorEstado['DEVUELTO']}">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Material</th>
                                                    <th>Fecha Solicitada</th>
                                                    <th>Fecha Devolución</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="prestamo" items="${prestamosPorEstado['DEVUELTO']}">
                                                    <tr>
                                                        <td>
                                                            <i class="fas fa-book text-primary"></i> 
                                                            Material ID: ${prestamo.material.id}
                                                        </td>
                                                        <td>${prestamo.fechaSolicitada}</td>
                                                        <td>${prestamo.fechaDevolucion}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center text-muted py-3">
                                        <i class="fas fa-inbox fa-2x mb-2"></i>
                                        <p>No tienes préstamos devueltos en tu historial</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
