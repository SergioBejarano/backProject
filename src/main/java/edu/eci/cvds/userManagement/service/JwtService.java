package edu.eci.cvds.userManagement.service;

import edu.eci.cvds.userManagement.model.UserManagementException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Decodes and validates a JWT token.
     *
     * @param token JWT token to decode
     * @return Extracted claims from the token
     */
    public Claims parseToken(String token) throws UserManagementException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UserManagementException(UserManagementException.EXPIRED_TOKEN, e);
        } catch (JwtException e) {
            throw new UserManagementException(UserManagementException.INVALID_TOKEN, e);
        }
    }

    /**
     * Checks if a token is valid for a specific user.
     *
     * @param token JWT token
     * @param username Expected user's name
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, String username) throws UserManagementException {
        Claims claims = parseToken(token);
        String tokenUsername = claims.getSubject();
        return tokenUsername.equals(username) && !isTokenExpired(claims);
    }

    /**
     * Checks if the token has expired.
     *
     * @param claims Token claims
     * @return true if the token has expired, false otherwise
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
