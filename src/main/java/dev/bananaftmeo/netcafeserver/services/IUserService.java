package dev.bananaftmeo.netcafeserver.services;

import dev.bananaftmeo.netcafeserver.exceptions.UserRegistrationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.requests.RegisterRequest;

public interface IUserService {
    void registerUser(RegisterRequest registerRequest) throws UserRegistrationException;

    ApplicationUser findUserByUsername(String username);
}