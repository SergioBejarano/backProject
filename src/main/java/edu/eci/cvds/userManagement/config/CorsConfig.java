package edu.eci.cvds.userManagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class configures Cross-Origin Resource Sharing (CORS) settings for the application.
 * CORS allows web browsers to make requests to a domain other than the one that served the web page.
 * This configuration enables secure cross-origin requests by allowing only specific origins (in this case, "http://localhost:3000") to access resources.
 * It also permits specific HTTP methods (GET, POST, PUT, DELETE, OPTIONS), allows any headers to be included in the request, and permits the inclusion of credentials (cookies, HTTP authentication) in cross-origin requests.
 * By using this class, the application ensures that the frontend running on "http://localhost:3000" can interact with the backend API while maintaining proper security and preventing unauthorized access from other domains.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures Cross-Origin Resource Sharing (CORS) settings for the application.
     * This configuration allows requests from a specific origin, enables certain HTTP methods,
     * and permits credentials to be included in the requests.
     *
     * @return a WebMvcConfigurer implementation with CORS settings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
