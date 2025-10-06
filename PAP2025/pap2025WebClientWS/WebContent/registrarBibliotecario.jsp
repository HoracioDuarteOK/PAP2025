<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>Registrar Bibliotecario - Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index.jsp">Biblioteca Comunitaria</a>
		<div class="navbar-nav ml-auto">
			<a class="nav-link" href="index.jsp">← Volver al Inicio</a>
		</div>
	</nav>

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header text-center">
						<h3>📚 Registrar Bibliotecario</h3>
						<p class="mb-0">Registro para personal de la biblioteca</p>
					</div>
					<div class="card-body">
						<% if (request.getAttribute("error") != null) { %>
							<div class="alert alert-danger" role="alert">
								<%= request.getAttribute("error") %>
							</div>
						<% } %>
						
						<form action="RegistrarBibliotecario" method="post">
							<div class="form-group">
								<label for="nombre">👤 Nombre Completo:</label>
								<input type="text" class="form-control" id="nombre" name="nombre" 
									placeholder="Nombre del bibliotecario" required>
							</div>
							
							<div class="form-group">
								<label for="email">📧 Email:</label>
								<input type="email" class="form-control" id="email" name="email" 
									placeholder="bibliotecario@biblioteca.com" required>
							</div>
							
							<div class="form-group">
								<label for="password">🔒 Contraseña:</label>
								<input type="password" class="form-control" id="password" name="password" 
									placeholder="Mínimo 6 caracteres" minlength="6" required>
							</div>
							
							<div class="form-group">
								<label for="nroEmpleado">🏢 Número de Empleado:</label>
								<input type="number" class="form-control" id="nroEmpleado" name="nroEmpleado" 
									placeholder="Ej: 1001" required>
							</div>
							
							<div class="form-group text-center">
								<button type="submit" class="btn btn-success btn-lg">
									✅ Registrar Bibliotecario
								</button>
							</div>
						</form>
					</div>
					<div class="card-footer text-center">
						<p class="mb-0">
							¿Ya tienes cuenta? <a href="login.jsp">Iniciar Sesión</a>
						</p>
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
