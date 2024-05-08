package dev.bananaftmeo.netcafeserver.services.authenticationservices;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;
import dev.bananaftmeo.netcafeserver.models.responses.UserResponse;

public interface IAuthenticationService {
    public AuthenticatedUserResponse loginUser(LoginRequest loginRequest) throws UserAuthenticationException;
    public UserResponse loginWithUserInfo(LoginRequest loginRequest) throws UserAuthenticationException;

}
