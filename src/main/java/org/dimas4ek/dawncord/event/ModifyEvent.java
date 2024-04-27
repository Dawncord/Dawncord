package org.dimas4ek.dawncord.event;

public class ModifyEvent<T> {
    private final T t;

    public ModifyEvent(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}
