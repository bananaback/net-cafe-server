package dev.bananaftmeo.netcafeserver.models.responses;

import dev.bananaftmeo.netcafeserver.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String identityNumber;
    private Double balance;
    private RoleEnum role;
}
