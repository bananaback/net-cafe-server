package dev.bananaftmeo.netcafeserver.utils.tokenvalidators;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class RefreshTokenValidator implements ITokenValidator {

    @Value("${jwt.refresh_token.secret}")
    private String refreshTokenSecret;

    @Override
    public boolean validateToken(String token) {
        // Decode the secret key from Base64URL
        byte[] decodedSecretBytes = java.util.Base64.getUrlDecoder().decode(refreshTokenSecret);
        // Convert the decoded bytes into a SecretKey instance
        SecretKey key = Keys.hmacShaKeyFor(decodedSecretBytes);
        try {

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            // OK, we can trust this JWT
            return true;

        } catch (JwtException e) {

            // don't trust the JWT!
            return false;
        }
    }

}
