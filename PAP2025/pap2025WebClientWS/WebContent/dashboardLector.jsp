<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="publicadores.DtLector" %>
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

<title>Dashboard Lector - Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index.jsp">Biblioteca Comunitaria</a>
		<div class="navbar-nav ml-auto">
			<span class="navbar-text mr-3">
				👤 <%= session.getAttribute("nombreUsuario") %>
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
				<h2 class="mb-4">📖 Dashboard del Lector</h2>
				
				<div class="row">
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📚 Mis Préstamos</h5>
								<p class="card-text">Ver mis préstamos activos y historial</p>
								<a href="GestionPrestamos" class="btn btn-primary">Ver Préstamos</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📖 Catálogo</h5>
								<p class="card-text">Explorar materiales disponibles</p>
								<a href="consultas.jsp" class="btn btn-info">Ver Catálogo</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">👤 Mi Perfil</h5>
								<p class="card-text">Ver y editar mi información personal</p>
								<a href="#" class="btn btn-secondary">Ver Perfil</a>
							</div>
						</div>
					</div>
				</div>
				
				<div class="card">
					<div class="card-header">
						<h5>📊 Información Personal</h5>
					</div>
					<div class="card-body">
						<% DtLector lector = (DtLector) session.getAttribute("usuario"); %>
						<% if (lector != null) { %>
							<div class="row">
								<div class="col-md-6">
									<p><strong>Nombre:</strong> <%= lector.getNombre() %></p>
									<p><strong>Email:</strong> <%= lector.getEmail() %></p>
									<p><strong>Dirección:</strong> <%= lector.getDireccion() %></p>
								</div>
								<div class="col-md-6">
									<p><strong>Zona:</strong> <%= lector.getZona() %></p>
									<p><strong>Estado:</strong> 
										<% if ("ACTIVO".equals(lector.getEstadoL())) { %>
											<span class="badge badge-success"><%= lector.getEstadoL() %></span>
										<% } else { %>
											<span class="badge badge-danger"><%= lector.getEstadoL() %></span>
										<% } %>
									</p>
									<p><strong>Fecha Registro:</strong> <%= lector.getFechaRegistro().getDia() %>/<%= lector.getFechaRegistro().getMes() %>/<%= lector.getFechaRegistro().getAnio() %></p>
								</div>
							</div>
						<% } %>
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
