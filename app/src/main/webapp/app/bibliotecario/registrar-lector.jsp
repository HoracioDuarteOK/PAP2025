<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Lector - Biblioteca Comunitaria</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/bibliotecario/usuarios">👥 Usuarios</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">🚪 Cerrar Sesión</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">👤 Registrar Nuevo Lector</h5>
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
                        
                        <form method="post" action="${pageContext.request.contextPath}/bibliotecario/usuarios/registrar-lector">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="nombre" class="form-label">Nombre Completo</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="direccion" class="form-label">Dirección</label>
                                        <input type="text" class="form-control" id="direccion" name="direccion" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="zona" class="form-label">Zona</label>
                                        <select class="form-select" id="zona" name="zona" required>
                                            <option value="">Seleccionar zona...</option>
                                            <option value="BIBLOTECA_CENTRAL">Biblioteca Central</option>
                                            <option value="SUCURSAL_ESTE">Sucursal Este</option>
                                            <option value="SUCURSAL_OESTE">Sucursal Oeste</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="${pageContext.request.contextPath}/bibliotecario/usuarios" class="btn btn-secondary me-md-2">Cancelar</a>
                                <button type="submit" class="btn btn-primary">Registrar Lector</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
