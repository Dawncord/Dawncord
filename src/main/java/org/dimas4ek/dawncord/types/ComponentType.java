package org.dimas4ek.dawncord.types;

public enum ComponentType {
    ACTION(1),
    BUTTON(2),
    TEXT(3),
    TEXT_INPUT(4),
    USER(5),
    ROLE(6),
    MENTIONABLE(7),
    CHANNEL(8);

    private final int value;

    ComponentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
