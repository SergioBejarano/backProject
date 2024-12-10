package edu.eci.cvds.user_management.config;

import edu.eci.cvds.user_management.service.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(ApiClient apiClient) {
        return new JwtAuthenticationFilter(apiClient);
    }
}
