package com.example.musicApp.security;

import com.example.musicApp.entity.User;
import com.example.musicApp.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(1)
public class SecurityManager implements Filter {

    public static final Map<String, Long> tokenStore = new ConcurrentHashMap<>();

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    @Autowired
    private UserService userService;

    // =============================================================================
    // FILTER IMPLEMENTATION (Authentication)
    // =============================================================================

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        try {
            // Allow unauthenticated access to login and register endpoints
            if (path.contains("/api/users/login")) {
                chain.doFilter(request, response);
                return;
            }

            // Extract and validate authorization header
            String authHeader = req.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                sendUnauthorizedResponse(res, "Missing or invalid Authorization header");
                return;
            }

            String token = authHeader.substring(7);
            Long userId = tokenStore.get(token);

            if (userId == null) {
                sendUnauthorizedResponse(res, "Invalid or expired token");
                return;
            }

            // Load the full user object and set in security context
            Optional<User> userOpt = userService.getProfile(userId);
            if (userOpt.isPresent()) {
                setCurrentUser(userOpt.get());
                chain.doFilter(request, response);
            } else {
                sendUnauthorizedResponse(res, "User not found");
            }
        } finally {
            // Always clear the security context after request
            clear();
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"" + message + "\"}");
    }

    // =============================================================================
    // SECURITY CONTEXT METHODS
    // =============================================================================

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static Long getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    public static void clear() {
        currentUser.remove();
    }

    public static boolean isAdmin() {
        User user = getCurrentUser();
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public static boolean isCurrentUser(Long userId) {
        User user = getCurrentUser();
        return user != null && user.getId().equals(userId);
    }
}

