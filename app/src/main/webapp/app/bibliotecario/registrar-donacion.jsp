<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Donación - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .card { box-shadow: 0 15px 35px rgba(0,0,0,0.1); border: none; }
        .btn-primary { background: linear-gradient(45deg, #667eea, #764ba2); border: none; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">📚 Biblioteca Comunitaria</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/bibliotecario">🏠 Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/bibliotecario/materiales">📚 Materiales</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">🚪 Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">📚 Registrar Nueva Donación</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                <i class="bi bi-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty success}">
                            <div class="alert alert-success" role="alert">
                                <i class="bi bi-check-circle"></i> ${success}
                            </div>
                        </c:if>
                        
                        <!-- Selector de tipo de material -->
                        <div class="mb-4">
                            <label class="form-label">Tipo de Material</label>
                            <div class="btn-group w-100" role="group">
                                <input type="radio" class="btn-check" name="tipo" id="libro" value="libro" checked>
                                <label class="btn btn-outline-primary" for="libro">📖 Libro</label>
                                
                                <input type="radio" class="btn-check" name="tipo" id="artespecial" value="artespecial">
                                <label class="btn btn-outline-primary" for="artespecial">🎨 Artículo Especial</label>
                            </div>
                        </div>
                        
                        <form method="post" action="${pageContext.request.contextPath}/bibliotecario/materiales/registrar-donacion">
                            <input type="hidden" name="tipo" id="tipoHidden" value="libro">
                            
                            <!-- Formulario para Libro -->
                            <div id="formularioLibro">
                                <div class="row">
                                    <div class="col-md-8">
                                        <div class="mb-3">
                                            <label for="titulo" class="form-label">Título del Libro</label>
                                            <input type="text" class="form-control" id="titulo" name="titulo" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="mb-3">
                                            <label for="cantPaginas" class="form-label">Cantidad de Páginas</label>
                                            <input type="number" class="form-control" id="cantPaginas" name="cantPaginas" min="1" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Formulario para Artículo Especial -->
                            <div id="formularioArtEspecial" style="display: none;">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <label for="descripcion" class="form-label">Descripción del Artículo</label>
                                            <input type="text" class="form-control" id="descripcion" name="descripcion">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="mb-3">
                                            <label for="peso" class="form-label">Peso (kg)</label>
                                            <input type="number" class="form-control" id="peso" name="peso" step="0.1" min="0.1">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="mb-3">
                                            <label for="ancho" class="form-label">Ancho (cm)</label>
                                            <input type="number" class="form-control" id="ancho" name="ancho" step="0.1" min="0.1">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="mb-3">
                                            <label for="alto" class="form-label">Alto (cm)</label>
                                            <input type="number" class="form-control" id="alto" name="alto" step="0.1" min="0.1">
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="mb-3">
                                            <label for="profundidad" class="form-label">Profundidad (cm)</label>
                                            <input type="number" class="form-control" id="profundidad" name="profundidad" step="0.1" min="0.1">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="${pageContext.request.contextPath}/bibliotecario/materiales" class="btn btn-secondary me-md-2">Cancelar</a>
                                <button type="submit" class="btn btn-primary">Registrar Donación</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Cambiar formulario según el tipo seleccionado
        document.querySelectorAll('input[name="tipo"]').forEach(radio => {
            radio.addEventListener('change', function() {
                const tipo = this.value;
                const formularioLibro = document.getElementById('formularioLibro');
                const formularioArtEspecial = document.getElementById('formularioArtEspecial');
                const tipoHidden = document.getElementById('tipoHidden');
                
                tipoHidden.value = tipo;
                
                if (tipo === 'libro') {
                    formularioLibro.style.display = 'block';
                    formularioArtEspecial.style.display = 'none';
                    // Hacer campos de libro requeridos
                    document.getElementById('titulo').required = true;
                    document.getElementById('cantPaginas').required = true;
                    // Quitar requerido de artículos especiales
                    document.getElementById('descripcion').required = false;
                    document.getElementById('peso').required = false;
                    document.getElementById('ancho').required = false;
                    document.getElementById('alto').required = false;
                    document.getElementById('profundidad').required = false;
                } else {
                    formularioLibro.style.display = 'none';
                    formularioArtEspecial.style.display = 'block';
                    // Quitar requerido de libro
                    document.getElementById('titulo').required = false;
                    document.getElementById('cantPaginas').required = false;
                    // Hacer campos de artículos especiales requeridos
                    document.getElementById('descripcion').required = true;
                    document.getElementById('peso').required = true;
                    document.getElementById('ancho').required = true;
                    document.getElementById('alto').required = true;
                    document.getElementById('profundidad').required = true;
                }
            });
        });
    </script>
</body>
</html>
