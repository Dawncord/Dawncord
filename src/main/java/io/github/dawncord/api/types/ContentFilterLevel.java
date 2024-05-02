package io.github.dawncord.api.types;

/**
 * Represents the level of content filtering in a guild.
 */
public enum ContentFilterLevel {
    /**
     * Content filtering is disabled;
     * <p>
     * media content will not be scanned.
     */
    DISABLED(0),

    /**
     * Content filtering is enabled for members without roles;
     * <p>
     * media content sent by members without roles will be scanned.
     */
    MEMBERS_WITHOUT_ROLES(1),

    /**
     * Content filtering is enabled for all members;
     * <p>
     * media content sent by all members will be scanned.
     */
    ALL_MEMBERS(2);

    private final int value;

    ContentFilterLevel(int value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the content filter level.
     *
     * @return The value associated with the content filter level.
     */
    public int getValue() {
        return value;
    }
}
