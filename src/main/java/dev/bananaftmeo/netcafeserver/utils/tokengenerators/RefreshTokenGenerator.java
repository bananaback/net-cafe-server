package dev.bananaftmeo.netcafeserver.utils.tokengenerators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.utils.TokenGenerator;

public class RefreshTokenGenerator implements ITokenGenerator {
    @Value("${jwt.refresh_token.secret}")
    private String refreshTokenSecret;
    @Value("${jwt.refresh_token.expiration_minutes}")
    private double refreshTokenExpirationMinutes;
    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public String generateToken(ApplicationUser user) {
        return tokenGenerator.generateJwt(user, refreshTokenSecret, refreshTokenExpirationMinutes);
    }
}
