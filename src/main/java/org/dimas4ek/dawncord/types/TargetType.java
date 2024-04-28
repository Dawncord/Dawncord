package org.dimas4ek.dawncord.types;

public enum TargetType {
    STREAM(1),
    EMBEDDED_APPLICATION(2);

    private final int value;

    TargetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
