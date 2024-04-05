package dev.bananaftmeo.netcafeserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;
import dev.bananaftmeo.netcafeserver.utils.AccessTokenGenerator;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccessTokenGenerator accessTokenGenerator;

    @Override
    public String loginUser(LoginRequest loginRequest) throws UserAuthenticationException {
        ApplicationUser user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new UserAuthenticationException("User not found");
        }
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String jwtToken = accessTokenGenerator.generateAccessToken(user);
            return jwtToken;
        } else {
            throw new UserAuthenticationException("Password does not correct.");
        }
    }

}
