package org.dimas4ek.dawncord.types;

public enum PermissionOverrideType {
    ROLE(0),
    MEMBER(1);

    private final int value;

    PermissionOverrideType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
