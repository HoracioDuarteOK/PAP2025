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

<title>Consultas - Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index.jsp">Biblioteca Comunitaria</a>
		<div class="navbar-nav ml-auto">
			<a class="nav-link" href="index.jsp">← Volver al Inicio</a>
		</div>
	</nav>

	<div class="container mt-5">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-center mb-4">📊 Consultas del Sistema</h2>
				
				<div class="row">
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📖 Materiales</h5>
								<p class="card-text">Consultar inventario de libros y artículos especiales</p>
								<a href="#" class="btn btn-primary">Ver Materiales</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">📋 Préstamos</h5>
								<p class="card-text">Ver estado de préstamos activos y devoluciones</p>
								<a href="#" class="btn btn-success">Ver Préstamos</a>
							</div>
						</div>
					</div>
					
					<div class="col-md-4 mb-4">
						<div class="card h-100">
							<div class="card-body text-center">
								<h5 class="card-title">👥 Usuarios</h5>
								<p class="card-text">Información de lectores y bibliotecarios</p>
								<a href="#" class="btn btn-info">Ver Usuarios</a>
							</div>
						</div>
					</div>
				</div>
				
				<div class="alert alert-info mt-4" role="alert">
					<h4 class="alert-heading">ℹ️ Información</h4>
					<p>Las consultas específicas estarán disponibles según el tipo de usuario logueado:</p>
					<ul class="mb-0">
						<li><strong>Lectores:</strong> Pueden ver sus préstamos personales y materiales disponibles</li>
						<li><strong>Bibliotecarios:</strong> Acceso completo a todas las consultas y reportes del sistema</li>
					</ul>
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
