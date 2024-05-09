package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;
import dev.bananaftmeo.netcafeserver.models.UserSession;
import dev.bananaftmeo.netcafeserver.models.responses.UserSessionResponse;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByUserId(Long userId);

    Optional<UserSession> findFirstByUserIdAndStatusOrderByCheckinAtDesc(Long userId, UserSessionEnum status);

    List<UserSession> findByStatus(UserSessionEnum status);

    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.responses.UserSessionResponse(us.userSessionId, us.user.id, us.computer.id, us.status, us.checkinAt, us.checkoutAt) FROM UserSession us WHERE (us.computer.id, us.checkinAt) IN (SELECT us2.computer.id, MAX(us2.checkinAt) FROM UserSession us2 WHERE us2.computer.id IN :computerIds GROUP BY us2.computer.id) ORDER BY us.checkinAt DESC")
    List<UserSessionResponse> findLatestByComputerIds(@Param("computerIds") List<Long> computerIds);

}
