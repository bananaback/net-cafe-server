package dev.bananaftmeo.netcafeserver.services;

import dev.bananaftmeo.netcafeserver.exceptions.UserAuthenticationException;
import dev.bananaftmeo.netcafeserver.models.responses.AuthenticatedUserResponse;

public interface IRefreshTokenService {
    AuthenticatedUserResponse rotateToken(String refreshToken) throws UserAuthenticationException;
}
