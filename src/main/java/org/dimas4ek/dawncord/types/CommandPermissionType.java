package org.dimas4ek.dawncord.types;

public enum CommandPermissionType {
    ROLE(1),
    USER(2),
    CHANNEL(3);

    private final int value;

    CommandPermissionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
