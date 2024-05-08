package dev.bananaftmeo.netcafeserver.services.authenticationservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.RefreshToken;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.models.responses.UserResponse;
import dev.bananaftmeo.netcafeserver.repositories.RefreshTokenRepository;
import dev.bananaftmeo.netcafeserver.utils.tokengenerators.AccessTokenGenerator;
import dev.bananaftmeo.netcafeserver.utils.tokengenerators.RefreshTokenGenerator;

@Component
public class Authenticator {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AccessTokenGenerator accessTokenGenerator;
    @Autowired
    private RefreshTokenGenerator refreshTokenGenerator;

    public AuthenticatedUserResponse authenticate(ApplicationUser user) throws UserAuthenticationException {
        String accessToken = accessTokenGenerator.generateToken(user);
        String refreshToken = refreshTokenGenerator.generateToken(user);
        RefreshToken refreshTokenObject = new RefreshToken();
        refreshTokenObject.setToken(refreshToken);

        // Set the user for the refresh token
        refreshTokenObject.setUser(user);

        // Add the refresh token to the user's refresh tokens set
        user.getRefreshTokens().add(refreshTokenObject);

        try {
            // Save the refresh token (which will also cascade the save to the user's
            // refresh tokens set)
            refreshTokenRepository.save(refreshTokenObject);

            // Save the user (optional if you haven't made any other changes to the user
            // entity)
            // userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new UserAuthenticationException("The given refresh token to save is null.");
        } catch (OptimisticLockingFailureException e) {
            throw new UserAuthenticationException(
                    "The entity uses optimistic locking and has a version attribute with a different value from that found in the persistence store or the entity is assumed to be present but does not exist in the database.");
        }

        return new AuthenticatedUserResponse(true, accessToken, refreshToken);
    }

    public UserResponse authenticate1(ApplicationUser user) throws UserAuthenticationException {
        String accessToken = accessTokenGenerator.generateToken(user);
        String refreshToken = refreshTokenGenerator.generateToken(user);
        RefreshToken refreshTokenObject = new RefreshToken();
        refreshTokenObject.setToken(refreshToken);

        // Set the user for the refresh token
        refreshTokenObject.setUser(user);

        // Add the refresh token to the user's refresh tokens set
        user.getRefreshTokens().add(refreshTokenObject);

        try {
            // Save the refresh token (which will also cascade the save to the user's
            // refresh tokens set)
            refreshTokenRepository.save(refreshTokenObject);

            // Save the user (optional if you haven't made any other changes to the user
            // entity)
            // userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new UserAuthenticationException("The given refresh token to save is null.");
        } catch (OptimisticLockingFailureException e) {
            throw new UserAuthenticationException(
                    "The entity uses optimistic locking and has a version attribute with a different value from that found in the persistence store or the entity is assumed to be present but does not exist in the database.");
        }

        return new UserResponse(user.getId(), user.getBalance(), user.getUsername(), true, accessToken, refreshToken,
                0.f);
    }
}
