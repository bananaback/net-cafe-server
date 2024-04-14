package dev.bananaftmeo.netcafeserver.utils.tokengenerators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;

@Component
public class AccessTokenGenerator implements ITokenGenerator {
    @Value("${jwt.access_token.secret}")
    private String jwtSecret;

    @Value("${jwt.access_token.expiration_minutes}")
    private double jwtExpirationMinutes;
    @Autowired
    private TokenGenerator tokenGenerator;

    public String generateToken(ApplicationUser user) {
        return tokenGenerator.generateJwt(user, jwtSecret, jwtExpirationMinutes);
    }
}
