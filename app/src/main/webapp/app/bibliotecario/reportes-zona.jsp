<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte por Zona - Biblioteca Comunitaria</title>
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
        .zona-card {
            transition: transform 0.3s ease;
            cursor: pointer;
        }
        .zona-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/reportes-zona">
                                <i class="fas fa-map-marked-alt"></i> Reporte por Zona
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/materiales-pendientes">
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
                        <h2><i class="fas fa-map-marked-alt"></i> Reporte de Préstamos por Zona</h2>
                        <p class="text-muted mb-0">Analiza el uso del servicio en diferentes barrios</p>
                    </div>

                    <!-- Resumen General -->
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <div class="card border-primary">
                                <div class="card-body text-center">
                                    <h2 class="text-primary mb-0">${totalPrestamos}</h2>
                                    <p class="text-muted mb-0">Total de Préstamos en el Sistema</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card border-info">
                                <div class="card-body text-center">
                                    <h2 class="text-info mb-0">${estadisticasPorZona.size()}</h2>
                                    <p class="text-muted mb-0">Zonas con Actividad</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Tarjetas por Zona -->
                    <div class="row">
                        <c:forEach var="entry" items="${zonasOrdenadas}">
                            <c:set var="zona" value="${entry.key}"/>
                            <c:set var="stats" value="${estadisticasPorZona[zona]}"/>
                            
                            <div class="col-md-6 col-lg-4 mb-4">
                                <div class="card zona-card h-100">
                                    <div class="card-header bg-info text-white">
                                        <h5 class="mb-0">
                                            <i class="fas fa-map-marker-alt"></i> 
                                            ${zona.replace('_', ' ')}
                                        </h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="mb-3">
                                            <div class="d-flex justify-content-between align-items-center mb-2">
                                                <span>Total Préstamos:</span>
                                                <span class="badge bg-primary fs-6">${stats['total']}</span>
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center mb-2">
                                                <span><i class="fas fa-clock text-warning"></i> Pendientes:</span>
                                                <span class="badge bg-warning">${stats['pendientes']}</span>
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center mb-2">
                                                <span><i class="fas fa-play text-info"></i> En Curso:</span>
                                                <span class="badge bg-info">${stats['enCurso']}</span>
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center mb-2">
                                                <span><i class="fas fa-check text-success"></i> Devueltos:</span>
                                                <span class="badge bg-success">${stats['devueltos']}</span>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="text-center">
                                            <h4 class="text-danger mb-0">${stats['activos']}</h4>
                                            <small class="text-muted">Préstamos Activos</small>
                                        </div>
                                    </div>
                                    <div class="card-footer bg-light">
                                        <small class="text-muted">
                                            <i class="fas fa-percentage"></i> 
                                            ${String.format("%.1f", (stats['total'] * 100.0 / totalPrestamos))}% del total
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Tabla Detallada -->
                    <div class="card mt-4">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="fas fa-table"></i> Tabla Comparativa por Zona</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Zona</th>
                                            <th class="text-center">Total</th>
                                            <th class="text-center">Pendientes</th>
                                            <th class="text-center">En Curso</th>
                                            <th class="text-center">Devueltos</th>
                                            <th class="text-center">Activos</th>
                                            <th class="text-center">% del Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="entry" items="${zonasOrdenadas}">
                                            <c:set var="zona" value="${entry.key}"/>
                                            <c:set var="stats" value="${estadisticasPorZona[zona]}"/>
                                            <tr>
                                                <td><strong><i class="fas fa-map-marker-alt"></i> ${zona.replace('_', ' ')}</strong></td>
                                                <td class="text-center"><span class="badge bg-primary">${stats['total']}</span></td>
                                                <td class="text-center"><span class="badge bg-warning">${stats['pendientes']}</span></td>
                                                <td class="text-center"><span class="badge bg-info">${stats['enCurso']}</span></td>
                                                <td class="text-center"><span class="badge bg-success">${stats['devueltos']}</span></td>
                                                <td class="text-center"><span class="badge bg-danger">${stats['activos']}</span></td>
                                                <td class="text-center">${String.format("%.1f%%", (stats['total'] * 100.0 / totalPrestamos))}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

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

