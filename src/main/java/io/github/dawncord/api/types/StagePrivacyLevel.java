package io.github.dawncord.api.types;

/**
 * Represents privacy levels for stage channels.
 */
public enum StagePrivacyLevel {
    /**
     * Stage channel accessible only to guild members.
     */
    GUILD_ONLY(2);

    private final int value;

    StagePrivacyLevel(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the stage privacy level.
     *
     * @return The value of the stage privacy level.
     */
    public int getValue() {
        return value;
    }
}
