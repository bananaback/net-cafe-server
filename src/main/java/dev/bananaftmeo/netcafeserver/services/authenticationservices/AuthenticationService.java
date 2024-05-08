package dev.bananaftmeo.netcafeserver.services.authenticationservices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.models.responses.UserResponse;
import dev.bananaftmeo.netcafeserver.repositories.ComputerRepository;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;
import dev.bananaftmeo.netcafeserver.services.usersessionservices.IUserSessionService;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private IUserSessionService userSessionService;

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
    public UserResponse loginWithUserInfo(LoginRequest loginRequest, Long computerId)
            throws UserAuthenticationException {
        ApplicationUser user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new UserAuthenticationException("User not found");
        }

        Computer computer = computerRepository.findById(computerId).get();

        userSessionService.createSession(user, computer);

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            UserResponse userResponse = authenticator.authenticate1(user);
            userResponse.setPricePerHour(computer.getPricePerHour());
            return userResponse;
        } else {
            return new UserResponse(user.getId(), user.getBalance(), user.getUsername(), false, "", "", 0.f);
        }
    }

}
