package dev.bananaftmeo.netcafeserver.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private double balance;
    private String userName;
    private boolean isSuccess;
    private String accessToken;
    private String refreshToken;
    private float pricePerHour;
}
