package io.github.dawncord.api.types;

/**
 * Represents permission override types.
 */
public enum PermissionOverrideType {
    /**
     * Permission override for a role.
     */
    ROLE(0),

    /**
     * Permission override for a member.
     */
    MEMBER(1);

    private final int value;

    PermissionOverrideType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the permission override type.
     *
     * @return The value of the permission override type.
     */
    public int getValue() {
        return value;
    }
}
