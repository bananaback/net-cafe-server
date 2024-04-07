package dev.bananaftmeo.netcafeserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;
import dev.bananaftmeo.netcafeserver.utils.AccessTokenGenerator;
import dev.bananaftmeo.netcafeserver.utils.tokengenerators.RefreshTokenGenerator;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccessTokenGenerator accessTokenGenerator;
    @Autowired
    private RefreshTokenGenerator refreshTokenGenerator;

    @Override
    public AuthenticatedUserResponse loginUser(LoginRequest loginRequest) throws UserAuthenticationException {
        ApplicationUser user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new UserAuthenticationException("User not found");
        }
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String accessToken = accessTokenGenerator.generateToken(user);
            String refreshToken = refreshTokenGenerator.generateToken(user);
            return new AuthenticatedUserResponse(true, accessToken, refreshToken);
        } else {
            return new AuthenticatedUserResponse(false, "", "");
        }
    }

}
