package io.github.dawncord.api.event;

/**
 * Represents an event triggered when modifying an entity.
 *
 * @param <T> The type of entity being modified.
 */
public class ModifyEvent<T> {
    private final T t;

    /**
     * Constructs a ModifyEvent with the specified entity.
     *
     * @param t The entity being modified.
     */
    public ModifyEvent(T t) {
        this.t = t;
    }

    /**
     * Retrieves the entity being modified.
     *
     * @return The modified entity.
     */
    public T get() {
        return t;
    }
}
