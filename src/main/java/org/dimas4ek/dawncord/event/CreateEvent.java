package org.dimas4ek.dawncord.event;

public class CreateEvent<T> {
    private final T t;

    public CreateEvent(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}
