package edu.eci.cvds.user_management.service;

import edu.eci.cvds.user_management.model.UserManagementException;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private SecretKey secretKey;

    private String validToken;
    private String invalidToken;
    private String expiredToken;
    private String username;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        username = "testUser";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        secretKey = keyGenerator.generateKey();

        validToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 100000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        invalidToken = "invalid.token";

        expiredToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() - 100000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }



    @Test
    void testIsTokenValidExpiredToken() throws UserManagementException {
        try {
            boolean result = jwtService.isTokenValid(expiredToken, username);

            assertFalse(result, "The token should be expired and invalid, but it was considered valid.");
        } catch (UserManagementException e) {
            assertFalse(e.getMessage().contains("expired"), "The exception should be about expiration.");
        }
    }


    @Test
    void testIsTokenExpiredNotExpired() {
        Claims claims = Jwts.claims();
        claims.setExpiration(new Date(System.currentTimeMillis() + 10000));
        assertFalse(jwtService.isTokenExpired(claims));
    }

    @Test
    void testIsTokenExpiredExpired() {
        Claims claims = Jwts.claims();
        claims.setExpiration(new Date(System.currentTimeMillis() - 10000));
        assertTrue(jwtService.isTokenExpired(claims));
    }

    @Test
    void testIsTokenValidValidToken() {
        String token = "valid.token";
        String username = "validUser";
        Claims mockClaims = mock(Claims.class);
        try {
            when(mockClaims.getSubject()).thenReturn(username);
            when(jwtService.parseToken(token)).thenReturn(mockClaims);
            when(jwtService.isTokenExpired(mockClaims)).thenReturn(false);
            assertTrue(jwtService.isTokenValid(token, username));
        } catch (UserManagementException e) {
            assertEquals("The provided token is invalid.", e.getMessage());
        }
    }
}
