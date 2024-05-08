package dev.bananaftmeo.netcafeserver.services.usersessionservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.UserSession;
import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;
import dev.bananaftmeo.netcafeserver.repositories.UserSessionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSessionService implements IUserSessionService {

    private final UserSessionRepository userSessionRepository;

    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
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
        Optional<UserSession> optionalUserSession = userSessionRepository.findByUserId(userId);
        if (optionalUserSession.isPresent()) {
            UserSession userSession = optionalUserSession.get();
            if (userSession.getStatus() != UserSessionEnum.FINISHED) {
                userSession.setStatus(UserSessionEnum.FINISHED);
                userSession.setCheckoutAt(LocalDateTime.now());
                userSessionRepository.save(userSession);
                return true;
            }
        }
        return false;
    }

}