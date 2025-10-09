<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Servicios SOAP - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-tomorrow.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 2rem 0;
        }
        .container {
            background: white;
            border-radius: 20px;
            padding: 2rem;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
        }
        .method-card {
            border-left: 4px solid #667eea;
            margin-bottom: 1.5rem;
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 5px;
        }
        .endpoint {
            background: #2d2d2d;
            color: #f8f8f2;
            padding: 1rem;
            border-radius: 5px;
            font-family: 'Courier New', monospace;
            margin: 1rem 0;
        }
        pre {
            margin: 0;
            background: transparent !important;
        }
        code {
            background: #2d2d2d;
            color: #f8f8f2;
            padding: 0.2rem 0.4rem;
            border-radius: 3px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="text-center mb-4">
            <i class="fas fa-globe fa-3x text-primary mb-3"></i>
            <h1>📡 Web Services XML</h1>
            <p class="lead">Documentación de los servicios web XML de la Biblioteca Comunitaria</p>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-primary">
                <i class="fas fa-arrow-left"></i> Volver al Login
            </a>
        </div>

        <hr>

        <!-- Información General -->
        <div class="row mb-4">
            <div class="col-md-12">
                <div class="alert alert-info">
                    <h5><i class="fas fa-info-circle"></i> Información General</h5>
                    <p class="mb-0">
                        Los Web Services XML están disponibles para consumo externo y permiten la integración 
                        con otros sistemas. Todos los servicios devuelven datos en formato XML.
                    </p>
                </div>
            </div>
        </div>

        <!-- Endpoint Principal -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0"><i class="fas fa-server"></i> Endpoint Principal</h4>
            </div>
            <div class="card-body">
                <div class="endpoint">
                    <strong>URL Base:</strong> http://localhost:8080/ws/biblioteca
                </div>
                <div class="endpoint">
                    <strong>Obtener Materiales:</strong> 
                    <a href="http://localhost:8080/ws/biblioteca?operation=obtenerMateriales" target="_blank" style="color: #50fa7b;">
                        http://localhost:8080/ws/biblioteca?operation=obtenerMateriales
                    </a>
                </div>
                <div class="endpoint">
                    <strong>Obtener Estadísticas:</strong> 
                    <a href="http://localhost:8080/ws/biblioteca?operation=obtenerEstadisticas" target="_blank" style="color: #50fa7b;">
                        http://localhost:8080/ws/biblioteca?operation=obtenerEstadisticas
                    </a>
                </div>
                <p class="mt-3 mb-0">
                    <i class="fas fa-lightbulb text-warning"></i> 
                    <strong>Tip:</strong> Puedes consumir estos servicios desde cualquier lenguaje que soporte HTTP y XML (Java, Python, JavaScript, etc.).
                </p>
            </div>
        </div>

        <!-- Operaciones Disponibles -->
        <h3 class="mb-3"><i class="fas fa-list"></i> Operaciones Disponibles</h3>

        <!-- Materiales -->
        <div class="card mb-3">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0"><i class="fas fa-book"></i> Operaciones de Materiales</h5>
            </div>
            <div class="card-body">
                <div class="method-card">
                    <h6><code>obtenerTodosMateriales()</code></h6>
                    <p>Obtiene todos los materiales (libros y artículos especiales) disponibles en la biblioteca.</p>
                    <p><strong>Retorna:</strong> Lista de MaterialDTO</p>
                </div>

                <div class="method-card">
                    <h6><code>obtenerMaterialPorId(int idMaterial)</code></h6>
                    <p>Obtiene un material específico por su ID.</p>
                    <p><strong>Parámetros:</strong> idMaterial (int)</p>
                    <p><strong>Retorna:</strong> MaterialDTO</p>
                </div>

                <div class="method-card">
                    <h6><code>obtenerLibros()</code></h6>
                    <p>Obtiene solo los libros disponibles.</p>
                    <p><strong>Retorna:</strong> Lista de MaterialDTO (solo libros)</p>
                </div>
            </div>
        </div>

        <!-- Préstamos -->
        <div class="card mb-3">
            <div class="card-header bg-warning text-dark">
                <h5 class="mb-0"><i class="fas fa-exchange-alt"></i> Operaciones de Préstamos</h5>
            </div>
            <div class="card-body">
                <div class="method-card">
                    <h6><code>obtenerTodosPrestamos()</code></h6>
                    <p>Obtiene todos los préstamos registrados en el sistema.</p>
                    <p><strong>Retorna:</strong> Lista de PrestamoDTO</p>
                </div>

                <div class="method-card">
                    <h6><code>obtenerPrestamosPorLector(String emailLector)</code></h6>
                    <p>Obtiene los préstamos de un lector específico.</p>
                    <p><strong>Parámetros:</strong> emailLector (String)</p>
                    <p><strong>Retorna:</strong> Lista de PrestamoDTO</p>
                </div>

                <div class="method-card">
                    <h6><code>obtenerPrestamosPorEstado(String estado)</code></h6>
                    <p>Obtiene préstamos filtrados por estado.</p>
                    <p><strong>Parámetros:</strong> estado (String) - PENDIENTE, ENCURSO, DEVUELTO</p>
                    <p><strong>Retorna:</strong> Lista de PrestamoDTO</p>
                </div>
            </div>
        </div>

        <!-- Usuarios -->
        <div class="card mb-3">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0"><i class="fas fa-users"></i> Operaciones de Usuarios</h5>
            </div>
            <div class="card-body">
                <div class="method-card">
                    <h6><code>obtenerTodosLectores()</code></h6>
                    <p>Obtiene todos los lectores registrados.</p>
                    <p><strong>Retorna:</strong> Lista de UsuarioDTO</p>
                </div>

                <div class="method-card">
                    <h6><code>obtenerUsuarioPorEmail(String email)</code></h6>
                    <p>Obtiene información de un usuario por su email.</p>
                    <p><strong>Parámetros:</strong> email (String)</p>
                    <p><strong>Retorna:</strong> UsuarioDTO</p>
                </div>
            </div>
        </div>

        <!-- Estadísticas -->
        <div class="card mb-3">
            <div class="card-header bg-secondary text-white">
                <h5 class="mb-0"><i class="fas fa-chart-bar"></i> Operaciones de Estadísticas</h5>
            </div>
            <div class="card-body">
                <div class="method-card">
                    <h6><code>obtenerEstadisticas()</code></h6>
                    <p>Obtiene estadísticas generales del sistema en formato XML.</p>
                    <p><strong>Retorna:</strong> String (XML con estadísticas)</p>
                </div>
            </div>
        </div>

        <!-- Ejemplo de Uso -->
        <div class="card">
            <div class="card-header bg-dark text-white">
                <h5 class="mb-0"><i class="fas fa-code"></i> Ejemplo de Consumo (Java)</h5>
            </div>
            <div class="card-body">
                <pre><code class="language-java">// Generar cliente con wsimport
// wsimport -keep -p pap2025.client http://localhost:8080/ws/biblioteca?wsdl

import pap2025.client.BibliotecaSOAPService;
import pap2025.client.BibliotecaService;
import pap2025.client.MaterialDTO;

public class ClienteEjemplo {
    public static void main(String[] args) {
        // Crear servicio y puerto
        BibliotecaSOAPService service = new BibliotecaSOAPService();
        BibliotecaService port = service.getBibliotecaServicePort();
        
        // Llamar a operación
        List<MaterialDTO> materiales = port.obtenerTodosMateriales();
        
        for (MaterialDTO material : materiales) {
            System.out.println("Material: " + material.getTitulo());
        }
    }
}</code></pre>
            </div>
        </div>

        <!-- Herramientas Recomendadas -->
        <div class="alert alert-success mt-4">
            <h5><i class="fas fa-tools"></i> Herramientas Recomendadas para Testing</h5>
            <ul class="mb-0">
                <li><strong>SoapUI:</strong> Cliente SOAP gráfico con generación automática desde WSDL</li>
                <li><strong>Postman:</strong> Soporta SOAP requests con XML</li>
                <li><strong>wsimport (JDK):</strong> Generador de clientes Java desde WSDL</li>
                <li><strong>curl:</strong> Para testing desde línea de comandos</li>
            </ul>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-core.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/autoloader/prism-autoloader.min.js"></script>
</body>
</html>

