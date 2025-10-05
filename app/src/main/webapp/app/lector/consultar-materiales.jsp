<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultar Materiales - Biblioteca Comunitaria</title>
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
        .material-card {
            transition: transform 0.2s ease;
            cursor: pointer;
        }
        .material-card:hover {
            transform: translateY(-3px);
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
        .search-box {
            border-radius: 25px;
            border: 2px solid #e9ecef;
            transition: all 0.3s ease;
        }
        .search-box:focus {
            border-color: #28a745;
            box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.25);
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-prestamos">
                                <i class="fas fa-book-open"></i> Mis Préstamos
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-materiales">
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
                                <h2 class="mb-1">Consultar Materiales</h2>
                                <p class="text-muted mb-0">Explora el catálogo de libros y artículos especiales disponibles</p>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-success fs-6">
                                    <i class="fas fa-book"></i> ${totalMateriales} Materiales Disponibles
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Búsqueda -->
                    <div class="row mb-4">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <div class="input-group">
                                                <span class="input-group-text bg-success text-white">
                                                    <i class="fas fa-search"></i>
                                                </span>
                                                <input type="text" class="form-control search-box" 
                                                       id="searchInput" placeholder="Buscar por título, descripción...">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <select class="form-select" id="filterType">
                                                <option value="all">Todos los materiales</option>
                                                <option value="libros">Solo libros</option>
                                                <option value="artespeciales">Solo artículos especiales</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

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
                                    <p class="text-muted mb-0">Total Disponible</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Navegación por pestañas -->
                    <ul class="nav nav-tabs" id="materialTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="todos-tab" data-bs-toggle="tab" 
                                    data-bs-target="#todos" type="button" role="tab">
                                <i class="fas fa-th"></i> Todos (${totalMateriales})
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
                        <!-- Tab: Todos los materiales -->
                        <div class="tab-pane fade show active" id="todos" role="tabpanel">
                            <div class="row mt-4" id="todosContent">
                                <c:forEach var="material" items="${materiales}">
                                    <div class="col-md-6 col-lg-4 mb-4 material-item" 
                                         data-type="${material.getClass().simpleName.toLowerCase()}"
                                         data-title="${material.getClass().simpleName == 'Libro' ? material.titulo : material.descripcion}">
                                        <div class="card material-card h-100">
                                            <div class="card-body">
                                                <div class="d-flex align-items-start">
                                                    <c:choose>
                                                        <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                            <div class="material-icon bg-primary me-3">
                                                                <i class="fas fa-book"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <h6 class="card-title mb-1">${material.titulo}</h6>
                                                                <p class="card-text text-muted mb-2">
                                                                    <small><i class="fas fa-file-alt"></i> ${material.cantPaginas} páginas</small>
                                                                </p>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="material-icon bg-info me-3">
                                                                <i class="fas fa-cube"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <h6 class="card-title mb-1">${material.descripcion}</h6>
                                                                <p class="card-text text-muted mb-2">
                                                                    <small><i class="fas fa-weight"></i> ${material.peso} kg</small>
                                                                </p>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="mt-3">
                                                    <span class="badge bg-light text-dark">
                                                        <i class="fas fa-calendar"></i>
                                                        Ingreso: <fmt:formatDate value="${material.fechaIngreso}" pattern="dd/MM/yyyy"/>
                                                    </span>
                                                    <span class="badge bg-secondary ms-2">
                                                        ID: #${material.id}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                
                                <c:if test="${empty materiales}">
                                    <div class="col-12">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-book fa-3x mb-3"></i>
                                            <h5>No hay materiales disponibles</h5>
                                            <p>No se encontraron materiales en el catálogo</p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Tab: Libros -->
                        <div class="tab-pane fade" id="libros" role="tabpanel">
                            <div class="row mt-4" id="librosContent">
                                <c:forEach var="libro" items="${libros}">
                                    <div class="col-md-6 col-lg-4 mb-4 material-item" 
                                         data-type="libro" data-title="${libro.titulo}">
                                        <div class="card material-card h-100">
                                            <div class="card-body">
                                                <div class="d-flex align-items-start">
                                                    <div class="material-icon bg-primary me-3">
                                                        <i class="fas fa-book"></i>
                                                    </div>
                                                    <div class="flex-grow-1">
                                                        <h6 class="card-title mb-1">${libro.titulo}</h6>
                                                        <p class="card-text text-muted mb-2">
                                                            <small><i class="fas fa-file-alt"></i> ${libro.cantPaginas} páginas</small>
                                                        </p>
                                                    </div>
                                                </div>
                                                <div class="mt-3">
                                                    <span class="badge bg-light text-dark">
                                                        <i class="fas fa-calendar"></i>
                                                        Ingreso: <fmt:formatDate value="${libro.fechaIngreso}" pattern="dd/MM/yyyy"/>
                                                    </span>
                                                    <span class="badge bg-secondary ms-2">
                                                        ID: #${libro.id}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                
                                <c:if test="${empty libros}">
                                    <div class="col-12">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-book fa-3x mb-3"></i>
                                            <h5>No hay libros disponibles</h5>
                                            <p>No se encontraron libros en el catálogo</p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Tab: Artículos Especiales -->
                        <div class="tab-pane fade" id="artespeciales" role="tabpanel">
                            <div class="row mt-4" id="artespecialesContent">
                                <c:forEach var="artEspecial" items="${artEspeciales}">
                                    <div class="col-md-6 col-lg-4 mb-4 material-item" 
                                         data-type="artespecial" data-title="${artEspecial.descripcion}">
                                        <div class="card material-card h-100">
                                            <div class="card-body">
                                                <div class="d-flex align-items-start">
                                                    <div class="material-icon bg-info me-3">
                                                        <i class="fas fa-cube"></i>
                                                    </div>
                                                    <div class="flex-grow-1">
                                                        <h6 class="card-title mb-1">${artEspecial.descripcion}</h6>
                                                        <p class="card-text text-muted mb-2">
                                                            <small><i class="fas fa-weight"></i> ${artEspecial.peso} kg</small>
                                                        </p>
                                                        <p class="card-text text-muted mb-2">
                                                            <small><i class="fas fa-ruler-combined"></i> 
                                                                ${artEspecial.dimensiones.ancho} x ${artEspecial.dimensiones.alto} x ${artEspecial.dimensiones.profundo} cm
                                                            </small>
                                                        </p>
                                                    </div>
                                                </div>
                                                <div class="mt-3">
                                                    <span class="badge bg-light text-dark">
                                                        <i class="fas fa-calendar"></i>
                                                        Ingreso: <fmt:formatDate value="${artEspecial.fechaIngreso}" pattern="dd/MM/yyyy"/>
                                                    </span>
                                                    <span class="badge bg-secondary ms-2">
                                                        ID: #${artEspecial.id}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                
                                <c:if test="${empty artEspeciales}">
                                    <div class="col-12">
                                        <div class="text-center text-muted py-5">
                                            <i class="fas fa-cube fa-3x mb-3"></i>
                                            <h5>No hay artículos especiales disponibles</h5>
                                            <p>No se encontraron artículos especiales en el catálogo</p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Funcionalidad de búsqueda y filtrado
        document.getElementById('searchInput').addEventListener('input', filterMaterials);
        document.getElementById('filterType').addEventListener('change', filterMaterials);

        function filterMaterials() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const filterType = document.getElementById('filterType').value;
            const activeTab = document.querySelector('.nav-tabs .nav-link.active').getAttribute('data-bs-target');
            const contentDiv = document.querySelector(activeTab);
            const materials = contentDiv.querySelectorAll('.material-item');

            materials.forEach(material => {
                const title = material.getAttribute('data-title').toLowerCase();
                const type = material.getAttribute('data-type');
                
                let showMaterial = true;
                
                // Filtrar por texto de búsqueda
                if (searchTerm && !title.includes(searchTerm)) {
                    showMaterial = false;
                }
                
                // Filtrar por tipo
                if (filterType !== 'all') {
                    if (filterType === 'libros' && type !== 'libro') {
                        showMaterial = false;
                    } else if (filterType === 'artespeciales' && type !== 'artespecial') {
                        showMaterial = false;
                    }
                }
                
                material.style.display = showMaterial ? 'block' : 'none';
            });
        }

        // Agregar funcionalidad de click a las tarjetas de materiales
        document.querySelectorAll('.material-card').forEach(card => {
            card.addEventListener('click', function() {
                // Aquí se podría agregar funcionalidad para mostrar más detalles
                // o solicitar un préstamo
                console.log('Material clicked:', this);
            });
        });
    </script>
</body>
</html>
