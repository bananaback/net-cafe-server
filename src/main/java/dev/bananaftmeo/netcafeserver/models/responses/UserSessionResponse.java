package dev.bananaftmeo.netcafeserver.models.responses;

import java.time.LocalDateTime;

import dev.bananaftmeo.netcafeserver.enums.UserSessionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionResponse {

    private Long userSessionId;
    private Long userId;
    private Long computerId;
    private UserSessionEnum status; // Adjusted type
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;

}
