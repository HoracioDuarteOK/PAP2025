<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Materiales - Biblioteca Comunitaria</title>
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-materiales">
                                <i class="fas fa-book"></i> Gestión de Materiales
                            </a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-prestamos">
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
                        <h2><i class="fas fa-book"></i> Gestión de Materiales</h2>
                        <p class="text-muted mb-0">Registra donaciones y consulta el inventario</p>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.success != null}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle"></i>
                            <c:choose>
                                <c:when test="${param.success == 'libro_registrado'}">
                                    Libro registrado correctamente.
                                </c:when>
                                <c:when test="${param.success == 'articulo_registrado'}">
                                    Artículo especial registrado correctamente.
                                </c:when>
                                <c:otherwise>
                                    Material registrado exitosamente.
                                </c:otherwise>
                            </c:choose>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <!-- Botón Registrar Donación -->
                    <div class="mb-4">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registrarDonacionModal">
                            <i class="fas fa-plus-circle"></i> Registrar Nueva Donación
                        </button>
                    </div>

                    <!-- Tabla de Materiales -->
                    <div class="card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="fas fa-list"></i> Inventario de Materiales</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tipo</th>
                                            <th>Título/Descripción</th>
                                            <th>Detalles</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="material" items="${materiales}">
                                            <tr>
                                                <td>${material.id}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                            <span class="badge bg-primary"><i class="fas fa-book"></i> Libro</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-info"><i class="fas fa-cube"></i> Artículo</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                            ${material.titulo}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${material.descripcion}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                            <small>${material.cantPaginas} páginas</small>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <small>Peso: ${material.peso} kg</small>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Modal Registrar Donación -->
                    <div class="modal fade" id="registrarDonacionModal" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Registrar Nueva Donación</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <div class="modal-body">
                                    <ul class="nav nav-tabs" id="materialTabs" role="tablist">
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link active" id="libro-tab" data-bs-toggle="tab" data-bs-target="#libro" type="button">
                                                <i class="fas fa-book"></i> Libro
                                            </button>
                                        </li>
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link" id="articulo-tab" data-bs-toggle="tab" data-bs-target="#articulo" type="button">
                                                <i class="fas fa-cube"></i> Artículo Especial
                                            </button>
                                        </li>
                                    </ul>
                                    
                                    <div class="tab-content mt-3" id="materialTabContent">
                                        <!-- Form Libro -->
                                        <div class="tab-pane fade show active" id="libro" role="tabpanel">
                                            <form action="${pageContext.request.contextPath}/app/gestion-materiales" method="post">
                                                <input type="hidden" name="tipoMaterial" value="libro">
                                                <div class="mb-3">
                                                    <label class="form-label">Título:</label>
                                                    <input type="text" name="titulo" class="form-control" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Cantidad de Páginas:</label>
                                                    <input type="number" name="cantPaginas" class="form-control" min="1" required>
                                                </div>
                                                <button type="submit" class="btn btn-primary">Registrar Libro</button>
                                            </form>
                                        </div>
                                        
                                        <!-- Form Artículo Especial -->
                                        <div class="tab-pane fade" id="articulo" role="tabpanel">
                                            <form action="${pageContext.request.contextPath}/app/gestion-materiales" method="post">
                                                <input type="hidden" name="tipoMaterial" value="articulo">
                                                <div class="mb-3">
                                                    <label class="form-label">Descripción:</label>
                                                    <input type="text" name="descripcion" class="form-control" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Peso (kg):</label>
                                                    <input type="number" name="peso" class="form-control" step="0.1" min="0" required>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label class="form-label">Alto (cm):</label>
                                                        <input type="number" name="alto" class="form-control" step="0.1" min="0" required>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="form-label">Ancho (cm):</label>
                                                        <input type="number" name="ancho" class="form-control" step="0.1" min="0" required>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="form-label">Profundidad (cm):</label>
                                                        <input type="number" name="profundidad" class="form-control" step="0.1" min="0" required>
                                                    </div>
                                                </div>
                                                <button type="submit" class="btn btn-primary mt-3">Registrar Artículo</button>
                                            </form>
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
