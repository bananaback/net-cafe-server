package dev.bananaftmeo.netcafeserver.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryCreationException extends Exception {
    private String errrorMessage;

    public ProductCategoryCreationException(String errorMessage) {
        super(errorMessage);
        this.errrorMessage = errorMessage;
    }
}
