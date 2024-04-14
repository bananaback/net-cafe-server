package dev.bananaftmeo.netcafeserver.services.refreshtokenservices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.RefreshToken;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.repositories.RefreshTokenRepository;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;
import dev.bananaftmeo.netcafeserver.services.authenticationservices.Authenticator;

@Service
public class RefreshTokenService implements IRefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Authenticator authenticator;

    @Override
    public AuthenticatedUserResponse rotateToken(String token) throws UserAuthenticationException {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken == null) {
            throw new UserAuthenticationException("Invalid refresh token: " + token + ".");
        }
        refreshTokenRepository.deleteById(refreshToken.getId());
        Optional<ApplicationUser> user = userRepository.findById(refreshToken.getUser().getId());
        if (user.isEmpty()) {
            throw new UserAuthenticationException("User not found.");
        }
        return authenticator.authenticate(user.get());
    }

    @Override
    public void deleteAllRefreshTokenOfUser(Long userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }

}
