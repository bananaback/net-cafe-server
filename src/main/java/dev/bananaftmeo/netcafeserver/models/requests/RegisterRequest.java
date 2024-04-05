package dev.bananaftmeo.netcafeserver.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    private String email;
    @NotBlank(message = "Identity number cannot be blank")
    private String identityNumber;

}
