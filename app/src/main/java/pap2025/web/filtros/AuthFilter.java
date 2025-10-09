package pap2025.web.filtros;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtro de autenticación para proteger las páginas de la aplicación
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/app/*"})
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Obtener la sesión sin crear una nueva
        HttpSession session = httpRequest.getSession(false);
        
        // Verificar si el usuario está autenticado
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
        
        if (isLoggedIn) {
            // Usuario autenticado, continuar con la solicitud
            chain.doFilter(request, response);
        } else {
            // Usuario no autenticado, redirigir al login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp?error=unauthorized");
        }
    }
    
    @Override
    public void destroy() {
        // Limpieza del filtro
    }
}
