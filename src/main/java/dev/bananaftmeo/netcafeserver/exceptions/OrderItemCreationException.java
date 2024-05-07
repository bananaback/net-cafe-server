package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCreationException extends Exception {
    private String errorMessage;

    public OrderItemCreationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
