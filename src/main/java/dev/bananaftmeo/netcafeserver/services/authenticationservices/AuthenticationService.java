package dev.bananaftmeo.netcafeserver.services.authenticationservices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.models.responses.UserResponse;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;

    @Override
    public AuthenticatedUserResponse loginUser(LoginRequest loginRequest) throws UserAuthenticationException {
        ApplicationUser user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new UserAuthenticationException("User not found");
        }
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return authenticator.authenticate(user);
        } else {
            return new AuthenticatedUserResponse(false, "", "");
        }
    }

    @Override
    public UserResponse loginWithUserInfo(LoginRequest loginRequest) throws UserAuthenticationException {
        ApplicationUser user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new UserAuthenticationException("User not found");
        }
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return authenticator.authenticate1(user);
        } else {
            return new UserResponse(user.getId(), user.getBalance(), user.getUsername(),false, "", "");
        }
    }

}
