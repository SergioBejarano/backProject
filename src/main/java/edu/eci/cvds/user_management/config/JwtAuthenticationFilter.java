package edu.eci.cvds.user_management.config;

import edu.eci.cvds.user_management.model.UserManagementException;
import edu.eci.cvds.user_management.service.ApiClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ApiClient apiClient;

    public JwtAuthenticationFilter(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final Logger logger = LoggerFactory.getLogger(this.getClass());
        final String token = getTokenFromRequest(request);

        if (token != null) {
            try {
                if (apiClient.validateToken(token)) {
                    String role = decodePayload(token);
                    if (role.isEmpty()) {
                        throw new UserManagementException(UserManagementException.ROLE_NOT_FOUND);
                    }

                    UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                            "12335", null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(userToken);

                    logger.info("Authentication successful. Role: {}", role);
                } else {
                    throw new UserManagementException(UserManagementException.INVALID_TOKEN);
                }
            } catch (UserManagementException e) {
                logger.error("Authentication failed. URL: {}. Error: {}",
                        request.getRequestURL(), e.getMessage(), e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String decodePayload(String token) throws UserManagementException {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid token format");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            if (payload.contains("\"role\":")) {
                int startIndex = payload.indexOf("\"role\":") + 7;
                int endIndex = payload.indexOf("\"", startIndex + 1);
                return payload.substring(startIndex + 1, endIndex).toUpperCase();
            } else {
                throw new UserManagementException(UserManagementException.ROLE_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new UserManagementException("Error decoding token payload.", e);
        }
    }
}
