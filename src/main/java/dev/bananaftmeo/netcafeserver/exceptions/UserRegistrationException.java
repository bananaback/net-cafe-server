package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;

@Getter
public class UserRegistrationException extends Exception {
    private String errorMessage;

    public UserRegistrationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
