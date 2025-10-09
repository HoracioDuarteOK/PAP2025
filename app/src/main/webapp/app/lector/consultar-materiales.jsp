<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        .material-card {
            transition: transform 0.2s;
        }
        .material-card:hover {
            transform: translateY(-5px);
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/gestion-prestamos">
                                <i class="fas fa-book-open"></i> Mis Préstamos
                            </a>
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/gestion-materiales">
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
                        <h2><i class="fas fa-search"></i> Catálogo de Materiales</h2>
                        <p class="text-muted mb-0">Explora libros y artículos especiales disponibles</p>
                    </div>

                    <!-- Filtros -->
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-search"></i></span>
                                        <input type="text" class="form-control" id="searchInput" placeholder="Buscar materiales...">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <select class="form-select" id="filterType">
                                        <option value="">Todos los tipos</option>
                                        <option value="Libro">Libros</option>
                                        <option value="ArtEspeciales">Artículos Especiales</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Lista de Materiales -->
                    <div class="row" id="materialesList">
                        <c:forEach var="material" items="${materiales}">
                            <div class="col-md-6 col-lg-4 mb-4 material-item" data-type="${material.getClass().simpleName}">
                                <div class="card material-card h-100">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-start mb-3">
                                            <c:choose>
                                                <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                    <span class="badge bg-primary"><i class="fas fa-book"></i> Libro</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-info"><i class="fas fa-cube"></i> Artículo</span>
                                                </c:otherwise>
                                            </c:choose>
                                            <span class="text-muted">ID: ${material.id}</span>
                                        </div>
                                        
                                        <h5 class="card-title">
                                            <c:choose>
                                                <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                    ${material.titulo}
                                                </c:when>
                                                <c:otherwise>
                                                    ${material.descripcion}
                                                </c:otherwise>
                                            </c:choose>
                                        </h5>
                                        
                                        <div class="card-text">
                                            <c:choose>
                                                <c:when test="${material.getClass().simpleName == 'Libro'}">
                                                    <p class="mb-1"><small><i class="fas fa-file-alt"></i> ${material.cantPaginas} páginas</small></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="mb-1"><small><i class="fas fa-weight"></i> Peso: ${material.peso} kg</small></p>
                                                    <p class="mb-1"><small><i class="fas fa-ruler-combined"></i> 
                                                        ${material.dimensiones.alto} x ${material.dimensiones.ancho} x ${material.dimensiones.prof} cm
                                                    </small></p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <c:if test="${empty materiales}">
                        <div class="text-center text-muted py-5">
                            <i class="fas fa-inbox fa-3x mb-3"></i>
                            <h5>No hay materiales disponibles</h5>
                            <p>Por el momento no hay materiales en el catálogo</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Esperar a que el DOM esté completamente cargado
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchInput');
            const filterType = document.getElementById('filterType');
            
            if (searchInput && filterType) {
                searchInput.addEventListener('keyup', filterMaterials);
                filterType.addEventListener('change', filterMaterials);
                console.log('✅ Filtros de búsqueda activados');
            } else {
                console.error('❌ No se encontraron los elementos de filtro');
            }
        });

        function filterMaterials() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const filterType = document.getElementById('filterType').value;
            const items = document.querySelectorAll('.material-item');

            console.log('🔍 Filtrando: búsqueda="' + searchTerm + '", tipo="' + filterType + '"');
            console.log('📊 Total items encontrados:', items.length);
            
            let visibleCount = 0;
            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                const type = item.getAttribute('data-type');
                
                const matchesSearch = searchTerm === '' || text.includes(searchTerm);
                const matchesType = filterType === '' || type === filterType;

                if (matchesSearch && matchesType) {
                    item.style.display = '';
                    visibleCount++;
                } else {
                    item.style.display = 'none';
                }
            });
            
            console.log('👁️ Materiales visibles:', visibleCount);
        }
    </script>
</body>
</html>
