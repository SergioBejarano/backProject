package edu.eci.cvds.user_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    /**
     * Configure a RestClient bean as the primary client to make HTTP requests.
     *
     * @return a configured RestClient instance.
     */
    @Bean
    @Primary
    public RestClient restClient() {
        return RestClient.builder()
                .build();
    }
}