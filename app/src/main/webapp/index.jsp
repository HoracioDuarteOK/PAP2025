<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Biblioteca Comunitaria - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            padding: 2rem;
            width: 100%;
            max-width: 400px;
        }
        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .login-header i {
            font-size: 3rem;
            color: #667eea;
            margin-bottom: 1rem;
        }
        .login-header h2 {
            color: #333;
            font-weight: 600;
        }
        .form-control {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            padding: 0.75rem 1rem;
            transition: all 0.3s ease;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-login {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 10px;
            padding: 0.75rem 2rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
        .footer-text {
            text-align: center;
            margin-top: 2rem;
            color: #6c757d;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="login-card">
            <div class="login-header">
                <i class="fas fa-book-open"></i>
                <h2>Biblioteca Comunitaria</h2>
                <p class="text-muted">Inicia sesión para acceder al sistema</p>
            </div>

            <!-- Mensajes de error/éxito -->
            <c:if test="${param.error != null}">
                <div class="alert alert-danger">
                    <c:choose>
                        <c:when test="${param.error == 'campos_vacios'}">
                            <i class="fas fa-exclamation-triangle"></i> Por favor, completa todos los campos.
                        </c:when>
                        <c:when test="${param.error == 'usuario_no_encontrado'}">
                            <i class="fas fa-user-times"></i> Usuario no encontrado. Verifica tu email.
                        </c:when>
                        <c:when test="${param.error == 'tipo_usuario_invalido'}">
                            <i class="fas fa-exclamation-circle"></i> Tipo de usuario no válido.
                        </c:when>
                        <c:when test="${param.error == 'login_required'}">
                            <i class="fas fa-lock"></i> Debes iniciar sesión para acceder a esta página.
                        </c:when>
                        <c:when test="${param.error == 'error_sistema'}">
                            <i class="fas fa-exclamation-triangle"></i> Error del sistema. Intenta nuevamente.
                        </c:when>
                        <c:otherwise>
                            <i class="fas fa-exclamation-triangle"></i> Error al iniciar sesión.
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <c:if test="${param.mensaje != null}">
                <div class="alert alert-success">
                    <c:choose>
                        <c:when test="${param.mensaje == 'logout_exitoso'}">
                            <i class="fas fa-check-circle"></i> Sesión cerrada exitosamente.
                        </c:when>
                        <c:otherwise>
                            <i class="fas fa-info-circle"></i> ${param.mensaje}
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <!-- Formulario de login -->
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">
                        <i class="fas fa-envelope"></i> Email
                    </label>
                    <input type="email" class="form-control" id="email" name="email" 
                           placeholder="Ingresa tu email" required>
                </div>

                <div class="mb-4">
                    <label for="password" class="form-label">
                        <i class="fas fa-lock"></i> Contraseña
                    </label>
                    <input type="password" class="form-control" id="password" name="password" 
                           placeholder="Ingresa tu contraseña" required>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary btn-login">
                        <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
                    </button>
                </div>
            </form>

            <div class="footer-text">
                <p><i class="fas fa-info-circle"></i> Acceso para bibliotecarios y lectores registrados</p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
