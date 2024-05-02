package io.github.dawncord.api.types;

/**
 * Represents privacy levels for guild events.
 */
public enum GuildEventPrivacyLevel {
    /**
     * The scheduled event is only accessible to guild members.
     */
    GUILD_ONLY(2);

    private final int value;

    GuildEventPrivacyLevel(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the privacy level.
     *
     * @return The value of the privacy level.
     */
    public int getValue() {
        return value;
    }
}