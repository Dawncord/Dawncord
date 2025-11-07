package io.github.dawncord.api.types;

public enum RoleFlags {
    IN_PROMPT(1 << 0);

    private final int value;

    RoleFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
