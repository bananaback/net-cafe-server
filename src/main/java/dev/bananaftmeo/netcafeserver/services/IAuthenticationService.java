package dev.bananaftmeo.netcafeserver.services;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.requests.LoginRequest;

public interface IAuthenticationService {
    public String loginUser(LoginRequest loginRequest) throws UserAuthenticationException;
}
