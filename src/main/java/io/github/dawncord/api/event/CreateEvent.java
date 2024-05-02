package io.github.dawncord.api.event;

/**
 * Represents a generic create event.
 *
 * @param <T> The type of object associated with the create event.
 */
public class CreateEvent<T> {
    private final T t;

    /**
     * Constructs a CreateEvent with the specified object.
     *
     * @param t The object associated with the create event.
     */
    public CreateEvent(T t) {
        this.t = t;
    }

    /**
     * Retrieves the object associated with the create event.
     *
     * @return The object associated with the create event.
     */
    public T get() {
        return t;
    }
}
