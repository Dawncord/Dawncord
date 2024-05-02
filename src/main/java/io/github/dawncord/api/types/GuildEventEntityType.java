package io.github.dawncord.api.types;

/**
 * Represents different entity types for guild events.
 */
public enum GuildEventEntityType {
    /**
     * Stage instance entity type.
     */
    STAGE_INSTANCE(1),

    /**
     * Voice entity type.
     */
    VOICE(2),

    /**
     * External entity type.
     */
    EXTERNAL(3);

    private final int value;

    GuildEventEntityType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the entity type.
     *
     * @return The value of the entity type.
     */
    public int getValue() {
        return value;
    }
}
