package io.github.dawncord.api.types;

/**
 * Represents the type of permission for a command.
 */
public enum CommandPermissionType {
    /**
     * Permission applies to a role.
     */
    ROLE(1),

    /**
     * Permission applies to a user.
     */
    USER(2),

    /**
     * Permission applies to a channel.
     */
    CHANNEL(3);

    private final int value;

    CommandPermissionType(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the permission type.
     *
     * @return The value associated with the permission type.
     */
    public int getValue() {
        return value;
    }
}
