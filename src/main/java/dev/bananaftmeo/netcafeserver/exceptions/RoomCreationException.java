package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreationException extends Exception {
    private String errorMessage;

    public RoomCreationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
