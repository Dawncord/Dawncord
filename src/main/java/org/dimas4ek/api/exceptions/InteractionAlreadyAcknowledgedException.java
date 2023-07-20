package org.dimas4ek.api.exceptions;

public class InteractionAlreadyAcknowledgedException extends RuntimeException {
    public InteractionAlreadyAcknowledgedException(String message) {
        super(message);
    }
}
