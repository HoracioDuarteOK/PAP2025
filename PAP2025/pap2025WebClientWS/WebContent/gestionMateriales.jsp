<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="publicadores.DtMaterial" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<title>Gestión de Materiales - Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="dashboardBibliotecario.jsp">Biblioteca Comunitaria</a>
		<div class="navbar-nav ml-auto">
			<span class="navbar-text mr-3">
				📚 <%= session.getAttribute("nombreUsuario") %>
			</span>
			<a class="nav-link" href="Logout">🚪 Cerrar Sesión</a>
		</div>
	</nav>

	<div class="container mt-4">
		<h2 class="mb-4">📖 Gestión de Materiales</h2>
		
		<% if (request.getAttribute("error") != null) { %>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<%= request.getAttribute("error") %>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		<% } %>
		
		<!-- Formularios para registrar materiales -->
		<div class="row mb-4">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<h5>📚 Registrar Libro</h5>
					</div>
					<div class="card-body">
						<form action="GestionMateriales" method="post">
							<input type="hidden" name="tipoMaterial" value="libro">
							<div class="form-group">
								<label for="titulo">📖 Título del Libro:</label>
								<input type="text" class="form-control" id="titulo" name="titulo" required>
							</div>
							<div class="form-group">
								<label for="cantidadPaginas">📄 Cantidad de Páginas:</label>
								<input type="number" class="form-control" id="cantidadPaginas" name="cantidadPaginas" min="1" required>
							</div>
							<button type="submit" class="btn btn-primary">📚 Registrar Libro</button>
						</form>
					</div>
				</div>
			</div>
			
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<h5>📦 Registrar Artículo Especial</h5>
					</div>
					<div class="card-body">
						<form action="GestionMateriales" method="post">
							<input type="hidden" name="tipoMaterial" value="articulo">
							<div class="form-group">
								<label for="descripcion">📝 Descripción:</label>
								<input type="text" class="form-control" id="descripcion" name="descripcion" required>
							</div>
							<div class="form-group">
								<label for="peso">⚖️ Peso (kg):</label>
								<input type="number" class="form-control" id="peso" name="peso" step="0.1" min="0" required>
							</div>
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label for="largo">📏 Largo (cm):</label>
										<input type="number" class="form-control" id="largo" name="largo" step="0.1" min="0" required>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="ancho">📐 Ancho (cm):</label>
										<input type="number" class="form-control" id="ancho" name="ancho" step="0.1" min="0" required>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label for="alto">📏 Alto (cm):</label>
										<input type="number" class="form-control" id="alto" name="alto" step="0.1" min="0" required>
									</div>
								</div>
							</div>
							<button type="submit" class="btn btn-info">📦 Registrar Artículo</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Lista de materiales existentes -->
		<div class="card">
			<div class="card-header">
				<h5>📚 Inventario de Materiales</h5>
			</div>
			<div class="card-body">
				<% List<DtMaterial> materiales = (List<DtMaterial>) request.getAttribute("materiales"); %>
				<% if (materiales != null && !materiales.isEmpty()) { %>
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>ID</th>
									<th>Tipo</th>
									<th>Título/Descripción</th>
									<th>Fecha Registro</th>
									<th>Estado</th>
								</tr>
							</thead>
							<tbody>
								<% for (DtMaterial material : materiales) { %>
									<tr>
										<td><%= material.getId() %></td>
										<td>
											<% if ("LIBRO".equals(material.getTipo())) { %>
												<span class="badge badge-primary">📚 Libro</span>
											<% } else { %>
												<span class="badge badge-info">📦 Artículo</span>
											<% } %>
										</td>
										<td><%= material.getTitulo() %></td>
										<td><%= material.getFechaRegistro().getDia() %>/<%= material.getFechaRegistro().getMes() %>/<%= material.getFechaRegistro().getAnio() %></td>
										<td>
											<% if (material.isDisponible()) { %>
												<span class="badge badge-success">✅ Disponible</span>
											<% } else { %>
												<span class="badge badge-warning">⏳ Prestado</span>
											<% } %>
										</td>
									</tr>
								<% } %>
							</tbody>
						</table>
					</div>
				<% } else { %>
					<div class="alert alert-info" role="alert">
						No hay materiales registrados en el sistema.
					</div>
				<% } %>
			</div>
		</div>
		
		<div class="mt-4">
			<a href="dashboardBibliotecario.jsp" class="btn btn-secondary">← Volver al Dashboard</a>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>
