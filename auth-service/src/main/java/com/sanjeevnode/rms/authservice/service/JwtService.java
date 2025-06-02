package com.sanjeevnode.rms.authservice.service;

import com.sanjeevnode.rms.authservice.dto.UserDTO;
import com.sanjeevnode.rms.authservice.exception.InvalidTokenException;
import com.sanjeevnode.rms.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key for signing the JWT (should be stored securely in production)
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Token expiration time in milliseconds
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    /**
     * Extract all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey()) // Use the signing key to verify the token
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extract the username (subject) from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validate the token against the provided user details.
     *
     * @param token the JWT token
     * @param user  the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check if the token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from the JWT token using a resolver function.
     *
     * @param token    the JWT token
     * @param resolver the function to resolve the claim
     * @param <T>      the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Generate a new JWT token for the given user.
     *
     * @param user the user for whom the token is generated
     * @return the generated JWT token
     */
    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername()) // Set the subject (username)
                .issuedAt(new Date(System.currentTimeMillis())) // Set the issue date
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set the expiration date
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .claim("createdAt", user.getCreatedAt())
                .claim("updatedAt", user.getUpdatedAt())
                .signWith(getSignInKey()) // Sign the token with the secret key
                .compact();
    }

    /**
     * Get the signing key for the JWT token.
     *
     * @return the signing key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decode the secret key
        return Keys.hmacShaKeyFor(keyBytes); // Generate the signing key
    }
}