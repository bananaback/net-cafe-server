package dev.bananaftmeo.netcafeserver.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputerCreationException extends Exception {
    private String errorMessage;

    public ComputerCreationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
