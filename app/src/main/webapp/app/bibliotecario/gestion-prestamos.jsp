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
        
        /* Responsive Mobile */
        @media (max-width: 768px) {
            .sidebar-col {
                position: fixed;
                left: -100%;
                top: 0;
                height: 100vh;
                width: 80%;
                max-width: 280px;
                z-index: 1050;
                transition: left 0.3s ease;
            }
            .sidebar-col.show {
                left: 0;
            }
            .sidebar-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0,0,0,0.5);
                z-index: 1040;
                display: none;
            }
            .sidebar-overlay.show {
                display: block;
            }
            .mobile-menu-btn {
                position: fixed;
                top: 10px;
                left: 10px;
                z-index: 1030;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border: none;
                border-radius: 10px;
                padding: 10px 15px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.2);
            }
            .main-content {
                padding-top: 60px !important;
            }
            .header h2 {
                font-size: 1.3rem;
            }
            .mb-4 .btn {
                width: 100%;
                margin-bottom: 0.5rem !important;
                display: block;
            }
            .table-responsive {
                border: 0;
            }
            .modal-dialog {
                margin: 0.5rem;
            }
            .modal-footer {
                flex-direction: column;
            }
            .modal-footer .btn {
                width: 100%;
                margin: 0.25rem 0 !important;
            }
            .row.g-3 .col-md-4, .row.g-3 .col-md-8 {
                margin-bottom: 0.75rem;
            }
        }
    </style>
