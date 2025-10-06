<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="publicadores.DtBibliotecario" %>
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

<title>Dashboard Bibliotecario - Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index.jsp">Biblioteca Comunitaria</a>
		<div class="navbar-nav ml-auto">
			<span class="navbar-text mr-3">
				📚 <%= session.getAttribute("nombreUsuario") %>
			</span>
			<a class="nav-link" href="Logout">🚪 Cerrar Sesión</a>
		</div>
	</nav>

	<div class="container mt-4">
		<% if (request.getAttribute("mensaje") != null) { %>
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				<%= request.getAttribute("mensaje") %>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		<% } %>
		
		<div class="row">
			<div class="col-md-12">
				<h2 class="mb-4">📚 Dashboard del Bibliotecario</h2>
				
				<div class="row">
					<div class="col-md-3 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📖 Materiales</h5>
								<p class="card-text">Gestionar inventario de libros y artículos</p>
								<a href="GestionMateriales" class="btn btn-primary">Gestionar</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-3 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📋 Préstamos</h5>
								<p class="card-text">Controlar préstamos y devoluciones</p>
								<a href="GestionPrestamos" class="btn btn-success">Gestionar</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-3 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">👥 Usuarios</h5>
								<p class="card-text">Administrar lectores y bibliotecarios</p>
								<a href="#" class="btn btn-info">Administrar</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-3 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📊 Reportes</h5>
								<p class="card-text">Consultas y análisis del sistema</p>
								<a href="consultas.jsp" class="btn btn-warning">Ver Reportes</a>
							</div>
						</div>
					</div>
				</div>
				
				<div class="card">
					<div class="card-header">
						<h5>👤 Información del Bibliotecario</h5>
					</div>
					<div class="card-body">
						<% DtBibliotecario bibliotecario = (DtBibliotecario) session.getAttribute("usuario"); %>
						<% if (bibliotecario != null) { %>
							<div class="row">
								<div class="col-md-6">
									<p><strong>Nombre:</strong> <%= bibliotecario.getNombre() %></p>
									<p><strong>Email:</strong> <%= bibliotecario.getEmail() %></p>
								</div>
								<div class="col-md-6">
									<p><strong>Número de Empleado:</strong> <%= bibliotecario.getNroEmpleado() %></p>
									<p><strong>Tipo Usuario:</strong> <span class="badge badge-success">Bibliotecario</span></p>
								</div>
							</div>
						<% } %>
					</div>
				</div>
				
				<div class="card mt-4">
					<div class="card-header">
						<h5>🚀 Acciones Rápidas</h5>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-md-4">
								<a href="registrarLector.jsp" class="btn btn-outline-primary btn-block">
									👤 Registrar Lector
								</a>
							</div>
							<div class="col-md-4">
								<a href="registrarBibliotecario.jsp" class="btn btn-outline-success btn-block">
									📚 Registrar Bibliotecario
								</a>
							</div>
							<div class="col-md-4">
								<a href="GestionMateriales" class="btn btn-outline-info btn-block">
									📖 Registrar Material
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
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
