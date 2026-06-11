package io.github.dawncord.api.types;

/**
 * Represents flags that can be applied to a guild role.
 */
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
