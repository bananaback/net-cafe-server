package dev.bananaftmeo.netcafeserver.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserResponse {
    private String message;
    private String accessToken;
}
