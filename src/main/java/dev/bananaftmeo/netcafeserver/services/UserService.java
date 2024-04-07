package dev.bananaftmeo.netcafeserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.enums.RoleEnum;
import dev.bananaftmeo.netcafeserver.exceptions.UserRegistrationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.requests.RegisterRequest;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;

@Service
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest registerRequest) throws UserRegistrationException {
        ApplicationUser existingUser = userRepository.findByUsername(registerRequest.getUsername());
        if (existingUser != null) {
            throw new UserRegistrationException(
                    "User with username " + registerRequest.getUsername() + " already exist.");
        }
        existingUser = userRepository.findByPhoneNumber(registerRequest.getPhoneNumber());
        if (existingUser != null) {
            throw new UserRegistrationException(
                    "User with phone number " + registerRequest.getPhoneNumber() + " already exist.");
        }
        existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            throw new UserRegistrationException(
                    "User with email " + registerRequest.getEmail() + " already exist.");
        }
        existingUser = userRepository.findByIdentityNumber(registerRequest.getIdentityNumber());
        if (existingUser != null) {
            throw new UserRegistrationException(
                    "User with identity number " + registerRequest.getIdentityNumber() + " already exist.");
        }
        ApplicationUser user = new ApplicationUser(
                0L,
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPhoneNumber(),
                registerRequest.getIdentityNumber(),
                passwordEncoder.encode(registerRequest.getPassword()),
                RoleEnum.USER, null);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

}
