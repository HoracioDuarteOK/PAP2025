<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="publicadores.DtPrestamo" %>
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

<title>Gestión de Préstamos - Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index.jsp">Biblioteca Comunitaria</a>
		<div class="navbar-nav ml-auto">
			<span class="navbar-text mr-3">
				<% if ("lector".equals(session.getAttribute("tipoUsuario"))) { %>
					👤 <%= session.getAttribute("nombreUsuario") %>
				<% } else { %>
					📚 <%= session.getAttribute("nombreUsuario") %>
				<% } %>
			</span>
			<a class="nav-link" href="Logout">🚪 Cerrar Sesión</a>
		</div>
	</nav>

	<div class="container mt-4">
		<% if ("lector".equals(request.getAttribute("vista"))) { %>
			<h2 class="mb-4">📖 Mis Préstamos</h2>
		<% } else { %>
			<h2 class="mb-4">📋 Gestión de Préstamos</h2>
		<% } %>
		
		<% if (request.getAttribute("error") != null) { %>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<%= request.getAttribute("error") %>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		<% } %>
		
		<% if ("bibliotecario".equals(request.getAttribute("vista"))) { %>
			<!-- Formulario para crear nuevo préstamo (solo bibliotecarios) -->
			<div class="card mb-4">
				<div class="card-header">
					<h5>➕ Crear Nuevo Préstamo</h5>
				</div>
				<div class="card-body">
					<form action="GestionPrestamos" method="post">
						<input type="hidden" name="accion" value="crear">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label for="emailLector">👤 Email del Lector:</label>
									<input type="email" class="form-control" id="emailLector" name="emailLector" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label for="idMaterial">📖 ID del Material:</label>
									<input type="number" class="form-control" id="idMaterial" name="idMaterial" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label>&nbsp;</label>
									<button type="submit" class="btn btn-success btn-block">📚 Crear Préstamo</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		<% } %>
		
		<!-- Lista de préstamos -->
		<div class="card">
			<div class="card-header">
				<% if ("lector".equals(request.getAttribute("vista"))) { %>
					<h5>📖 Mis Préstamos Activos</h5>
				<% } else { %>
					<h5>📋 Préstamos en Curso</h5>
				<% } %>
			</div>
			<div class="card-body">
				<% List<DtPrestamo> prestamos = (List<DtPrestamo>) request.getAttribute("prestamos"); %>
				<% if (prestamos != null && !prestamos.isEmpty()) { %>
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>ID</th>
									<% if ("bibliotecario".equals(request.getAttribute("vista"))) { %>
										<th>Lector</th>
									<% } %>
									<th>Material</th>
									<th>Fecha Préstamo</th>
									<th>Fecha Vencimiento</th>
									<th>Estado</th>
									<% if ("bibliotecario".equals(request.getAttribute("vista"))) { %>
										<th>Acciones</th>
									<% } %>
								</tr>
							</thead>
							<tbody>
								<% for (DtPrestamo prestamo : prestamos) { %>
									<tr>
										<td><%= prestamo.getId() %></td>
										<% if ("bibliotecario".equals(request.getAttribute("vista"))) { %>
											<td><%= prestamo.getLector().getNombre() %><br><small><%= prestamo.getLector().getEmail() %></small></td>
										<% } %>
										<td><%= prestamo.getMaterial().getTitulo() %><br><small><%= prestamo.getMaterial().getTipo() %></small></td>
										<td><%= prestamo.getFechaPrestamo().getDia() %>/<%= prestamo.getFechaPrestamo().getMes() %>/<%= prestamo.getFechaPrestamo().getAnio() %></td>
										<td><%= prestamo.getFechaVencimiento().getDia() %>/<%= prestamo.getFechaVencimiento().getMes() %>/<%= prestamo.getFechaVencimiento().getAnio() %></td>
										<td>
											<% if ("EN_CURSO".equals(prestamo.getEstado())) { %>
												<span class="badge badge-warning">⏳ En Curso</span>
											<% } else if ("DEVUELTO".equals(prestamo.getEstado())) { %>
												<span class="badge badge-success">✅ Devuelto</span>
											<% } else if ("VENCIDO".equals(prestamo.getEstado())) { %>
												<span class="badge badge-danger">⚠️ Vencido</span>
											<% } else { %>
												<span class="badge badge-secondary"><%= prestamo.getEstado() %></span>
											<% } %>
										</td>
										<% if ("bibliotecario".equals(request.getAttribute("vista"))) { %>
											<td>
												<% if ("EN_CURSO".equals(prestamo.getEstado())) { %>
													<form action="GestionPrestamos" method="post" style="display: inline;">
														<input type="hidden" name="accion" value="actualizar">
														<input type="hidden" name="idPrestamo" value="<%= prestamo.getId() %>">
														<input type="hidden" name="nuevoEstado" value="DEVUELTO">
														<button type="submit" class="btn btn-success btn-sm">✅ Marcar Devuelto</button>
													</form>
												<% } %>
											</td>
										<% } %>
									</tr>
								<% } %>
							</tbody>
						</table>
					</div>
				<% } else { %>
					<div class="alert alert-info" role="alert">
						<% if ("lector".equals(request.getAttribute("vista"))) { %>
							No tienes préstamos activos en este momento.
						<% } else { %>
							No hay préstamos en curso en este momento.
						<% } %>
					</div>
				<% } %>
			</div>
		</div>
		
		<div class="mt-4">
			<% if ("lector".equals(request.getAttribute("vista"))) { %>
				<a href="dashboardLector.jsp" class="btn btn-secondary">← Volver al Dashboard</a>
			<% } else { %>
				<a href="dashboardBibliotecario.jsp" class="btn btn-secondary">← Volver al Dashboard</a>
			<% } %>
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
