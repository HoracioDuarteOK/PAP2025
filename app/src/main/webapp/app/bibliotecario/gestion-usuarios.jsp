<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios - Biblioteca Comunitaria</title>
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-usuarios">
                                <i class="fas fa-users"></i> Gestión de Usuarios
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-materiales">
                                <i class="fas fa-book"></i> Gestión de Materiales
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-prestamos">
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
                                <h2 class="mb-1">Gestión de Usuarios</h2>
                                <p class="text-muted mb-0">Administrar lectores y sus estados</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-primary fs-6">
                                    <i class="fas fa-users"></i> ${totalLectores} Lectores
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i>
                            <c:choose>
                                <c:when test="${param.error == 'email_requerido'}">
                                    El email del lector es requerido.
                                </c:when>
                                <c:when test="${param.error == 'parametros_requeridos'}">
                                    Todos los parámetros son requeridos.
                                </c:when>
                                <c:when test="${param.error == 'zona_invalida'}">
                                    La zona seleccionada no es válida.
                                </c:when>
                                <c:when test="${param.error == 'error_suspender_lector'}">
                                    Error al suspender el lector.
                                </c:when>
                                <c:when test="${param.error == 'error_activar_lector'}">
                                    Error al activar el lector.
                                </c:when>
                                <c:when test="${param.error == 'error_cambiar_zona'}">
                                    Error al cambiar la zona del lector.
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
                                <c:when test="${param.mensaje == 'lector_suspendido'}">
                                    Lector suspendido exitosamente.
                                </c:when>
                                <c:when test="${param.mensaje == 'lector_activado'}">
                                    Lector activado exitosamente.
                                </c:when>
                                <c:when test="${param.mensaje == 'zona_actualizada'}">
                                    Zona del lector actualizada exitosamente.
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
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-users fa-2x text-primary mb-2"></i>
                                    <h4>${totalLectores}</h4>
                                    <p class="text-muted mb-0">Total Lectores</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-user-check fa-2x text-success mb-2"></i>
                                    <h4>${lectoresActivos}</h4>
                                    <p class="text-muted mb-0">Lectores Activos</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-user-times fa-2x text-danger mb-2"></i>
                                    <h4>${lectoresSuspendidos}</h4>
                                    <p class="text-muted mb-0">Lectores Suspendidos</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Lista de Lectores -->
                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0"><i class="fas fa-list"></i> Lista de Lectores</h5>
                        </div>
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover mb-0">
                                    <thead class="table-light">
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Email</th>
                                            <th>Dirección</th>
                                            <th>Zona</th>
                                            <th>Estado</th>
                                            <th>Fecha Registro</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="lector" items="${lectores}">
                                            <tr>
                                                <td>
                                                    <strong>${lector.nombre}</strong>
                                                </td>
                                                <td>${lector.email}</td>
                                                <td>${lector.direccion}</td>
                                                <td>
                                                    <span class="badge bg-info">${lector.zona}</span>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${lector.estadoL == 'ACTIVO'}">
                                                            <span class="badge-estado bg-success text-white">Activo</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge-estado bg-danger text-white">Suspendido</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${lector.fechaRegistro}" pattern="dd/MM/yyyy"/>
                                                </td>
                                                <td>
                                                    <div class="btn-group" role="group">
                                                        <c:choose>
                                                            <c:when test="${lector.estadoL == 'ACTIVO'}">
                                                                <button class="btn btn-warning btn-action" 
                                                                        onclick="suspenderLector('${lector.email}')"
                                                                        title="Suspender">
                                                                    <i class="fas fa-ban"></i>
                                                                </button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button class="btn btn-success btn-action" 
                                                                        onclick="activarLector('${lector.email}')"
                                                                        title="Activar">
                                                                    <i class="fas fa-check"></i>
                                                                </button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        
                                                        <button class="btn btn-info btn-action" 
                                                                onclick="cambiarZona('${lector.email}', '${lector.zona}')"
                                                                title="Cambiar Zona">
                                                            <i class="fas fa-map-marker-alt"></i>
                                                        </button>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para cambiar zona -->
    <div class="modal fade" id="modalCambiarZona" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Cambiar Zona del Lector</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="${pageContext.request.contextPath}/app/gestion-usuarios" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="accion" value="cambiar_zona">
                        <input type="hidden" name="emailLector" id="emailLectorZona">
                        
                        <div class="mb-3">
                            <label for="nuevaZona" class="form-label">Nueva Zona</label>
                            <select class="form-select" name="nuevaZona" id="nuevaZona" required>
                                <option value="">Selecciona una zona</option>
                                <option value="BIBLOTECA_CENTRAL">Biblioteca Central</option>
                                <option value="SUCURSAL_ESTE">Sucursal Este</option>
                                <option value="SUCURSAL_OESTE">Sucursal Oeste</option>
                                <option value="BIBLOTECA_INFANTIL">Biblioteca Infantil</option>
                                <option value="ARCHIVO_GENERAL">Archivo General</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Cambiar Zona</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function suspenderLector(email) {
            if (confirm('¿Estás seguro de que quieres suspender este lector?')) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '${pageContext.request.contextPath}/app/gestion-usuarios';
                
                const accion = document.createElement('input');
                accion.type = 'hidden';
                accion.name = 'accion';
                accion.value = 'suspender';
                
                const emailInput = document.createElement('input');
                emailInput.type = 'hidden';
                emailInput.name = 'emailLector';
                emailInput.value = email;
                
                form.appendChild(accion);
                form.appendChild(emailInput);
                document.body.appendChild(form);
                form.submit();
            }
        }

        function activarLector(email) {
            if (confirm('¿Estás seguro de que quieres activar este lector?')) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = '${pageContext.request.contextPath}/app/gestion-usuarios';
                
                const accion = document.createElement('input');
                accion.type = 'hidden';
                accion.name = 'accion';
                accion.value = 'activar';
                
                const emailInput = document.createElement('input');
                emailInput.type = 'hidden';
                emailInput.name = 'emailLector';
                emailInput.value = email;
                
                form.appendChild(accion);
                form.appendChild(emailInput);
                document.body.appendChild(form);
                form.submit();
            }
        }

        function cambiarZona(email, zonaActual) {
            document.getElementById('emailLectorZona').value = email;
            document.getElementById('nuevaZona').value = zonaActual;
            
            const modal = new bootstrap.Modal(document.getElementById('modalCambiarZona'));
            modal.show();
        }
    </script>
</body>
</html>
