package edu.eci.cvds.user_management.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS and security configuration for the application, implementing the {@link WebMvcConfigurer} interface.
 * This class handles the HTTP security configuration and CORS management to allow access to specific resources
 * without authentication, while also protecting against CSRF attacks.
 */
 @Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor that injects the JWT authentication filter.
     *
     * @param jwtAuthenticationFilter the JWT filter used to authenticate requests
     */
    public CorsConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the HTTP security filter chain.
     * @param security the {@link HttpSecurity} object used to configure HTTP security.
     * @return the security filter chain configuration.
     * @throws Exception if an error occurs while configuring the security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/swagger-ui/", "/v3/api-docs/", "/swagger-resources/", "/webjars/").permitAll()
                                .requestMatchers("/notifications/admin/").hasRole("ADMIN")
                                .requestMatchers("/notifications/users/").hasRole("STUDENT").

                                anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
