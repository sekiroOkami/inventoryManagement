package org.sekiro.InventoryManagementSystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtils {

    public static final long EXPIRATION_TIME_IN_MILLISEC = 1000L * 60L * 60L; // 1 hour

    // Stores the secret key used to sign and verify JWTs.
    private SecretKey key;

    @Value("${secretJwtString}")
    private String secretJwtString;

    // Initializes the SecretKey after DI(e.g. secretJwtString)
    @PostConstruct
    private void init() {
        log.info("Initializing JwtUtils with secretJwtString length: {}",
                secretJwtString == null ? "null" : secretJwtString.length());

        if (secretJwtString == null || secretJwtString.length() < 32) {
            log.error("JWT secret is null or too short: length={}",
                    secretJwtString == null ? "null" : secretJwtString.length());
            throw new IllegalStateException("JWT secret must be at least 32 characters long.");
        }

        try {
            byte[] keyByte = secretJwtString.getBytes(StandardCharsets.UTF_8);
            this.key = Keys.hmacShaKeyFor(keyByte);
            log.info("JWT key initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize JWT key", e);
            throw new IllegalStateException("Failed to initialize JWT key", e);
        }
    }

    // Generates a JWT for a user identified by their email
    public String generateToken(String email) {
        // Called after successful authentication(e.g., login) to issue a token:
        // String token = jwtUtils.generateToken("john@example.com");
        return Jwts.builder()
                // Sets the JWT's subject claim to the user's email
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +EXPIRATION_TIME_IN_MILLISEC))
                .signWith(key)
                .compact();
    }

    // Extracts the username(email) from the JWT's subject claim.
    public String getUsernameFromToken(String token) {
        // Used during token validation to verify the user:
        // String email = jwtUtils.getUsernameFromToken(token); // Returns "john@example.com"
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        try {
            return claimsTFunction.apply(
                // Creates a parser to verify the token's signature
                Jwts.parser().verifyWith(key).build()
                        // Parses the token and verifies its signature, throwing exceptions for invalid or expired tokens
                        .parseSignedClaims(token)
                        // Retrieves the token's claims
                        .getPayload()
            );
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token");
        }

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}

