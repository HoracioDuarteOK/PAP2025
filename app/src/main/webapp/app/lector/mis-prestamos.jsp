<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
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
        @media (max-width: 768px) {
            .sidebar-col { position: fixed; left: -100%; top: 0; height: 100vh; width: 80%; max-width: 280px; z-index: 1050; transition: left 0.3s ease; }
            .sidebar-col.show { left: 0; }
            .sidebar-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 1040; display: none; }
            .sidebar-overlay.show { display: block; }
            .mobile-menu-btn { position: fixed; top: 10px; left: 10px; z-index: 1030; background: linear-gradient(135deg, #28a745 0%, #20c997 100%); color: white; border: none; border-radius: 10px; padding: 10px 15px; box-shadow: 0 2px 10px rgba(0,0,0,0.2); }
            .main-content { padding-top: 60px !important; }
            .header h2 { font-size: 1.3rem; }
        }
    </style>
</head>
<body>
    <button class="mobile-menu-btn d-md-none" onclick="toggleSidebar()"><i class="fas fa-bars"></i></button>
    <div class="sidebar-overlay" onclick="toggleSidebar()"></div>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 px-0 sidebar-col">
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
    <script>
        function toggleSidebar() {
            document.querySelector('.sidebar-col').classList.toggle('show');
            document.querySelector('.sidebar-overlay').classList.toggle('show');
        }
        document.addEventListener('DOMContentLoaded', function() {
            if (window.innerWidth <= 768) {
                document.querySelectorAll('.sidebar .nav-link').forEach(link => {
                    link.addEventListener('click', toggleSidebar);
                });
            }
        });
    </script>
</body>
</html>
