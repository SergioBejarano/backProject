package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.model.UserManagementException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApiClient {

    private final RestClient restClient;

    @Value("${API_AUTH_URL}")
    private String apiAuthUrl;

    /**
     * Validate the given token by making a request to the authentication API.
     *
     * @param token The token to validate.
     * @return true if the token is valid; false otherwise.
     * @throws UserManagementException if an error occurs during validation.
     */
    public boolean validateToken(String token) throws UserManagementException {
        try {
            String uri = String.format("%s/auth/session", apiAuthUrl);
            assert restClient != null;
            ResponseEntity<?> response = restClient.get()
                    .uri(uri)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(Object.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Object responseBody = response.getBody();
                if (responseBody instanceof Map<?, ?> responseMap) {
                    String role = (String) responseMap.get("role");
                    return "Administrator".equals(role);
                }
                return false;
            }
            handleErrorResponse((HttpStatus) response.getStatusCode());
            return false;
        } catch (Exception e) {
            throw new UserManagementException("An error occurred while validating the token.", e);
        }
    }


    /**
     * Handle error responses by throwing a UserManagementException with an appropriate message.
     *
     * @param statusCode The HTTP status code.
     * @throws UserManagementException with an error message based on the status code.
     */
    private void handleErrorResponse(HttpStatus statusCode) throws UserManagementException {
        if (statusCode.is4xxClientError()) {
            throw new UserManagementException("Client error occurred: " + statusCode.getReasonPhrase());
        } else if (statusCode.is5xxServerError()) {
            throw new UserManagementException("Server error occurred: " + statusCode.getReasonPhrase());
        } else {
            throw new UserManagementException("Unexpected error occurred: " + statusCode.getReasonPhrase());
        }
    }
    public ApiClient() {
        this.restClient = null;
    }
}
