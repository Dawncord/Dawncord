package org.dimas4ek.dawncord.types;

public enum SelectMenuType {
    TEXT(3),
    USER(5),
    ROLE(6),
    MENTIONABLE(7),
    CHANNEL(8);

    private final int value;

    SelectMenuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
