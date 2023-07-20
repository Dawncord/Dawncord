package org.dimas4ek.api.exceptions;

public class InvalidOptionOrderException extends RuntimeException {
    public InvalidOptionOrderException(String errorMessage) {
        super(errorMessage);
    }
}
