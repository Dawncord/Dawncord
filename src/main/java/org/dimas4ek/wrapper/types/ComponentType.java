package org.dimas4ek.wrapper.types;

public enum ComponentType {
    Action(1),
    Button(2),
    String(3),
    Text(4),
    User(5),
    Role(6),
    Mentionable(7),
    Channel(8);

    private final int value;

    ComponentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
