<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Préstamos - Biblioteca Comunitaria</title>
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-usuarios">
                                <i class="fas fa-users"></i> Gestión de Usuarios
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-materiales">
                                <i class="fas fa-book"></i> Gestión de Materiales
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-prestamos">
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
                        <h2><i class="fas fa-exchange-alt"></i> Gestión de Préstamos</h2>
                        <p class="text-muted mb-0">Administra todos los préstamos del sistema</p>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.success != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle"></i> ${param.success}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Botón Crear Préstamo -->
                    <div class="mb-4">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#crearPrestamoModal">
                            <i class="fas fa-plus-circle"></i> Crear Nuevo Préstamo
                        </button>
                    </div>

                    <!-- Tabla de Préstamos -->
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="fas fa-list"></i> Lista de Préstamos</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Lector</th>
                                            <th>Material</th>
                                            <th>Estado</th>
                                            <th>Fecha Solicitada</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="prestamo" items="${prestamos}">
                                            <tr>
                                                <td>${prestamo.id}</td>
                                                <td>${prestamo.lector.nombre}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                            ${prestamo.material.titulo}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${prestamo.material.descripcion}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${prestamo.estadoP == 'PENDIENTE'}">
                                                            <span class="badge bg-warning">Pendiente</span>
                                                        </c:when>
                                                        <c:when test="${prestamo.estadoP == 'ENCURSO'}">
                                                            <span class="badge bg-info">En Curso</span>
                                                        </c:when>
                                                        <c:when test="${prestamo.estadoP == 'DEVUELTO'}">
                                                            <span class="badge bg-success">Devuelto</span>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>${prestamo.fechaSolicitada}</td>
                                                <td>
                                                    <c:if test="${prestamo.estadoP != 'DEVUELTO'}">
                                                        <form action="${pageContext.request.contextPath}/app/cambiar-estado-prestamo" method="post" style="display:inline;">
                                                            <input type="hidden" name="idPrestamo" value="${prestamo.id}">
                                                            <c:choose>
                                                                <c:when test="${prestamo.estadoP == 'PENDIENTE'}">
                                                                    <input type="hidden" name="estado" value="ENCURSO">
                                                                    <button type="submit" class="btn btn-info btn-sm">
                                                                        <i class="fas fa-play"></i> Iniciar
                                                                    </button>
                                                                </c:when>
                                                                <c:when test="${prestamo.estadoP == 'ENCURSO'}">
                                                                    <input type="hidden" name="estado" value="DEVUELTO">
                                                                    <button type="submit" class="btn btn-success btn-sm">
                                                                        <i class="fas fa-check"></i> Devolver
                                                                    </button>
                                                                </c:when>
                                                            </c:choose>
                                                        </form>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Modal Crear Préstamo -->
                    <div class="modal fade" id="crearPrestamoModal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Crear Nuevo Préstamo</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <form action="${pageContext.request.contextPath}/app/gestion-prestamos" method="post">
                                    <input type="hidden" name="action" value="crear">
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class="form-label">Lector:</label>
                                            <select name="emailLector" class="form-select" required>
                                                <option value="">Seleccione un lector</option>
                                                <c:forEach var="lector" items="${lectores}">
                                                    <option value="${lector.email}">${lector.nombre} (${lector.email})</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Material:</label>
                                            <select name="idMaterial" class="form-select" required>
                                                <option value="">Seleccione un material</option>
                                                <c:forEach var="material" items="${materiales}">
                                                    <option value="${material.id}">
                                                        [${material.id}]
                                                        <c:choose>
                                                            <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                                ${material.titulo}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${material.descripcion}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                        <button type="submit" class="btn btn-primary">Crear Préstamo</button>
                                    </div>
                                </form>
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
