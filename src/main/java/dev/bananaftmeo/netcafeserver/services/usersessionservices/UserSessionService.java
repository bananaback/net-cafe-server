package dev.bananaftmeo.netcafeserver.services.usersessionservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.UserSession;
import dev.bananaftmeo.netcafeserver.models.responses.UserSessionResponse;
import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;
import dev.bananaftmeo.netcafeserver.repositories.UserSessionRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserSessionService implements IUserSessionService {

    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    public UserSessionService(UserSessionRepository userSessionRepository, UserRepository userRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserSession createSession(ApplicationUser user, Computer computer) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setComputer(computer);
        session.setStatus(UserSessionEnum.ONGOING); // Or whatever initial status you want to set
        session.setCheckinAt(LocalDateTime.now());
        return userSessionRepository.save(session);
    }

    @Override
    @Transactional
    public UserSession changeSessionStatus(UserSession userSession, UserSessionEnum newStatus) {
        userSession.setStatus(newStatus);
        if (newStatus == UserSessionEnum.FINISHED) {
            userSession.setCheckoutAt(LocalDateTime.now());
        }
        return userSessionRepository.save(userSession);
    }

    @Override
    @Transactional
    public boolean logoutUser(Long userId) {
        Optional<UserSession> optionalUserSession = userSessionRepository
                .findFirstByUserIdAndStatusOrderByCheckinAtDesc(userId, UserSessionEnum.ONGOING);
        if (optionalUserSession.isPresent()) {
            UserSession userSession = optionalUserSession.get();
            userSession.setStatus(UserSessionEnum.FINISHED);
            userSession.setCheckoutAt(LocalDateTime.now());
            userSessionRepository.save(userSession);
            return true;
        }
        return false;
    }

    @Transactional
    public void deductBalancesForOngoingSessions() {
        List<UserSession> ongoingSessions = userSessionRepository.findByStatus(UserSessionEnum.ONGOING);

        for (UserSession session : ongoingSessions) {
            LocalDateTime checkinTime = session.getCheckinAt();
            LocalDateTime now = LocalDateTime.now();
            long minutes = Duration.between(checkinTime, now).toMinutes();

            float pricePerHour = session.getComputer().getPricePerHour();
            double deduction = (pricePerHour / 60);

            ApplicationUser user = session.getUser();
            double currentBalance = user.getBalance();

            // Deduct until the balance reaches 0
            if (currentBalance > deduction) {
                user.setBalance(currentBalance - deduction);
            } else {
                // Balance is not enough to deduct fully, set balance to 0
                user.setBalance(0.0);
            }

            // Save the updated user
            userRepository.save(user);
        }
    }

    @Override
    public List<UserSessionResponse> getLatestSessionsByComputerIds(List<Long> computerIds) {
        return userSessionRepository.findLatestByComputerIds(computerIds);
    }
}