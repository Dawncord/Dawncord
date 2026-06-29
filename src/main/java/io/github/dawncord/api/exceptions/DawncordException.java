package io.github.dawncord.api.exceptions;

/**
 * Unchecked exception thrown when a Dawncord API operation fails — for example when an action's
 * {@code submit()} call, a deferred reply, or a command registration cannot be completed.
 * <p>
 * It is unchecked so the fluent action/builder API does not force callers into {@code try/catch}.
 */
public class DawncordException extends RuntimeException {

    /**
     * Constructs a {@code DawncordException} with the specified detail message.
     *
     * @param message The detail message describing the failure.
     */
    public DawncordException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code DawncordException} with the specified detail message and cause.
     *
     * @param message The detail message describing the failure.
     * @param cause   The underlying cause of the failure.
     */
    public DawncordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code DawncordException} wrapping the specified cause.
     *
     * @param cause The underlying cause of the failure.
     */
    public DawncordException(Throwable cause) {
        super(cause);
    }
}
