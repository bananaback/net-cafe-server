package dev.bananaftmeo.netcafeserver.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenGenerator {

    public String generateJwt(ApplicationUser user, String jwtSecret, double jwtExpirationMinutes) {

        // Convert expiration minutes to milliseconds
        long expirationTime = (long) (jwtExpirationMinutes * 60 * 1000);

        // Get current time
        Date now = new Date();

        byte[] decodedSecretBytes = java.util.Base64.getUrlDecoder().decode(jwtSecret);
        // Create a key object from the secret
        SecretKey key = Keys.hmacShaKeyFor(decodedSecretBytes);

        // Build the JWT payload
        String compactJws = Jwts.builder()
                .subject(user.getUsername()) // Set username as subject
                .claim("role", user.getRole()) // Add role claim
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(key)
                .compact();

        System.out.println(compactJws);

        return compactJws;
    }
}