</head>
<body>
    <!-- Botón Menú Móvil -->
    <button class="mobile-menu-btn d-md-none" onclick="toggleSidebar()">
        <i class="fas fa-bars"></i>
    </button>
    
    <!-- Overlay para cerrar sidebar en móvil -->
    <div class="sidebar-overlay" onclick="toggleSidebar()"></div>
    
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 px-0 sidebar-col">
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
                            <small class="text-white-50 px-3 mb-2 d-block">REPORTES</small>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/reportes-zona">
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

                    <!-- Botones de Acción -->
                    <div class="mb-4">
                        <button type="button" class="btn btn-primary me-2" data-bs-toggle="modal" data-bs-target="#crearPrestamoModal">
                            <i class="fas fa-plus-circle"></i> Crear Nuevo Préstamo
                        </button>
                        <a href="${pageContext.request.contextPath}/app/gestion-prestamos?misPrestamos=true" 
                           class="btn ${misPrestamosActivo ? 'btn-success' : 'btn-outline-success'} me-2">
                            <i class="fas fa-user-tie"></i> Mis Préstamos Gestionados
                        </a>
                        <a href="${pageContext.request.contextPath}/app/reportes-zona" class="btn btn-outline-info me-2">
                            <i class="fas fa-map-marked-alt"></i> Reporte por Zona
                        </a>
                        <a href="${pageContext.request.contextPath}/app/materiales-pendientes" class="btn btn-outline-warning">
                            <i class="fas fa-exclamation-triangle"></i> Materiales Pendientes
                        </a>
                    </div>

                    <!-- Filtro por Lector (Funcionalidad Opcional) -->
                    <div class="card mb-4">
                        <div class="card-header bg-info text-white">
                            <h5 class="mb-0"><i class="fas fa-user-search"></i> Consultar Préstamos de un Lector</h5>
                        </div>
                        <div class="card-body">
                            <form method="get" class="row g-3">
                                <div class="col-md-8">
                                    <label for="filtroLector" class="form-label">Selecciona un lector:</label>
                                    <select name="emailLector" id="filtroLector" class="form-select">
                                        <option value="">-- Todos los lectores --</option>
                                        <c:forEach var="lector" items="${lectores}">
                                            <option value="${lector.email}" ${param.emailLector == lector.email ? 'selected' : ''}>
                                                ${lector.nombre} (${lector.email}) - ${lector.estadoL}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4 d-flex align-items-end">
                                    <button type="submit" class="btn btn-info me-2">
                                        <i class="fas fa-filter"></i> Filtrar
                                    </button>
                                    <c:if test="${not empty param.emailLector}">
                                        <a href="${pageContext.request.contextPath}/app/gestion-prestamos" class="btn btn-secondary">
                                            <i class="fas fa-times"></i> Limpiar
                                        </a>
                                    </c:if>
                                </div>
                            </form>
                            <c:if test="${not empty param.emailLector}">
                                <c:set var="prestamosActivos" value="0"/>
                                <c:set var="prestamosPendientes" value="0"/>
                                <c:set var="prestamosEnCurso" value="0"/>
                                <c:set var="prestamosDevueltos" value="0"/>
                                <c:forEach var="p" items="${prestamos}">
                                    <c:if test="${p.estadoP == 'PENDIENTE'}">
                                        <c:set var="prestamosPendientes" value="${prestamosPendientes + 1}"/>
                                        <c:set var="prestamosActivos" value="${prestamosActivos + 1}"/>
                                    </c:if>
                                    <c:if test="${p.estadoP == 'ENCURSO'}">
                                        <c:set var="prestamosEnCurso" value="${prestamosEnCurso + 1}"/>
                                        <c:set var="prestamosActivos" value="${prestamosActivos + 1}"/>
                                    </c:if>
                                    <c:if test="${p.estadoP == 'DEVUELTO'}">
                                        <c:set var="prestamosDevueltos" value="${prestamosDevueltos + 1}"/>
                                    </c:if>
                                </c:forEach>
                                
                                <div class="alert alert-success mt-3 mb-0">
                                    <h6 class="mb-3"><i class="fas fa-user-check"></i> Historial del Lector: <strong>${param.emailLector}</strong></h6>
                                    <div class="row text-center">
                                        <div class="col-md-3">
                                            <div class="p-2 bg-white rounded">
                                                <h4 class="text-warning mb-0">${prestamosPendientes}</h4>
                                                <small>Pendientes</small>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="p-2 bg-white rounded">
                                                <h4 class="text-info mb-0">${prestamosEnCurso}</h4>
                                                <small>En Curso</small>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="p-2 bg-white rounded">
                                                <h4 class="text-success mb-0">${prestamosDevueltos}</h4>
                                                <small>Devueltos</small>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="p-2 bg-white rounded">
                                                <h4 class="text-danger mb-0">${prestamosActivos}</h4>
                                                <small><strong>ACTIVOS</strong></small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!-- Tabla de Préstamos -->
                    <div class="card">
                        <div class="card-header ${not empty param.emailLector ? 'bg-success' : 'bg-primary'} text-white">
                            <h5 class="mb-0">
                                <i class="fas fa-list"></i> 
                                <c:choose>
                                    <c:when test="${not empty param.emailLector}">
                                        Préstamos del Lector Seleccionado (${prestamos.size()} total)
                                    </c:when>
                                    <c:otherwise>
                                        Lista de Todos los Préstamos (${prestamos.size()} total)
                                    </c:otherwise>
                                </c:choose>
                            </h5>
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
                                                    <!-- Botón Editar (Funcionalidad Opcional) -->
                                                    <button type="button" class="btn btn-warning btn-sm me-1" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#editarPrestamoModal"
                                                            data-id="${prestamo.id}"
                                                            data-lector="${prestamo.lector.email}"
                                                            data-material="${prestamo.material.id}"
                                                            data-estado="${prestamo.estadoP}"
                                                            data-dia="${prestamo.fechaDevolucion.dia}"
                                                            data-mes="${prestamo.fechaDevolucion.mes}"
                                                            data-anio="${prestamo.fechaDevolucion.anio}"
                                                            data-bibliotecario="${prestamo.bibliotecario.email}"
                                                            onclick="cargarDatosPrestamo(this)">
                                                        <i class="fas fa-edit"></i> Editar
                                                    </button>
                                                    
                                                    <!-- Botones cambio rápido de estado -->
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

                    <!-- Modal Editar Préstamo (Funcionalidad Opcional) -->
                    <div class="modal fade" id="editarPrestamoModal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header bg-warning text-dark">
                                    <h5 class="modal-title"><i class="fas fa-edit"></i> Editar Préstamo</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <form action="${pageContext.request.contextPath}/app/actualizar-prestamo" method="post">
                                    <input type="hidden" name="idPrestamo" id="editIdPrestamo">
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class="form-label">Lector:</label>
                                            <select name="emailLector" id="editEmailLector" class="form-select" required>
                                                <c:forEach var="lector" items="${lectores}">
                                                    <option value="${lector.email}">${lector.nombre} (${lector.email})</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Material:</label>
                                            <select name="idMaterial" id="editIdMaterial" class="form-select" required>
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
                                        <div class="mb-3">
                                            <label class="form-label">Bibliotecario:</label>
                                            <select name="emailBibliotecario" id="editEmailBibliotecario" class="form-select" required>
                                                <c:forEach var="biblio" items="${bibliotecarios}">
                                                    <option value="${biblio.email}">${biblio.nombre} (${biblio.email})</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Estado:</label>
                                            <select name="estado" id="editEstado" class="form-select" required>
                                                <option value="PENDIENTE">Pendiente</option>
                                                <option value="ENCURSO">En Curso</option>
                                                <option value="DEVUELTO">Devuelto</option>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Fecha de Devolución:</label>
                                            <input type="date" name="fechaDevolucion" id="editFechaDevolucion" class="form-control" required>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                        <button type="submit" class="btn btn-warning">
                                            <i class="fas fa-save"></i> Guardar Cambios
                                        </button>
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
    <script>
        // Función para cargar datos del préstamo en el modal de edición
        function cargarDatosPrestamo(button) {
            const id = button.getAttribute('data-id');
            const emailLector = button.getAttribute('data-lector');
            const idMaterial = button.getAttribute('data-material');
            const estado = button.getAttribute('data-estado');
            const dia = button.getAttribute('data-dia');
            const mes = button.getAttribute('data-mes');
            const anio = button.getAttribute('data-anio');
            const emailBibliotecario = button.getAttribute('data-bibliotecario');
            
            document.getElementById('editIdPrestamo').value = id;
            document.getElementById('editEmailLector').value = emailLector;
            document.getElementById('editIdMaterial').value = idMaterial;
            document.getElementById('editEstado').value = estado;
            document.getElementById('editEmailBibliotecario').value = emailBibliotecario;
            
            // Formatear fecha para input type="date" (YYYY-MM-DD)
            const fechaFormateada = anio + '-' + 
                                   (mes.toString().padStart(2, '0')) + '-' + 
                                   (dia.toString().padStart(2, '0'));
            document.getElementById('editFechaDevolucion').value = fechaFormateada;
        }
        
        // Función para toggle del sidebar en móviles
        function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar-col');
            const overlay = document.querySelector('.sidebar-overlay');
            
            if (sidebar && overlay) {
                sidebar.classList.toggle('show');
                overlay.classList.toggle('show');
            }
        }
        
        // Cerrar sidebar al hacer click en un link (solo en móvil)
        document.addEventListener('DOMContentLoaded', function() {
            if (window.innerWidth <= 768) {
                const sidebarLinks = document.querySelectorAll('.sidebar .nav-link');
                sidebarLinks.forEach(link => {
                    link.addEventListener('click', function() {
                        toggleSidebar();
                    });
                });
            }
        });
    </script>
</body>
</html>
