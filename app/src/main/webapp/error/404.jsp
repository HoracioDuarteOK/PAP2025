<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página no encontrada - Biblioteca Comunitaria</title>
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
        .error-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            padding: 3rem;
            text-align: center;
            max-width: 500px;
            width: 100%;
        }
        .error-icon {
            font-size: 5rem;
            color: #dc3545;
            margin-bottom: 1rem;
        }
        .error-code {
            font-size: 3rem;
            font-weight: 700;
            color: #333;
            margin-bottom: 0.5rem;
        }
        .error-message {
            color: #6c757d;
            margin-bottom: 2rem;
        }
        .btn-home {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 10px;
            padding: 0.75rem 2rem;
            color: white;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        .btn-home:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-card">
            <div class="error-icon">
                <i class="fas fa-exclamation-triangle"></i>
            </div>
            <div class="error-code">404</div>
            <h3 class="error-message">Página no encontrada</h3>
            <p class="text-muted mb-4">
                Lo sentimos, la página que estás buscando no existe o ha sido movida.
            </p>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-home">
                <i class="fas fa-home"></i> Volver al Inicio
            </a>
        </div>
    </div>
</body>
</html>
