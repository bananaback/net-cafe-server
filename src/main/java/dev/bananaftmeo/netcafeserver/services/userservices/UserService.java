package dev.bananaftmeo.netcafeserver.services.userservices;

import java.util.List;
import java.util.NoSuchElementException;

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
import dev.bananaftmeo.netcafeserver.models.responses.UserInfoResponse;
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
                registerRequest.getBalance(),
                passwordEncoder.encode(registerRequest.getPassword()),
                RoleEnum.USER, null, null, null);
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

    @Override
    public ApplicationUser findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserInfoResponse getUserInfoById(Long id) {
        UserInfoResponse userInfoResponse = userRepository.findUserById(id);
        if (userInfoResponse == null) {
            throw new NoSuchElementException("User with id " + id + " not found.");
        }
        return userInfoResponse;
    }

    @Override
    public List<UserInfoResponse> getAllUserInfo() {
        return userRepository.findAllUsers();
    }

    @Override
    public UserInfoResponse updateUserInfo(Long userId, UserInfoResponse userInfoResponse) {
        ApplicationUser user = userRepository.findById(userId).get();
        user.setBalance(user.getBalance() + userInfoResponse.getBalance());
        userRepository.save(user);
        return userInfoResponse;
    }

}
