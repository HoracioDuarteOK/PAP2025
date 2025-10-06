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

<title>Biblioteca Comunitaria</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="#">Biblioteca Comunitaria</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">Inicio
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="login.jsp">Iniciar Sesión</a></li>
				<li class="nav-item"><a class="nav-link" href="registrarLector.jsp">Registrar Lector</a></li>
				<li class="nav-item"><a class="nav-link" href="registrarBibliotecario.jsp">Registrar Bibliotecario</a></li>
				<li class="nav-item"><a class="nav-link" href="consultas.jsp">Consultas</a></li>
			</ul>
		</div>
	</nav>

	<div class="container mt-5">
		<div class="row">
			<div class="col-md-8 mx-auto text-center">
				<h1 class="display-4 mb-4">🏛️ Biblioteca Comunitaria</h1>
				<p class="lead">Sistema de gestión de biblioteca comunitaria para lectores y bibliotecarios</p>
				
				<div class="row mt-5">
					<div class="col-md-6 mb-4">
						<div class="card h-100">
							<div class="card-body">
								<h5 class="card-title">👤 Para Lectores</h5>
								<p class="card-text">Accede a tu perfil, consulta tus préstamos y solicita nuevos materiales</p>
								<a href="login.jsp" class="btn btn-primary">Iniciar Sesión como Lector</a>
							</div>
						</div>
					</div>
					<div class="col-md-6 mb-4">
						<div class="card h-100">
							<div class="card-body">
								<h5 class="card-title">📚 Para Bibliotecarios</h5>
								<p class="card-text">Gestiona materiales, préstamos y administra el sistema</p>
								<a href="login.jsp" class="btn btn-success">Iniciar Sesión como Bibliotecario</a>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row mt-4">
					<div class="col-md-12">
						<div class="card">
							<div class="card-body">
								<h5 class="card-title">📖 Servicios Disponibles</h5>
								<ul class="list-unstyled">
									<li>✅ Registro de usuarios (Lectores y Bibliotecarios)</li>
									<li>✅ Gestión de inventario de materiales</li>
									<li>✅ Sistema completo de préstamos</li>
									<li>✅ Control y seguimiento de devoluciones</li>
									<li>✅ Consultas y reportes</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
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
