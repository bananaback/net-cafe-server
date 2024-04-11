package dev.bananaftmeo.netcafeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.bananaftmeo.netcafeserver.enums.RoleEnum;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;

@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
    }

    private void initializeUsers() {
        // Check if the user with username "bananaback" already exists
        ApplicationUser existingUser = userRepository.findByUsername("bananaback");
        if (existingUser == null) {
            // User does not exist, create a new one
            ApplicationUser user = new ApplicationUser();
            user.setUsername("bananaback");
            user.setPasswordHash(passwordEncoder.encode("bananaback")); // Encode the password
            user.setIdentityNumber("1234567889");
            user.setPhoneNumber("0347439367");
            user.setRole(RoleEnum.ADMIN);
            // Set other properties if needed
            // Save the user
            userRepository.save(user);
        }
    }
}
