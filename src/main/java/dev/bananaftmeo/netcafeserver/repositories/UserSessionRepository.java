package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;
import dev.bananaftmeo.netcafeserver.models.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByUserId(Long userId);

    Optional<UserSession> findFirstByUserIdAndStatusOrderByCheckinAtDesc(Long userId, UserSessionEnum status);

    List<UserSession> findByStatus(UserSessionEnum status);

}
