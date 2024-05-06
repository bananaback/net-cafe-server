package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreationException extends Exception {
    private String errorMessage;

    public OrderCreationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
