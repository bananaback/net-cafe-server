package dev.bananaftmeo.netcafeserver.services.userservices;

import dev.bananaftmeo.netcafeserver.exceptions.UserRegistrationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.requests.RegisterRequest;
import dev.bananaftmeo.netcafeserver.models.responses.UserInfoResponse;

import java.util.List;

public interface IUserService {
    void registerUser(RegisterRequest registerRequest) throws UserRegistrationException;

    ApplicationUser findUserByUsername(String username);

    UserInfoResponse getUserInfoById(Long userId);

    UserInfoResponse updateUserInfo(Long userId, UserInfoResponse userInfoResponse);

    List<UserInfoResponse> getAllUserInfo();
}
