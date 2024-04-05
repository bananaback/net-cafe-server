package dev.bananaftmeo.netcafeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    ApplicationUser findByPhoneNumber(String phoneNumber);

    ApplicationUser findByIdentityNumber(String identityNumber);
}
