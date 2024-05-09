package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.responses.UserInfoResponse;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);

    ApplicationUser findByPhoneNumber(String phoneNumber);

    ApplicationUser findByIdentityNumber(String identityNumber);

    ApplicationUser findByEmail(String email);

    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.responses.UserInfoResponse(u.id, u.username, u.email, u.phoneNumber, u.identityNumber, u.balance, u.role) FROM ApplicationUser u")
    List<UserInfoResponse> findAllUsers();

    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.responses.UserInfoResponse(u.id, u.username, u.email, u.phoneNumber, u.identityNumber, u.balance, u.role) FROM ApplicationUser u WHERE u.id = :id")
    UserInfoResponse findUserById(Long id);
}
