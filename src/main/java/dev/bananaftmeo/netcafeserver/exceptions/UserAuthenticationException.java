package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationException extends Exception {
    private String errrorMessage;

    public UserAuthenticationException(String errorMessage) {
        super(errorMessage);
        this.errrorMessage = errorMessage;
    }
}
