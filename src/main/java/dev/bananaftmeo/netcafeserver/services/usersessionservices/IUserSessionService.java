package dev.bananaftmeo.netcafeserver.services.usersessionservices;

import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.UserSession;
import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;

public interface IUserSessionService {
    UserSession createSession(ApplicationUser user, Computer computer);

    UserSession changeSessionStatus(UserSession userSession, UserSessionEnum newStatus);

    boolean logoutUser(Long userId);
}
