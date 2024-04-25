package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreationException extends Exception {
    private String errorMessage;

    public ProductCreationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
