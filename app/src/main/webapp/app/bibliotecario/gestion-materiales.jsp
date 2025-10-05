<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
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
        .material-card {
            transition: transform 0.2s ease;
        }
        .material-card:hover {
            transform: translateY(-2px);
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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-materiales">
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
                                <h2 class="mb-1">Gestión de Materiales</h2>
                                <p class="text-muted mb-0">Registrar donaciones y consultar inventario</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-success fs-6">
                                    <i class="fas fa-book"></i> ${totalMateriales} Materiales
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Mensajes -->
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-triangle"></i>
                            <c:choose>
                                <c:when test="${param.error == 'campos_libro_requeridos'}">
                                    Todos los campos del libro son requeridos.
                                </c:when>
                                <c:when test="${param.error == 'paginas_invalidas'}">
                                    El número de páginas debe ser mayor a 0.
                                </c:when>
                                <c:when test="${param.error == 'campos_art_especial_requeridos'}">
                                    Todos los campos del artículo especial son requeridos.
                                </c:when>
                                <c:when test="${param.error == 'valores_dimensiones_invalidos'}">
                                    Los valores de peso y dimensiones deben ser mayores a 0.
                                </c:when>
                                <c:when test="${param.error == 'formato_paginas_invalido'}">
                                    El formato del número de páginas no es válido.
                                </c:when>
                                <c:when test="${param.error == 'formato_numeros_invalido'}">
                                    El formato de los números no es válido.
                                </c:when>
                                <c:when test="${param.error == 'error_registrar_libro'}">
                                    Error al registrar el libro.
                                </c:when>
                                <c:when test="${param.error == 'error_registrar_art_especial'}">
                                    Error al registrar el artículo especial.
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
                                <c:when test="${param.mensaje == 'libro_registrado'}">
                                    Libro registrado exitosamente con ID: ${param.id}
                                </c:when>
                                <c:when test="${param.mensaje == 'art_especial_registrado'}">
                                    Artículo especial registrado exitosamente con ID: ${param.id}
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
                                    <i class="fas fa-book fa-2x text-primary mb-2"></i>
                                    <h4>${totalLibros}</h4>
                                    <p class="text-muted mb-0">Libros</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-cube fa-2x text-info mb-2"></i>
                                    <h4>${totalArtEspeciales}</h4>
                                    <p class="text-muted mb-0">Artículos Especiales</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <i class="fas fa-gift fa-2x text-success mb-2"></i>
                                    <h4>${totalMateriales}</h4>
                                    <p class="text-muted mb-0">Total Donaciones</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Navegación por pestañas -->
                    <ul class="nav nav-tabs" id="materialTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="registrar-tab" data-bs-toggle="tab" 
                                    data-bs-target="#registrar" type="button" role="tab">
                                <i class="fas fa-plus-circle"></i> Registrar Donación
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="libros-tab" data-bs-toggle="tab" 
                                    data-bs-target="#libros" type="button" role="tab">
                                <i class="fas fa-book"></i> Libros (${totalLibros})
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="artespeciales-tab" data-bs-toggle="tab" 
                                    data-bs-target="#artespeciales" type="button" role="tab">
                                <i class="fas fa-cube"></i> Artículos Especiales (${totalArtEspeciales})
                            </button>
                        </li>
                    </ul>

                    <div class="tab-content" id="materialTabsContent">
                        <!-- Tab: Registrar Donación -->
                        <div class="tab-pane fade show active" id="registrar" role="tabpanel">
                            <div class="row mt-4">
                                <div class="col-md-6">
                                    <!-- Formulario para registrar libro -->
                                    <div class="card">
                                        <div class="card-header bg-primary text-white">
                                            <h5 class="mb-0"><i class="fas fa-book"></i> Registrar Donación de Libro</h5>
                                        </div>
                                        <div class="card-body">
                                            <form action="${pageContext.request.contextPath}/app/gestion-materiales" method="post">
                                                <input type="hidden" name="accion" value="registrar_libro">
                                                
                                                <div class="mb-3">
                                                    <label for="titulo" class="form-label">Título del Libro</label>
                                                    <input type="text" class="form-control" id="titulo" name="titulo" 
                                                           placeholder="Ingresa el título del libro" required>
                                                </div>
                                                
                                                <div class="mb-3">
                                                    <label for="cantPaginas" class="form-label">Cantidad de Páginas</label>
                                                    <input type="number" class="form-control" id="cantPaginas" name="cantPaginas" 
                                                           placeholder="Ingresa la cantidad de páginas" min="1" required>
                                                </div>
                                                
                                                <button type="submit" class="btn btn-primary w-100">
                                                    <i class="fas fa-plus"></i> Registrar Libro
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-md-6">
                                    <!-- Formulario para registrar artículo especial -->
                                    <div class="card">
                                        <div class="card-header bg-info text-white">
                                            <h5 class="mb-0"><i class="fas fa-cube"></i> Registrar Donación de Artículo Especial</h5>
                                        </div>
                                        <div class="card-body">
                                            <form action="${pageContext.request.contextPath}/app/gestion-materiales" method="post">
                                                <input type="hidden" name="accion" value="registrar_art_especial">
                                                
                                                <div class="mb-3">
                                                    <label for="descripcion" class="form-label">Descripción</label>
                                                    <input type="text" class="form-control" id="descripcion" name="descripcion" 
                                                           placeholder="Descripción del artículo" required>
                                                </div>
                                                
                                                <div class="mb-3">
                                                    <label for="peso" class="form-label">Peso (kg)</label>
                                                    <input type="number" class="form-control" id="peso" name="peso" 
                                                           placeholder="Peso en kilogramos" step="0.1" min="0.1" required>
                                                </div>
                                                
                                                <div class="row">
                                                    <div class="col-4">
                                                        <div class="mb-3">
                                                            <label for="ancho" class="form-label">Ancho (cm)</label>
                                                            <input type="number" class="form-control" id="ancho" name="ancho" 
                                                                   placeholder="Ancho" step="0.1" min="0.1" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-4">
                                                        <div class="mb-3">
                                                            <label for="alto" class="form-label">Alto (cm)</label>
                                                            <input type="number" class="form-control" id="alto" name="alto" 
                                                                   placeholder="Alto" step="0.1" min="0.1" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-4">
                                                        <div class="mb-3">
                                                            <label for="profundo" class="form-label">Profundo (cm)</label>
                                                            <input type="number" class="form-control" id="profundo" name="profundo" 
                                                                   placeholder="Profundo" step="0.1" min="0.1" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                <button type="submit" class="btn btn-info w-100">
                                                    <i class="fas fa-plus"></i> Registrar Artículo Especial
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Tab: Libros -->
                        <div class="tab-pane fade" id="libros" role="tabpanel">
                            <div class="card mt-4">
                                <div class="card-header">
                                    <h5 class="mb-0"><i class="fas fa-book"></i> Lista de Libros Donados</h5>
                                </div>
                                <div class="card-body p-0">
                                    <div class="table-responsive">
                                        <table class="table table-hover mb-0">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Título</th>
                                                    <th>Páginas</th>
                                                    <th>Fecha Ingreso</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="libro" items="${libros}">
                                                    <tr>
                                                        <td><strong>#${libro.id}</strong></td>
                                                        <td>${libro.titulo}</td>
                                                        <td>${libro.cantPaginas}</td>
                                                        <td>
                                                            <fmt:formatDate value="${libro.fechaIngreso}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty libros}">
                                                    <tr>
                                                        <td colspan="4" class="text-center text-muted">
                                                            <i class="fas fa-book fa-2x mb-2"></i><br>
                                                            No hay libros registrados
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Tab: Artículos Especiales -->
                        <div class="tab-pane fade" id="artespeciales" role="tabpanel">
                            <div class="card mt-4">
                                <div class="card-header">
                                    <h5 class="mb-0"><i class="fas fa-cube"></i> Lista de Artículos Especiales Donados</h5>
                                </div>
                                <div class="card-body p-0">
                                    <div class="table-responsive">
                                        <table class="table table-hover mb-0">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Descripción</th>
                                                    <th>Peso (kg)</th>
                                                    <th>Dimensiones (cm)</th>
                                                    <th>Fecha Ingreso</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="artEspecial" items="${artEspeciales}">
                                                    <tr>
                                                        <td><strong>#${artEspecial.id}</strong></td>
                                                        <td>${artEspecial.descripcion}</td>
                                                        <td>${artEspecial.peso}</td>
                                                        <td>
                                                            ${artEspecial.dimensiones.ancho} x ${artEspecial.dimensiones.alto} x ${artEspecial.dimensiones.profundo}
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${artEspecial.fechaIngreso}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty artEspeciales}">
                                                    <tr>
                                                        <td colspan="5" class="text-center text-muted">
                                                            <i class="fas fa-cube fa-2x mb-2"></i><br>
                                                            No hay artículos especiales registrados
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
</body>
</html>
