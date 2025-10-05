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
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        .table {
            border-radius: 10px;
            overflow: hidden;
        }
        .badge-estado {
            padding: 0.5rem 1rem;
            border-radius: 20px;
            font-size: 0.8rem;
        }
        .btn-action {
            padding: 0.25rem 0.5rem;
            margin: 0.1rem;
            border-radius: 5px;
        }
        .nav-tabs .nav-link {
            border-radius: 10px 10px 0 0;
            border: none;
            margin-right: 0.2rem;
        }
        .nav-tabs .nav-link.active {
            background: #667eea;
            color: white;
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/bibliotecario/dashboard.jsp">
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/bibliotecario/reportes">
                                <i class="fas fa-chart-bar"></i> Reportes
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/bibliotecario/perfil">
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
                                <h2 class="mb-1">Gestión de Préstamos</h2>
                                <p class="text-muted mb-0">Administrar préstamos y devoluciones</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-warning fs-6">
                                    <i class="fas fa-exchange-alt"></i> ${totalPrestamos} Préstamos
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i>
                            <c:choose>
                                <c:when test="${param.error == 'parametros_requeridos'}">
                                    Todos los parámetros son requeridos.
                                </c:when>
                                <c:when test="${param.error == 'objetos_no_encontrados'}">
                                    Algunos objetos no fueron encontrados.
                                </c:when>
                                <c:when test="${param.error == 'formato_id_invalido'}">
                                    El formato del ID no es válido.
                                </c:when>
                                <c:when test="${param.error == 'estado_invalido'}">
                                    El estado seleccionado no es válido.
                                </c:when>
                                <c:when test="${param.error == 'error_crear_prestamo'}">
                                    Error al crear el préstamo.
                                </c:when>
                                <c:when test="${param.error == 'error_actualizar_estado'}">
                                    Error al actualizar el estado del préstamo.
                                </c:when>
                                <c:when test="${param.error == 'error_procesar_prestamo'}">
                                    Error al procesar el préstamo.
                                </c:when>
                                <c:otherwise>
                                    Error: ${param.error}
                                </c:otherwise>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <c:if test="${param.mensaje != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle"></i>
                            <c:choose>
                                <c:when test="${param.mensaje == 'prestamo_creado'}">
                                    Préstamo creado exitosamente con ID: ${param.id}
                                </c:when>
                                <c:when test="${param.mensaje == 'estado_actualizado'}">
                                    Estado del préstamo actualizado exitosamente.
                                </c:when>
                                <c:otherwise>
                                    ${param.mensaje}
                                </c:otherwise>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Estadísticas -->
                    <div class="row mb-4">
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-clock fa-2x text-warning mb-2"></i>
                                    <h4>${prestamosPendientes.size()}</h4>
                                    <p class="text-muted mb-0">Pendientes</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-play fa-2x text-info mb-2"></i>
                                    <h4>${prestamosEnCurso.size()}</h4>
                                    <p class="text-muted mb-0">En Curso</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-check fa-2x text-success mb-2"></i>
                                    <h4>${prestamosDevueltos.size()}</h4>
                                    <p class="text-muted mb-0">Devueltos</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-book fa-2x text-primary mb-2"></i>
                                    <h4>${materialesDisponibles.size()}</h4>
                                    <p class="text-muted mb-0">Materiales Disponibles</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Navegación por pestañas -->
                    <ul class="nav nav-tabs" id="prestamosTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="crear-tab" data-bs-toggle="tab" 
                                    data-bs-target="#crear" type="button" role="tab">
                                <i class="fas fa-plus-circle"></i> Crear Préstamo
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="listado-tab" data-bs-toggle="tab" 
                                    data-bs-target="#listado" type="button" role="tab">
                                <i class="fas fa-list"></i> Listado de Préstamos
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
                    </ul>

                    <div class="tab-content" id="prestamosTabsContent">
                        <!-- Tab: Crear Préstamo -->
                        <div class="tab-pane fade" id="crear" role="tabpanel">
                            <div class="card mt-4">
                                <div class="card-header bg-success text-white">
                                    <h5 class="mb-0"><i class="fas fa-plus-circle"></i> Crear Nuevo Préstamo</h5>
                                </div>
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/app/gestion-prestamos" method="post">
                                        <input type="hidden" name="accion" value="crear_prestamo">
                                        
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label for="idMaterial" class="form-label">Material</label>
                                                    <select class="form-select" name="idMaterial" id="idMaterial" required>
                                                        <option value="">Selecciona un material</option>
                                                        <c:forEach var="material" items="${materialesDisponibles}">
                                                            <option value="${material.id}">
                                                                <c:choose>
                                                                    <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                                        📚 ${material.titulo}
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        📦 ${material.descripcion}
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label for="emailLector" class="form-label">Lector</label>
                                                    <select class="form-select" name="emailLector" id="emailLector" required>
                                                        <option value="">Selecciona un lector</option>
                                                        <c:forEach var="lector" items="${lectoresActivos}">
                                                            <option value="${lector.email}">${lector.nombre} (${lector.email})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label for="idBibliotecario" class="form-label">Bibliotecario</label>
                                                    <select class="form-select" name="idBibliotecario" id="idBibliotecario" required>
                                                        <option value="">Selecciona un bibliotecario</option>
                                                        <c:forEach var="bibliotecario" items="${bibliotecarios}">
                                                            <option value="${bibliotecario.nroEmpleado}">${bibliotecario.nombre} (Nro: ${bibliotecario.nroEmpleado})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label for="fechaDevolucion" class="form-label">Fecha de Devolución</label>
                                                    <input type="date" class="form-control" name="fechaDevolucion" id="fechaDevolucion" required>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="text-end">
                                            <button type="submit" class="btn btn-success">
                                                <i class="fas fa-plus"></i> Crear Préstamo
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Tab: Listado de Préstamos -->
                        <div class="tab-pane fade show active" id="listado" role="tabpanel">
                            <div class="card mt-4">
                                <div class="card-header">
                                    <h5 class="mb-0"><i class="fas fa-list"></i> Todos los Préstamos</h5>
                                </div>
                                <div class="card-body p-0">
                                    <div class="table-responsive">
                                        <table class="table table-hover mb-0">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Material</th>
                                                    <th>Lector</th>
                                                    <th>Bibliotecario</th>
                                                    <th>Fecha Solicitud</th>
                                                    <th>Fecha Devolución</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="prestamo" items="${prestamos}">
                                                    <tr>
                                                        <td><strong>#${prestamo.id}</strong></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    <i class="fas fa-book text-primary"></i> ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="fas fa-cube text-info"></i> ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${prestamo.lector.nombre}</td>
                                                        <td>${prestamo.bibliotecario.nombre}</td>
                                                        <td>
                                                            <fmt:formatDate value="${prestamo.fechaSolicitada}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                        <td>
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
                                                        </td>
                                                        <td>
                                                            <c:if test="${prestamo.estadoP != 'DEVUELTO'}">
                                                                <button class="btn btn-success btn-action" 
                                                                        onclick="actualizarEstado(${prestamo.id}, 'DEVUELTO')"
                                                                        title="Marcar como Devuelto">
                                                                    <i class="fas fa-check"></i>
                                                                </button>
                                                            </c:if>
                                                            <c:if test="${prestamo.estadoP == 'PENDIENTE'}">
                                                                <button class="btn btn-info btn-action" 
                                                                        onclick="actualizarEstado(${prestamo.id}, 'ENCURSO')"
                                                                        title="Marcar como En Curso">
                                                                    <i class="fas fa-play"></i>
                                                                </button>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty prestamos}">
                                                    <tr>
                                                        <td colspan="8" class="text-center text-muted">
                                                            <i class="fas fa-exchange-alt fa-2x mb-2"></i><br>
                                                            No hay préstamos registrados
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Tab: Préstamos Pendientes -->
                        <div class="tab-pane fade" id="pendientes" role="tabpanel">
                            <div class="card mt-4">
                                <div class="card-header bg-warning text-white">
                                    <h5 class="mb-0"><i class="fas fa-clock"></i> Préstamos Pendientes</h5>
                                </div>
                                <div class="card-body p-0">
                                    <div class="table-responsive">
                                        <table class="table table-hover mb-0">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Material</th>
                                                    <th>Lector</th>
                                                    <th>Fecha Solicitud</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="prestamo" items="${prestamosPendientes}">
                                                    <tr>
                                                        <td><strong>#${prestamo.id}</strong></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    <i class="fas fa-book text-primary"></i> ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="fas fa-cube text-info"></i> ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${prestamo.lector.nombre}</td>
                                                        <td>
                                                            <fmt:formatDate value="${prestamo.fechaSolicitada}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-info btn-action" 
                                                                    onclick="actualizarEstado(${prestamo.id}, 'ENCURSO')"
                                                                    title="Aprobar Préstamo">
                                                                <i class="fas fa-check"></i> Aprobar
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty prestamosPendientes}">
                                                    <tr>
                                                        <td colspan="5" class="text-center text-muted">
                                                            <i class="fas fa-clock fa-2x mb-2"></i><br>
                                                            No hay préstamos pendientes
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Tab: Préstamos En Curso -->
                        <div class="tab-pane fade" id="curso" role="tabpanel">
                            <div class="card mt-4">
                                <div class="card-header bg-info text-white">
                                    <h5 class="mb-0"><i class="fas fa-play"></i> Préstamos En Curso</h5>
                                </div>
                                <div class="card-body p-0">
                                    <div class="table-responsive">
                                        <table class="table table-hover mb-0">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Material</th>
                                                    <th>Lector</th>
                                                    <th>Fecha Devolución</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="prestamo" items="${prestamosEnCurso}">
                                                    <tr>
                                                        <td><strong>#${prestamo.id}</strong></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${prestamo.material.getClass().simpleName == 'Libro'}">
                                                                    <i class="fas fa-book text-primary"></i> ${prestamo.material.titulo}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="fas fa-cube text-info"></i> ${prestamo.material.descripcion}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${prestamo.lector.nombre}</td>
                                                        <td>
                                                            <fmt:formatDate value="${prestamo.fechaDevolucion}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-success btn-action" 
                                                                    onclick="actualizarEstado(${prestamo.id}, 'DEVUELTO')"
                                                                    title="Registrar Devolución">
                                                                <i class="fas fa-undo"></i> Devolver
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty prestamosEnCurso}">
                                                    <tr>
                                                        <td colspan="5" class="text-center text-muted">
                                                            <i class="fas fa-play fa-2x mb-2"></i><br>
                                                            No hay préstamos en curso
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
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
    <script>
        function actualizarEstado(idPrestamo, nuevoEstado) {
            const mensaje = nuevoEstado === 'DEVUELTO' ? 
                '¿Estás seguro de que quieres marcar este préstamo como devuelto?' :
                '¿Estás seguro de que quieres cambiar el estado de este préstamo?';
            
            if (confirm(mensaje)) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '${pageContext.request.contextPath}/app/gestion-prestamos';
                
                const accion = document.createElement('input');
                accion.type = 'hidden';
                accion.name = 'accion';
                accion.value = 'actualizar_estado';
                
                const idInput = document.createElement('input');
                idInput.type = 'hidden';
                idInput.name = 'idPrestamo';
                idInput.value = idPrestamo;
                
                const estadoInput = document.createElement('input');
                estadoInput.type = 'hidden';
                estadoInput.name = 'nuevoEstado';
                estadoInput.value = nuevoEstado;
                
                form.appendChild(accion);
                form.appendChild(idInput);
                form.appendChild(estadoInput);
                document.body.appendChild(form);
                form.submit();
            }
        }

        // Establecer fecha mínima para la fecha de devolución (hoy)
        document.addEventListener('DOMContentLoaded', function() {
            const fechaDevolucion = document.getElementById('fechaDevolucion');
            const hoy = new Date().toISOString().split('T')[0];
            fechaDevolucion.min = hoy;
        });
    </script>
</body>
</html>
