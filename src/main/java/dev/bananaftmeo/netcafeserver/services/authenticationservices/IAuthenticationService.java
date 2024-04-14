package dev.bananaftmeo.netcafeserver.services.authenticationservices;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;

public interface IAuthenticationService {
    public AuthenticatedUserResponse loginUser(LoginRequest loginRequest) throws UserAuthenticationException;
}
