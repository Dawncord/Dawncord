package org.dimas4ek.wrapper.types;

public enum InteractionType {
    CHANNEL_MESSAGE_WITH_SOURCE(4);

    private final int value;
    InteractionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
