package pap2025.web.filtros;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Filtro de autenticación que verifica si el usuario está logueado
 * antes de acceder a las páginas protegidas
 */
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
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        
        // Verificar si el usuario está logueado
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
        
        // Permitir acceso a páginas públicas
        if (requestURI.endsWith("/login") || requestURI.endsWith("/index.jsp") || 
            requestURI.endsWith("/") || requestURI.contains("/css/") || 
            requestURI.contains("/js/") || requestURI.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Si no está logueado, redirigir al login
        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp?error=login_required");
            return;
        }
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpieza del filtro
    }
}
