package Jednostavan.sistem.e_trgovine.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull Object handler) throws Exception {
            
        String path = request.getRequestURI();
        
        // Dozvoli pristup login stranici i CSS fajlovima
        if (path.startsWith("/login") || path.startsWith("/css")) {
            return true;
        }
        
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
} 