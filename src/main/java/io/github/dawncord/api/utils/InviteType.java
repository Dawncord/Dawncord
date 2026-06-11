package io.github.dawncord.api.utils;

/**
 * Represents the supported invite target types.
 */
public enum InviteType {
    GUILD(0),
    GROUP_DM(1),
    FRIEND(2);

    private final int value;

    InviteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
